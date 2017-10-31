package cn.gov.bjys.onlinetrain.bean;


import android.os.Parcel;

import com.chad.library.adapter.base.entity.MultiItemEntity;

//维护题目列表的展示bean 记录题目的完成情况
public class ExamXqBean extends ExamBean implements MultiItemEntity {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mType);
        dest.writeInt(this.mPosition);
        dest.writeInt(this.mSpanSize);
    }

    public ExamXqBean() {
    }



    protected ExamXqBean(Parcel in) {
        super(in);
        this.mType = in.readInt();
        this.mPosition = in.readInt();
        this.mSpanSize = in.readInt();
    }

    public static final Creator<ExamXqBean> CREATOR = new Creator<ExamXqBean>() {
        @Override
        public ExamXqBean createFromParcel(Parcel source) {
            return new ExamXqBean(source);
        }

        @Override
        public ExamXqBean[] newArray(int size) {
            return new ExamXqBean[size];
        }
    };
}
