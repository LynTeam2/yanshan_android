package cn.gov.bjys.onlinetrain.bean;


import com.chad.library.adapter.base.entity.MultiItemEntity;

//维护题目列表的展示bean 记录题目的完成情况
public class ExamXqBean implements MultiItemEntity {

    public final static int NOMAL = 0;//未选择
    public final static int CHOICE = 1;//目前选择状态
    public final static int RIGHT = 2;//题目对的状态
    public final static int FAIL = 3;//题目错误的状态


    private int mType = NOMAL;
    private int mPosition = 0;//位置position也是内容
    private int mSpanSize = 1;//

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public int getmSpanSize() {
        return mSpanSize;
    }

    public void setmSpanSize(int mSpanSize) {
        this.mSpanSize = mSpanSize;
    }

    @Override
    public int getItemType() {
        return mType;
    }
}
