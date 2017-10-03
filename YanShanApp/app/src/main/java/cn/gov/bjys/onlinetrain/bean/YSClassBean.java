package cn.gov.bjys.onlinetrain.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by dodo on 2017/10/3.
 */
public class YSClassBean implements MultiItemEntity {

    public final static int GRID_ITEM_2 = 2;

    private String name;//课程名称
    private String mImgUrl;//图片路径
    private int span;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getmImgUrl() {
        return mImgUrl;
    }

    public void setmImgUrl(String mImgUrl) {
        this.mImgUrl = mImgUrl;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}