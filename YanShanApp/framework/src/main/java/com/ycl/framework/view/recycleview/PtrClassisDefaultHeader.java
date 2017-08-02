package com.ycl.framework.view.recycleview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ycl.framework.R;
import com.ycl.framework.R.id;
import com.ycl.framework.R.string;
import com.ycl.framework.R.styleable;

import java.text.SimpleDateFormat;
import java.util.Date;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


/**
 * ptr自定义类  on 2015/11/13.
 */
public class PtrClassisDefaultHeader extends FrameLayout implements PtrUIHandler {
    private int mRotateAniTime = 150;
    private RotateAnimation mFlipAnimation;
    private RotateAnimation mReverseFlipAnimation;
    private View mRotateView;   //选择的view
    private ProgressBar mProgressBar;
    private boolean mShouldShowLastUpdate;

    public PtrClassisDefaultHeader(Context context) {
        this(context, null);
    }

    public PtrClassisDefaultHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrClassisDefaultHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initViews(attrs);
    }

    protected void initViews(AttributeSet attrs) {
        TypedArray arr = this.getContext().obtainStyledAttributes(attrs, styleable.PtrClassicHeader, 0, 0);
        if (arr != null) {
            this.mRotateAniTime = arr.getInt(0, this.mRotateAniTime);
        }

        this.buildAnimation();
        View header = LayoutInflater.from(this.getContext()).inflate(R.layout.ptr_classic_custom_header, this);
        this.mRotateView = header.findViewById(id.ptr_classic_header_rotate_view);
        this.mProgressBar = (ProgressBar) header.findViewById(id.ptr_classic_header_rotate_view_progressbar);
        this.resetView();
    }

    public void setRotateAniTime(int time) {
        if (time != this.mRotateAniTime && time != 0) {
            this.mRotateAniTime = time;
            this.buildAnimation();
        }
    }

    private void buildAnimation() {
        mFlipAnimation = new RotateAnimation(0.0F, -180.0F, 1, 0.5F, 1, 0.5F);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration((long) this.mRotateAniTime);
        mFlipAnimation.setFillAfter(true);
        mReverseFlipAnimation = new RotateAnimation(-180.0F, 0.0F, 1, 0.5F, 1, 0.5F);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration((long) this.mRotateAniTime);
        mReverseFlipAnimation.setFillAfter(true);
    }

    private void resetView() {
        this.hideRotateView();
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    //重置旋转
    private void hideRotateView() {
        this.mRotateView.setVisibility(GONE);
        ViewCompat.setRotation(this.mRotateView, 0);
    }

    public void onUIReset(PtrFrameLayout frame) {
        this.resetView();
        this.mShouldShowLastUpdate = true;
    }

    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        this.mShouldShowLastUpdate = true;
        this.mProgressBar.setVisibility(View.INVISIBLE);
        this.mRotateView.setVisibility(View.VISIBLE);

    }

    //开始刷新(手势释放)
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        this.mShouldShowLastUpdate = false;
        this.hideRotateView();
        this.mProgressBar.setVisibility(View.VISIBLE);


    }

    //刷新完成回调
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        this.hideRotateView();
        this.mProgressBar.setVisibility(View.INVISIBLE);

    }

    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        int mOffsetToRefresh = frame.getOffsetToRefresh();
        int currentPos = ptrIndicator.getCurrentPosY();
        int lastPos = ptrIndicator.getLastPosY();

//        YisLoger.logTagI("onUIPositionChange", "   mOffsetToRefresh   " + mOffsetToRefresh + "    currentPos  " + currentPos + "  lastPos " + lastPos);

        ViewCompat.setRotation(this.mRotateView, lastPos);

    }

    //刷新状态
    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
        }

    }

    //
    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {

    }


    public void setLastUpdateTimeKey(String key) {
    }
}
