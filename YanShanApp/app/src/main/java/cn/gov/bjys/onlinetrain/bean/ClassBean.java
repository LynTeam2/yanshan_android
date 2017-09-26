package cn.gov.bjys.onlinetrain.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by dodozhou on 2017/9/26.
 */
public class ClassBean implements MultiItemEntity {

    public final static int GRID_TYPE = 0;

    private String className;

    private int itemType = GRID_TYPE;
    private int spanSize = 1;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }
}
