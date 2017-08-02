package com.ycl.framework.bean;

/**
 * 滚动 bean by joeYu on 16/7/29.
 */
@Deprecated
public class ScrollBean {
    private int scrollY;

    private int touchType;//收拾类型   1 ：move 2 up

    public ScrollBean(int scrollY, int touchType) {
        this.scrollY = scrollY;
        this.touchType = touchType;
    }

    public int getScrollY() {
        return scrollY;
    }

    public void setScrollY(int scrollY) {
        this.scrollY = scrollY;
    }


    public int getTouchType() {
        return touchType;
    }

    public void setTouchType(int touchType) {
        this.touchType = touchType;
    }
}
