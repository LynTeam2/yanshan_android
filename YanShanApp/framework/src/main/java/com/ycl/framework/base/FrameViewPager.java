package com.ycl.framework.base;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * 自定义ViewPager（Frame）  手势拦截 on 2015/6/12.
 */
public class FrameViewPager extends ViewPager {

    private boolean isIntercept = false;

    public FrameViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMyScroller();
        setOffscreenPageLimit(1);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isIntercept)
            return false;//拦截事件返回false，子view不受影响
        return super.onInterceptTouchEvent(arg0);
    }

    /**
     * 拦截手势tag
     */
    public void setIsIntercept(boolean params) {
        isIntercept = params;
    }

    public void setMyScroller() {
        try {
            //反射获取属性
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new MyScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDuration(boolean isScroll) {
        if (isScroll) {
            mDuration = 500;
        } else {
            mDuration = 0;
        }
    }

    private int mDuration = 500;

    public class MyScroller extends Scroller {
        public MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            //super.startScroll(startX, startY, dx, dy, 0 /*1 secs*/);//没有滑动时间了
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }

}
