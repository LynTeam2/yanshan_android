package com.ycl.framework.base;

/**
 * 消息数量 Event on 16/6/14.
 */
public class MsgCountEvent extends BaseEvent {

    //type = 1 重置  type = 2 ; 数量减少
    private int  msgTotal; //总数

    public int getMsgTotal() {
        return msgTotal;
    }

    public void setMsgTotal(int msgTotal) {
        this.msgTotal = msgTotal;
    }

}
