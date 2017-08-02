package com.ycl.framework.view.viewflipper;

import android.view.View;

import com.ycl.framework.base.FrameActivity;

import butterknife.ButterKnife;


/**
 * ViewFlipper 基础类 on 2015/6/19.
 */
public abstract class BaseVFlipper {

    protected VfChangeListener changeListener;
    protected View mView;
    protected FrameActivity activity;

    public BaseVFlipper(View contentView) {
        ButterKnife.bind(this, contentView);
        mView = contentView;
        activity = (FrameActivity) mView.getContext();
        initViews();
    }

    /**
     * 初始化View
     */
    public abstract void initViews();

    public void setChangeListener(VfChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    /**
     * ViewFlipper切换 Interface
     */
    public interface VfChangeListener {
        void changeVf(int position);
    }
}
