package com.ycl.framework.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.utils.util.YisLoger;

import butterknife.ButterKnife;


/**
 * Fragment's framework
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class FrameFragment extends Fragment {

    protected View viewRoot;

    protected boolean isReady = false; // 加载完？

    protected abstract View inflaterView(LayoutInflater inflater,
                                         ViewGroup container, Bundle bundle);
    //return inflater.inflate(R.layout.xxx, container, false);
    protected void  initStatusBar(){

    }
    protected void initViews() {
    }


    protected void initData() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewRoot = inflaterView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, viewRoot);
        isReady = true;
        initStatusBar();
        initViews();
        initData();

        return viewRoot;
    }

    public static <T extends FrameFragment> T newInstance(Class<T> clazz) {
        T fragment = null;
        try {
            fragment = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YisLoger.stateFg(getClass().getName(), "---------onCreateView ");
    }

    @Override
    public void onResume() {
        YisLoger.stateFg(getClass().getName(), "---------onResume ");
        super.onResume();
        if (getUserVisibleHint() && !isHidden()) {
            onVisibilityChanged(true);
        }
    }

    @Override
    public void onPause() {
        YisLoger.stateFg(getClass().getName(), "---------onPause ");
        super.onPause();
        if (getUserVisibleHint() && !isHidden()) {
            onVisibilityChanged(false);
        }
    }

    @Override
    public void onStop() {
        YisLoger.stateFg(getClass().getName(), "---------onStop ");
        super.onStop();

    }

    @Override
    public void onDestroyView() {
//        RequestManager.cancelAll(getClass().getSimpleName());
        YisLoger.stateFg(getClass().getName(), "---------onDestroyView ");
        super.onDestroyView();
        ButterKnife.unbind(this);
        isReady = false;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findViews(View convert, int ids) {
        return (T) convert.findViewById(ids);
    }

    @Override
    public void onDestroy() {
        YisLoger.stateFg(getClass().getName(), "---------onDestroy ");
        super.onDestroy();
        unbindDrawables(viewRoot);
    }

    //接触绑定
    private void unbindDrawables(View view) {
        try {
            if (view == null) return;
            if (view.getBackground() != null) {
                view.getBackground().setCallback(null);
            }
            if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    unbindDrawables(((ViewGroup) view).getChildAt(i));
                }
                ((ViewGroup) view).removeAllViews();
            }
        } catch (Exception ignore) {
        }
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
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        onVisibilityChanged(!hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        onVisibilityChanged(isVisibleToUser);
    }

    public void onVisibilityChanged(boolean visible) {
    }

    protected void showToast(int ids) {
        ToastUtil.showToast(getString(ids));
    }

    protected void showToast(String str){
        ToastUtil.showToast(str);
    }

    public void showProDialog(CharSequence content) {
        ((FrameActivity) getActivity()).showProDialog(content);
    }

    public void dismissProgressDialog() {
        if (getActivity() != null)
            ((FrameActivity) getActivity()).dismissProgressDialog();
    }

    //获取相应的APIService对象
    protected HRetrofitNetHelper getNetHelper() {
        return HRetrofitNetHelper.getInstance(getActivity().getApplicationContext());
    }

    public View getViewRoot() {
        return viewRoot;
    }
}
