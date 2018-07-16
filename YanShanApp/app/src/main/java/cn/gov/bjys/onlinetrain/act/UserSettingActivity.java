package cn.gov.bjys.onlinetrain.act;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.base.FrameActivityStack;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.GlideProxy;
import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.view.TitleHeaderView;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.DooUserSettingLinear;
import cn.gov.bjys.onlinetrain.api.BaseResponse;
import cn.gov.bjys.onlinetrain.api.HomeApi;
import cn.gov.bjys.onlinetrain.api.UserApi;
import cn.gov.bjys.onlinetrain.bean.AvatarBackBean;
import cn.gov.bjys.onlinetrain.bean.UserBean;
import cn.gov.bjys.onlinetrain.fragment.UserFragment.SaveNickFragment;
import cn.gov.bjys.onlinetrain.task.Un7zTask;
import cn.gov.bjys.onlinetrain.utils.AssetsHelper;
import cn.gov.bjys.onlinetrain.utils.MapParamsHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import cn.gov.bjys.onlinetrain.utils.YSUserInfoManager;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class UserSettingActivity extends FrameActivity {

    public final static int SET_NICK_BACK = 1;

    public final static int SAVE_USER_AVATAR = 2;


    @Bind(R.id.header)
    TitleHeaderView header;

    @Bind(R.id.quite_btn)
    Button quite_btn;

    @Bind(R.id.setting_layout)
    LinearLayout setting_layout;

    @Bind(R.id.content_layout)
    RelativeLayout mContentLayout;

    @OnClick({R.id.quite_btn})
    public void onTabClick(View v) {
        switch (v.getId()) {
            case R.id.quite_btn:
                //TODO GOTO QUTIE
                userLoginOut();
                break;
        }
    }

    private void userLoginOut() {
        startAct(LoginActivity.class);
        //清理账号密码
        SavePreference.save(this, YSConst.UserInfo.KEY_USER_ACCOUNT, "");
        SavePreference.save(this, YSConst.UserInfo.KEY_USER_PASSWORD, "");
        remoteLogout();
        finishAllActivitys();
    }

    private void remoteLogout() {
        Observable<BaseResponse<String>> obsLoginOut;
        obsLoginOut = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).
                getSpeUrlService(YSConst.BaseUrl.BASE_URL, UserApi.class).userLogout();
        obsLoginOut.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                        if ("1".equals(stringBaseResponse.getCode())) {
                            //退出登陆成功
                        }
                    }
                });

    }

    private void finishAllActivitys() {
        FrameActivityStack.create().finishAllActivity();
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_user_setting);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    android.support.v4.app.FragmentTransaction ft;

    @Override
    public void initViews() {
        super.initViews();
        ft = getSupportFragmentManager().beginTransaction();
        initSettingLayout();
    }


    DooUserSettingLinear avatarLinear, nickLinear,updateLinear,otherLinear;

    public void initSettingLayout() {
        setting_layout.removeAllViews();
        avatarLinear = new DooUserSettingLinear(this);
        avatarLinear.setTitle("头像");
        String userAvatar = SavePreference.getStr(BaseApplication.getAppContext(), YSConst.UserInfo.USER_AVATAR_PATH);
        if (!TextUtils.isEmpty(userAvatar)) {
            avatarLinear.setAvatar(userAvatar);
        } else {
            avatarLinear.setAvatar(R.drawable.user_normal_avatar);
        }
        avatarLinear.setCustomClick(R.id.next_layout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 设置头像
                startActForResult(UserAvatarChooseActivity.class, SAVE_USER_AVATAR);
            }
        });
        setting_layout.addView(avatarLinear);

        String nickName = SavePreference.getStr(BaseApplication.getAppContext(), YSConst.UserInfo.USER_SAVE_NICK);

        nickLinear = new DooUserSettingLinear(this);
        nickLinear.setTitle("昵称");
        nickLinear.setName(nickName);
        nickLinear.setCustomClick(R.id.next_layout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 昵称
                Bundle bundle = new Bundle();
                bundle.putInt(CommonActivity.TAG, CommonActivity.SAVE_NICK);
                startActForResultBundle(CommonActivity.class, bundle, SET_NICK_BACK);
            }
        });
        setting_layout.addView(nickLinear);
        updateLinear = new DooUserSettingLinear(this);
        updateLinear.setTitle("更新题库");
        updateLinear.setCustomClick(R.id.next_layout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateZip();
            }
        });
        setting_layout.addView(updateLinear);

/*        otherLinear = new DooUserSettingLinear(this);
        otherLinear.setTitle("其他");
        otherLinear.setCustomClick(R.id.next_layout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 其他
                ToastUtil.showToast("功能暂未开放");
            }
        });
        setting_layout.addView(otherLinear);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SET_NICK_BACK:
                if (resultCode == CommonActivity.SAVE_NICK) {
                    Bundle bundle = data.getExtras();
                    String nick = (String) bundle.get(SaveNickFragment.TAG);
                    nickLinear.setName(nick);
                    SavePreference.save(BaseApplication.getAppContext(), YSConst.UserInfo.USER_SAVE_NICK, nick);
                    uploadNickName(nick);
                }
                break;
            case SAVE_USER_AVATAR:
                if (resultCode == UserAvatarChooseActivity.AVATAR_SAVE_OK) {
                    Bundle bundle = data.getExtras();
                    String avatarPath = (String) bundle.get(UserAvatarChooseActivity.TAG);
                    if (!TextUtils.isEmpty(avatarPath)) {
                        SavePreference.save(BaseApplication.getAppContext(), YSConst.UserInfo.USER_AVATAR_PATH, avatarPath);
//                        avatarLinear.setAvatar(Uri.parse(avatarPath));
                        avatarLinear.setAvatar(avatarPath);
                        uploadIcon(new File(avatarPath));
                    }
                }
                break;
        }
    }


    private void uploadNickName(final String nickname) {
        Observable<BaseResponse<String>> obsLogin;
        obsLogin = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).
                getSpeUrlService(YSConst.BaseUrl.BASE_URL, UserApi.class).uploadNickName(HRetrofitNetHelper.createReqJsonBody(MapParamsHelper.uploadNickName(nickname)));
        obsLogin.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                        if("1".equals(stringBaseResponse.getCode())){
                            YSUserInfoManager.getsInstance().getUserBean().setNickname(nickname);
                        }
                    }
                });
    }
    private String mPath ="";
    private void uploadIcon(File file) {
        Observable<BaseResponse<String>> obs;
        obs = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).
                getSpeUrlService(YSConst.BaseUrl.BASE_URL, UserApi.class)
                .remoteAvatar(HRetrofitNetHelper.createFileReqJsonBody(file, null));

        obs.flatMap(new Func1<BaseResponse<String>, Observable<BaseResponse<String>>>() {
            @Override
            public Observable<BaseResponse<String>> call(BaseResponse<String> stringBaseResponse) {
                if ("1".equals( stringBaseResponse.getCode())) {
                    String ret = stringBaseResponse.getResults();
                    AvatarBackBean temp  = FastJSONParser.getBean(ret, AvatarBackBean.class);
                    mPath = temp.getPath();
                    return HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).
                            getSpeUrlService(YSConst.BaseUrl.BASE_URL, UserApi.class)
                            .uploadUserAvatar(HRetrofitNetHelper.createReqJsonBody(MapParamsHelper.uploadIcon(temp.getPath())));
                } else {
                    return null;
                }
            }
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                        if("1".equals(stringBaseResponse.getCode())){
                            YSUserInfoManager.getsInstance().getUserBean().setIcon(mPath);
                        }
                    }
                });
    }



    private void updateZip() {
        //TODO 下载更新包
        downloadZip();
    }

    //    public final static String UPGRADE_SAVE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "upgrade.7z";
    public final static String UPGRADE_SAVE_PATH = AssetsHelper.getDiskCacheDir(BaseApplication.getAppContext(),YSConst.UPDATE_ZIP) + File.separator + "upgrade.7z";
    public final static String DOWNLOAD_URL = "http://39.115.27.225/api/upgrade";
    private void downloadZip() {
        rx.Observable<ResponseBody> observable;
        observable = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext())
                .getSpeUrlService(YSConst.BaseUrl.BASE_URL, HomeApi.class)
//                .downZipPacket("http://39.104.118.75/api/upgrade");
                .downZipPacket(DOWNLOAD_URL);

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
                        new Un7zTask(false, UPGRADE_SAVE_PATH,
                                getFilesDir().getParent()+File.separator + YSConst.UPDATE_ZIP).execute();//开始解压压缩包，解压好了就不解压了
                        ToastUtil.showToast("题库更新完毕");
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
