package cn.gov.bjys.onlinetrain.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dodozhou on 2017/9/28.
 */
//试题类型
public class ExamBean implements Parcelable {

    public final static int VIDEO_EXAM = 0;//视频题目
    public final static int TEXT_SINGLE_EXAM = 1;//单选题
    public final static int TEXT_MULTIPLE_EXAM = 2;//多选题
    public final static int TEXT_JUDGMENT_EXAM = 3;//判断题

    private int type;

    public ExamBean(){

    }


    protected ExamBean(Parcel in) {
        type = in.readInt();
    }

    public static final Creator<ExamBean> CREATOR = new Creator<ExamBean>() {
        @Override
        public ExamBean createFromParcel(Parcel in) {
            return new ExamBean(in);
        }

        @Override
        public ExamBean[] newArray(int size) {
            return new ExamBean[size];
        }
    };

    public static int getVideoExam() {
        return VIDEO_EXAM;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
    }
}
