package com.ycl.framework.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.ycl.framework.utils.util.SystemBarTintManager;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by joeYu on 17/3/10.
 */

public class AutoFitSystemWindowView extends AutoRelativeLayout {

    private int statusHeight = 0;

    public AutoFitSystemWindowView(Context context) {
        this(context, null);
    }

    public AutoFitSystemWindowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoFitSystemWindowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (statusHeight == 0) {
            statusHeight = SystemBarTintManager.getStatusHeight(getContext());
        }
        setPadding(getPaddingLeft(), statusHeight, getPaddingRight(), getPaddingBottom());
    }


}
