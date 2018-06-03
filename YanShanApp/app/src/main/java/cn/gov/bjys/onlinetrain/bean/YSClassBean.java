package cn.gov.bjys.onlinetrain.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;


public class YSClassBean implements MultiItemEntity {


    public final static int GRID_COLUMN_2 = 2;

    public final static int LINEAR_COLUMN_1 = 1;

    private String name;//课程名称
    private String mImgUrl;//图片路径
    private String content;//内容


    private int spanSize = 1;
    private int mItemType = GRID_COLUMN_2;

    public YSClassBean(){

    }

    public YSClassBean(int spanSize, int type){
        this.spanSize = spanSize;
        this.mItemType = type;
    }

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

    public int getmItemType() {
        return mItemType;
    }

    public void setmItemType(int mItemType) {
        this.mItemType = mItemType;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
