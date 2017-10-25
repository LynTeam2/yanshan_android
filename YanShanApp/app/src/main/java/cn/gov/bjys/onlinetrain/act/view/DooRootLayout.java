package cn.gov.bjys.onlinetrain.act.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by Administrator on 2017/10/15 0015.
 */
public abstract class DooRootLayout extends AutoRelativeLayout {
    public DooRootLayout(Context context) {
        super(context);
        init();
    }

    public DooRootLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DooRootLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DooRootLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        addView(getRootView());
        initViews();
    }

    public abstract void initViews();

    public abstract View getRootView();
}
