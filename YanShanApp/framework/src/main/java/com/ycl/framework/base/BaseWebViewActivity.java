package com.ycl.framework.base;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ycl.framework.R;
import com.ycl.framework.view.ProgressWebView;
import com.ycl.framework.view.TitleHeaderView;

import java.lang.reflect.Method;


/**
 * 网页(WebView) Activity on 2016/1/25.
 */
public abstract class BaseWebViewActivity extends FrameActivity {
    protected TitleHeaderView tvTitle;

    protected ProgressWebView webView;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_webview);
    }

    @Override
    public void initViews() {
        initWebViews();
    }

    @Override
    public void initData() {
    }

    public abstract void setWebClient();

    public static String TOP_XLC = ";topxlc";

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViews() {
        tvTitle = findViews(R.id.title_view_activity_web_view);
        webView = findViews(R.id.web_view_act);
//        webView.setFocusable(true);
//        webView.requestFocus();
//        webView.clearCache(true);
        WebSettings settings = webView.getSettings();
        //本APP需要
//        settings.setUserAgentString(settings.getUserAgentString());
        String userAgent = settings.getUserAgentString();
        settings.setUserAgentString(userAgent + TOP_XLC);

        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(false);
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= 8) {
            settings.setPluginState(WebSettings.PluginState.ON);
        } else {
            //settings.setPluginState(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            settings.setBuiltInZoomControls(true);
            settings.setDisplayZoomControls(false);
        } else {
            try {
                Class<?> webview = Class.forName("android.webkit.WebView");
                Method method = webview.getMethod("getZoomButtonsController");
                method.invoke(this, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
/*        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });*/
        setWebClient();
    }

    public boolean onKeyDown(int keyCoder, KeyEvent event) {
        if (webView.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
            webView.goBack(); //goBack()表示返回webView的上一页面
            return true;
        } else if (keyCoder == KeyEvent.KEYCODE_BACK) {
            finish();// 按了返回键，但已经不能返回，则执行退出确认
            return true;
        }
        return super.onKeyDown(keyCoder, event);
    }

    protected void onDestroy() {
        if (webView != null) {
            webView.setVisibility(View.GONE);
            webView.removeAllViews();
            webView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
