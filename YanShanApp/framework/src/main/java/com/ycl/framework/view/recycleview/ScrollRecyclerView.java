package com.ycl.framework.view.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.ycl.framework.bean.ScrollBean;
import com.ycl.framework.utils.util.YisLoger;

import org.greenrobot.eventbus.EventBus;

/**
 * fix 修复 scrollTo的 异常抛出 by yuchaoliang on 16/5/20.
 */
public class ScrollRecyclerView extends RecyclerView {
    public ScrollRecyclerView(Context context) {
        super(context);
    }

    public ScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    //解决    ecyclerView does not support scrolling to an absolute position
    @Override
    public void scrollTo(int x, int y) {
        // super.scrollTo(x, y);
        YisLoger.logTagE("ScrollRecyclerView", "ScrollRecyclerView does not support scrolling to an absolute position.");
        // Either don't call super here or call just for some phones, or try catch it. From default implementation we have removed the Runtime Exception trown
    }


    private float lastY = -1;
    private float deltaY = -1;

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                lastY = ev.getY();
//                break;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }


//    public static final int CHANGE_RATIO = 3;
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//
//                YisLoger.debug("scrollbean     onTouch   " + getScrollY());
//                if (getScrollY() != 0) {
//                    deltaY = 0;
//                    lastY = ev.getY();
//                } else {
//                    deltaY = ev.getY() - lastY;
//                    if (deltaY > 0) {
//                        //下滑
//                        setT((int) -deltaY / CHANGE_RATIO);
//                    }
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                EventBus.getDefault().post(new ScrollBean(getScrollY(), 2));
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }


    public void setT(int t) {
        if (t < 0) {
            animatePull(t);
        }
    }

    private void animatePull(int t) {
        EventBus.getDefault().post(new ScrollBean(t, 1));
    }


    public float getDeltaY() {
        return deltaY;
    }
}
