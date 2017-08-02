package com.ycl.framework.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 相册 EventBus 回调 Entity(实体类) 2015/11/2.
 */
public class AlbumEntityEBus<T> extends BaseEvent {

    private ArrayList<T> datas;

    private String tagName;
    private int topicId;

    @SafeVarargs
    public AlbumEntityEBus(T... bean) {
        datas = new ArrayList<>();
        //添加所有T类型-->List
        Collections.addAll(datas, bean);
    }

    public AlbumEntityEBus(ArrayList<T> datas) {
        this.datas = datas;
    }

    public AlbumEntityEBus(List<T> imgs) {
        datas = new ArrayList<>();
        for (T bean : imgs) {
            this.datas.add(bean);
        }
    }

    public T getItem(int position) {
        if (position < datas.size()) {
            return datas.get(position);
        }
        return null;
    }

    public ArrayList<T> getDatas() {
        return datas;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }
}
