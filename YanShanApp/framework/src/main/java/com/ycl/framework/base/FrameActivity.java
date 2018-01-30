package com.ycl.framework.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.ycl.framework.R;
import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.ycl.framework.utils.util.SystemBarTintManager;
import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.utils.util.YisLoger;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;

//自适应布局 autoLayoutActivity
public abstract class FrameActivity extends AutoLayoutActivity {

    protected ProgressDialog mProgressDialog;//进度条
    private long progressDialogId;
    public HRetrofitNetHelper retrofitNetHelper;
    private OnActResultListener resultListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameActivityStack.create().addActivity(this);
        setRootView(); // 必须放在annotate之前调用
        ButterKnife.bind(this);
        initStatusBar();
        retrofitNetHelper = HRetrofitNetHelper.getInstance(getApplicationContext());
        if (initBaseParams(savedInstanceState)) {
            initViews();
            initData();
        }
    }

    protected abstract void setRootView();

    //初始化 状态栏
    protected void initStatusBar() {
        // fragmentManager中    <item name="android:fitsSystemWindows">false</item> 改变主题
        // 再给子布局  加 fitsSystemWindows = false
        setTranslucentStatus(true);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(false);
//        tintManager.setStatusBarTintColor(Color.parseColor("#FFC0312D"));
        tintManager.setStatusBarTintColor(Color.BLACK);
    }

    //设置透明度
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @SuppressWarnings("unchecked")
    public <T extends FrameFragment> T
    getFragment(String tag, Class<T> clazz, int ids) {
        T framgnt = (T) getSupportFragmentManager().findFragmentByTag(tag);
        if (framgnt == null)
            framgnt = FrameFragment.newInstance(clazz);
//        if (!framgnt.isAdded())
//            getSupportFragmentManager().beginTransaction().add(ids, framgnt, tag).commit();
        return framgnt;
    }

    //activity重置  数据重置
    protected boolean initBaseParams(Bundle savedInstanceState) {
        if (savedInstanceState != null) {

        }
        return true;
    }

    public void initData() {
    }

    public void initViews() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); //友盟统计
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        YisLoger.state(this.getClass().getName(), "---------  onRestart ");
    }


    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        FrameActivityStack.create().removeActivityStack(this);
        //此处不需要tag  Observable 内部处理异常
//        HRetrofitNetHelper.getInstance(getApplicationContext()).cancelCallsWithTag(getClass().getSimpleName());
        super.onDestroy();
//        unbindDrawables(((ViewGroup)findViewById(android.R.id.content)).getChildAt(0));
    }

    /**
     * 通过Class跳转界面 *
     */
    public void startAct(Class<?> cls) {
        startAct(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面 *
     */
    public void startAct(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivityForResult(intent, requestCode);
    }

    public void startActForResultBundle(Class<?> cls, Bundle mBundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (mBundle != null) {
            intent.putExtras(mBundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected void showToast(String text) {
        ToastUtil.showToast(text);
    }

    protected void showToast(int ids) {
        ToastUtil.showToast(getString(ids));
    }

    /**
     * 频繁的toast *
     */
    private Toast mToast2;

    //自定义 toast
    public void showWarmToast(String text) {
        if (TextUtils.isEmpty(text))
            return;
        if (mToast2 == null) {
            mToast2 = new Toast(getApplicationContext());
            mToast2.setGravity(Gravity.CENTER, 0, 0);
            mToast2.setView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.toast_photo_center_layout, null));
        }
        ((TextView) mToast2.getView().findViewById(R.id.tv_toast_warm_str)).setText(text);
        mToast2.setDuration(Toast.LENGTH_SHORT);
        mToast2.show();
    }

    //自定义 res  icon_toast_success_warm
    public void showWarmToast(String text, int resId) {
        if (TextUtils.isEmpty(text))
            return;
        if (mToast2 == null) {
            mToast2 = new Toast(getApplicationContext());
            mToast2.setGravity(Gravity.CENTER, 0, 0);
        }
        mToast2.setView(LayoutInflater.from(this).inflate(R.layout.toast_photo_center_layout, null));
        ((ImageView) mToast2.getView().findViewById(R.id.iv_toast_warm)).setImageResource(resId);
        ((TextView) mToast2.getView().findViewById(R.id.tv_toast_warm_str)).setText(text);
        mToast2.setDuration(Toast.LENGTH_SHORT);
        mToast2.show();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, null, "数据请求中...");
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
        } else
            mProgressDialog.show();
    }

    public void showProDialog(CharSequence content) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, null, content);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
        } else {
            mProgressDialog.setMessage(content);
            mProgressDialog.show();
        }
    }


    //带dismissListener
    public void showProDialog(CharSequence content, DialogInterface.OnDismissListener dismissListener) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, null, content);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
        } else {
            mProgressDialog.setMessage(content);
            mProgressDialog.show();
        }
        mProgressDialog.setOnDismissListener(dismissListener);
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    //进度弹窗是否显示
    protected boolean progressDialogIsShow() {
        if (mProgressDialog != null)
            return mProgressDialog.isShowing();
        return false;
    }

    //缩小动画
    public void finishZoom() {
        super.finish();
        overridePendingTransition(0, R.anim.zoom_exit);
    }

    public void startZoom(Class<?> cls, Bundle bundle) {
        startAct(cls, bundle);
        overridePendingTransition(R.anim.zoom_enter, 0);
    }

//    public void getData() {
//        retrofitNetHelper.getAPIService(UserApi.class).
//                getUserLogin(HRetrofitNetHelper.createReqBody(MapParamsHelper.getLoginMaps()))
// .doOnNext(new Action1<User>() {
//@Override
//public void call(User user) {
//    processUser(user);    //在异步线程里
//}).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<BaseResp<UserLoginBean>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        YisLoger.debug(e.getMessage());
//
//                    }
//
//                    @Override
//                    public void onNext(BaseResp<UserLoginBean> userLoginBeanBaseResp) {
//                        String code = userLoginBeanBaseResp.getCode();
//                    }
//                });
//    }


    //获取相应的APIService对象
    public <T> T getServiceApi(Class<T> service) {
        return retrofitNetHelper.getAPIService(service);
    }

    public <T> T getSpeServiceApi(String base_url, Class<T> service) {
        return retrofitNetHelper.getSpeUrlService(base_url, service);
    }

    //提取方法
    public void setText(@IdRes int viewId, CharSequence text) {
        TextView view = this.findViews(viewId);
        view.setText(text);
    }

    public void setImgs(@IdRes int viewId, int backgroundResId) {
        ImageView view = this.findViews(viewId);
        view.setImageResource(backgroundResId);
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViews(@IdRes int viewId) {
        return (T) findViewById(viewId);
    }


    //回调执行接口
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultListener != null)
            resultListener.callResultListener(requestCode, resultCode, data);
    }


    public void setResultListener(OnActResultListener resultListener) {
        this.resultListener = resultListener;
    }

    public interface OnActResultListener {

        void callResultListener(int requestCode, int resultCode, Intent data);
    }
}
