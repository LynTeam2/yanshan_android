package com.ycl.framework.db.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2018/1/30 0030.
 */

@DatabaseTable(tableName = "local_exam_pager_info")
public class SaveExamPagerBean  extends DBEntity implements Parcelable{
    @DatabaseField(generatedId = true,columnName = "dbId")
    private long dbId; //数据库自增长id

    @DatabaseField(columnName = "userid")
    private String userid;

    @DatabaseField(columnName = "exampagerid")
    private long exampagerid;


    @DatabaseField
    private long createTime;//考试时间

    @DatabaseField
    private String examName;//试卷名称

    @DatabaseField
    private String mAllPager;//整张试卷
    @DatabaseField
    private String mErrorPager;//试卷错误部分
    @DatabaseField
    private String mRightPager;//试卷正确部分
    @DatabaseField
    private String mNotDoPager;//试卷未做部分
    @DatabaseField
    private long mScore;//分数
    @DatabaseField
    private boolean mJige;//及格

    @DatabaseField
    private long useTimes;//考试花费时长 单位秒

    public long getUseTimes() {
        return useTimes;
    }

    public void setUseTimes(long useTimes) {
        this.useTimes = useTimes;
    }

    public long getmScore() {
        return mScore;
    }

    public void setmScore(long mScore) {
        this.mScore = mScore;
    }

    public boolean ismJige() {
        return mJige;
    }

    public void setmJige(boolean mJige) {
        this.mJige = mJige;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public long getExampagerid() {
        return exampagerid;
    }

    public void setExampagerid(long exampagerid) {
        this.exampagerid = exampagerid;
    }

    public String getmAllPager() {
        return mAllPager;
    }

    public void setmAllPager(String mAllPager) {
        this.mAllPager = mAllPager;
    }

    public String getmErrorPager() {
        return mErrorPager;
    }

    public void setmErrorPager(String mErrorPager) {
        this.mErrorPager = mErrorPager;
    }

    public String getmRightPager() {
        return mRightPager;
    }

    public void setmRightPager(String mRightPager) {
        this.mRightPager = mRightPager;
    }

    public String getmNotDoPager() {
        return mNotDoPager;
    }

    public void setmNotDoPager(String mNotDoPager) {
        this.mNotDoPager = mNotDoPager;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.dbId);
        dest.writeString(this.userid);
        dest.writeLong(this.exampagerid);
        dest.writeLong(this.createTime);
        dest.writeString(this.examName);
        dest.writeString(this.mAllPager);
        dest.writeString(this.mErrorPager);
        dest.writeString(this.mRightPager);
        dest.writeString(this.mNotDoPager);
        dest.writeLong(this.mScore);
        dest.writeByte(this.mJige ? (byte) 1 : (byte) 0);
        dest.writeLong(this.useTimes);
    }

    public SaveExamPagerBean() {
    }

    protected SaveExamPagerBean(Parcel in) {
        this.dbId = in.readLong();
        this.userid = in.readString();
        this.exampagerid = in.readLong();
        this.createTime = in.readLong();
        this.examName = in.readString();
        this.mAllPager = in.readString();
        this.mErrorPager = in.readString();
        this.mRightPager = in.readString();
        this.mNotDoPager = in.readString();
        this.mScore = in.readLong();
        this.mJige = in.readByte() != 0;
        this.useTimes = in.readLong();
    }

    public static final Creator<SaveExamPagerBean> CREATOR = new Creator<SaveExamPagerBean>() {
        @Override
        public SaveExamPagerBean createFromParcel(Parcel source) {
            return new SaveExamPagerBean(source);
        }

        @Override
        public SaveExamPagerBean[] newArray(int size) {
            return new SaveExamPagerBean[size];
        }
    };
}
