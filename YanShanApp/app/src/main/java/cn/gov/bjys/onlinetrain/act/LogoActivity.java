package cn.gov.bjys.onlinetrain.act;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.util.ToastUtil;
import com.zls.www.statusbarutil.StatusBarUtil;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.utils.PermissionUtil;
import cn.gov.bjys.onlinetrain.utils.YSConst;

/**
 * Created by Administrator on 2017/8/2 0002.
 */
public class LogoActivity extends FrameActivity {
    public final static String TAG = LogoActivity.class.getSimpleName();

    @Bind(R.id.count_down)
    Button mCountDown;

    @Bind(R.id.main_content)
    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_logo_layout);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this,StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    @Override
    public void initViews() {
        super.initViews();
        requestPermiss();
        mCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countDownTimer != null){
                    countDownTimer.cancel();
                }
                startNextActivity();
                LogoActivity.this.finish();
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

                mCountDown.setText(--count+"秒后可进入");
                Log.d(TAG,count+"秒后可进入");
            }

            @Override
            public void onFinish() {
                startNextActivity();
                if(countDownTimer != null) {
                    countDownTimer.cancel();
                }
                LogoActivity.this.finish();
            }
        };
        countDownTimer.start();
    }


    public void startNextActivity() {
        boolean notFirstLogin = SavePreference.getBoolean(this, YSConst.NOT_FIRST_LOGIN);
        if (notFirstLogin) {
            startAct(LoginActivity.class);
        } else {
            startAct(GuideActivity.class);
        }
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 0x01;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    private void requestPermiss() {
        PermissionUtil.checkPermission(LogoActivity.this,mCoordinatorLayout , PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE, new PermissionUtil.permissionInterface() {
            @Override
            public void success() {
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
                if (!PermissionUtil.shouldShowPermissions(this,permissions)) {//这个返回false 表示勾选了不再提示
                     showSnackBar(mCoordinatorLayout, "请去设置界面设置权限","去设置");
                } else {
                    //表示没有权限 ,但是没勾选不再提示
                    showSnackBar(mCoordinatorLayout, "没有权限无法更新最新课程内容!","去设置");

                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showSnackBar(View view,String content, String action){
        Snackbar snackbar = Snackbar.make(view, content,
                Snackbar.LENGTH_INDEFINITE);
        if(action != null){
            snackbar.setAction(action, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startToSetting();
                }
            });
        }
        snackbar.show();
    }

    public static int  REQUEST_PERMISSION_SEETING = 0x02;
    private void startToSetting(){
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

    private void reqPermissionRefreshUi(boolean reqOk){
        initCountDownTimer();
        if(reqOk){
            updateZip();
        }
    }

    int count = 0;
    private void updateZip(){
        //TODO 下载更新包
        count++ ;
        Log.d("dodoT","updateZip  " + count);
        ToastUtil.showToast("updateZip  " + count);
    }

}
