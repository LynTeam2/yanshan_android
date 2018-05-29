package cn.gov.bjys.onlinetrain.act;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.ycl.framework.utils.util.ToastUtil;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.io.File;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.api.BaseResponse;
import cn.gov.bjys.onlinetrain.api.HomeApi;
import cn.gov.bjys.onlinetrain.api.UserApi;
import cn.gov.bjys.onlinetrain.bean.UserBean;
import cn.gov.bjys.onlinetrain.task.Un7zTask;
import cn.gov.bjys.onlinetrain.task.UnZipTask;
import cn.gov.bjys.onlinetrain.utils.AssetsHelper;
import cn.gov.bjys.onlinetrain.utils.MapParamsHelper;
import cn.gov.bjys.onlinetrain.utils.PermissionUtil;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import cn.gov.bjys.onlinetrain.utils.YSUserInfoManager;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/2 0002.
 */
public class LogoActivity extends FrameActivity {
    public final static String TAG = LogoActivity.class.getSimpleName();

    @Bind(R.id.count_down)
    Button mCountDown;

    @Bind(R.id.main_content)
    RelativeLayout mCoordinatorLayout;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_logo_layout);
    }

    @Override

    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    @Override
    public void initViews() {
        super.initViews();
        requestPermiss();
        mCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                startNextActivity();
            }
        });
    }

    //定时器
    CountDownTimer countDownTimer;

    public void initCountDownTimer() {
        countDownTimer = new CountDownTimer(3200, 1000) {
            int count = 3;

            @Override
            public void onTick(long millisUntilFinished) {
                if (count <= 1) {
                    count = 1;
                }

                mCountDown.setText(--count + "秒后可进入");
                Log.d(TAG, count + "秒后可进入");
            }

            @Override
            public void onFinish() {
                startNextActivity();
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
            }
        };
        countDownTimer.start();
    }

    private String mUserName;
    private String mPassword;

    public void startNextActivity() {
//        boolean notFirstLogin = SavePreference.getBoolean(this, YSConst.NOT_FIRST_LOGIN);
        boolean notFirstLogin = true;
        if (notFirstLogin) {
            mUserName = SavePreference.getStr(this,YSConst.UserInfo.KEY_USER_ACCOUNT);
            mPassword = SavePreference.getStr(this,YSConst.UserInfo.KEY_USER_PASSWORD);
            userLogin();
        } else {
            startAct(GuideActivity.class);
            LogoActivity.this.finish();
        }
    }


    private void userLogin(){
        if(TextUtils.isEmpty(mUserName) || TextUtils.isEmpty(mPassword)){
            toLoginAct();
            return;
        }

        Observable<BaseResponse<String>> obsLogin;
        obsLogin = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).
                getSpeUrlService(YSConst.BaseUrl.BASE_URL, UserApi.class).userLogin(HRetrofitNetHelper.createReqJsonBody(MapParamsHelper.getLogin(mUserName, mPassword)));
        obsLogin.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<String>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("dodo", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("dodo", "e.message = " + e.getMessage());
                        toLoginAct();
                    }

                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                        Log.d("dodo", "resp = " + stringBaseResponse);
                        if ("1".equals(stringBaseResponse.getCode())) {
                            //登陆成功
                            //保存登陆用户信息
                            UserBean.UserParentBean parentBean = FastJSONParser.getBean(stringBaseResponse.getResults(), UserBean.UserParentBean.class);
                            UserBean bean = parentBean.getUser();
                            YSUserInfoManager.getsInstance().saveUserBean(bean);
                            toMainAct();
                        } else {
                            toLoginAct();
                        }
                    }
                });
    }

    private void toMainAct() {
        startAct(MainActivity.class);
        finish();
    }

    private void toLoginAct(){
        startAct(LoginActivity.class);
        finish();
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 0x01;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private void requestPermiss() {
        PermissionUtil.checkPermission(LogoActivity.this, mCoordinatorLayout, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE, new PermissionUtil.permissionInterface() {
            @Override
            public void success() {
                reqPermissionRefreshUi(true);
            }

            @Override
            public void oldPermission() {
                reqPermissionRefreshUi(true);
            }
        });
    }


    /**
     * 检测权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (PermissionUtil.verifyPermissions(grantResults)) {//有权限
                reqPermissionRefreshUi(true);
            } else {
                //没有权限
                if (!PermissionUtil.shouldShowPermissions(this, permissions)) {//这个返回false 表示勾选了不再提示
                    showSnackBar(mCoordinatorLayout, "请去设置界面设置权限", "去设置");
                } else {
                    //表示没有权限 ,但是没勾选不再提示
                    showSnackBar(mCoordinatorLayout, "没有权限无法更新最新课程内容!", "去设置");

                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showSnackBar(View view, String content, String action) {
        Snackbar snackbar = Snackbar.make(view, content,
                Snackbar.LENGTH_INDEFINITE);
        if (action != null) {
            snackbar.setAction(action, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startToSetting();
                }
            });
        }
        snackbar.show();
    }

    public static int REQUEST_PERMISSION_SEETING = 0x02;

    private void startToSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_PERMISSION_SEETING);
    }

    //重写下面的方法为了判断, 用户去设置界面是否开启了权限
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //如果是从设置界面返回,就继续判断权限
        if (requestCode == REQUEST_PERMISSION_SEETING) {
            //如果要直到授权再进入 去掉下面这行 否则加上
            reqPermissionRefreshUi(true);
            requestPermiss();
        }
    }

    private void reqPermissionRefreshUi(boolean reqOk) {
        initCountDownTimer();
        if (reqOk) {
            updateZip();
        }
    }

    int count = 0;

    /**
     * 更新zip包分为2部分
     * 1、当应用未解压zip时应该先解压zip
     * 2、再进行获取网络数据的操作
     */
    private void updateZip() {
        //TODO 下载更新包
        count++;
        Log.d("dodoT", "updateZip  " + count);
        ToastUtil.showToast("updateZip  " + count);
//        new UnZipTask(true, null, null).execute();//开始解压压缩包，解压好了就不解压了
        new Un7zTask(true, null, null).execute();//开始解压压缩包，解压好了就不解压了
        downloadZip();
    }

    public final static String UPGRADE_SAVE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "upgrade.7z";
    private void downloadZip() {
        rx.Observable<ResponseBody> observable;
        observable = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext())
                .getSpeUrlService(YSConst.BaseUrl.BASE_URL, HomeApi.class)
                .downZipPacket("http://39.104.118.75/api/upgrade");

        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())


                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        AssetsHelper.saveFile(body);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.d("dodoT","UPGRADE_SAVE_PATH = "+ UPGRADE_SAVE_PATH);
//                        new UnZipTask(false, UPGRADE_SAVE_PATH,
//                                getFilesDir().getParent()+File.separator + YSConst.UPDATE_ZIP).execute();//开始解压压缩包，解压好了就不解压了
                        new Un7zTask(false, UPGRADE_SAVE_PATH,
                                getFilesDir().getParent()+File.separator + YSConst.UPDATE_ZIP).execute();//开始解压压缩包，解压好了就不解压了
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
//                            AssetsHelper.writeCache(responseBody,  new File(UPGRADE_SAVE_PATH));

                    }
                });

    }


}
