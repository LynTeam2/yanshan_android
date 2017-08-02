package com.ycl.framework.base;

/**
 * EventBus 的Base类 on 2015/7/10.
 */
public class BaseEvent {
    protected int typeEvent;   //条件过滤

    protected String typeTag;// 条件过滤tag

    public String getTypeTag() {
        return typeTag;
    }

    public void setTypeTag(String typeTag) {
        this.typeTag = typeTag;
    }

    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public BaseEvent() {
    }
}
