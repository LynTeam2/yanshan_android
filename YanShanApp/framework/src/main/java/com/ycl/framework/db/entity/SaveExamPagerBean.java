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

    @DatabaseField(columnName = "create_time")
    private long createTime;//考试时间

    @DatabaseField
    private String examName = "";//试卷名称

    @DatabaseField
    private String mAllPager = "";//整张试卷
    @DatabaseField
    private String mErrorPager = "";//试卷错误部分
    @DatabaseField
    private String mRightPager = "";//试卷正确部分
    @DatabaseField
    private String mNotDoPager = "";//试卷未做部分
    @DatabaseField
    private String mMultiErrorPager = "";//多选错题
    @DatabaseField
    private String mTrueFalseErrorPager = "";//是非错题部分
    @DatabaseField
    private String mSimpleErrorPager = "";//单选错误部分
    @DatabaseField
    private String mMultiRightPager = "";//多选正确题
    @DatabaseField
    private String mTrueFalseRightPager = "";//是非正确部分
    @DatabaseField
    private String mSimpleRightPager = "";//单选正确部分
    @DatabaseField
    private String mMultiPager = "";//多选
    @DatabaseField
    private String mTrueFalsePager = "";//是非
    @DatabaseField
    private String mSimplePager = "";//单选
    @DatabaseField
    private long mScore;//分数
    @DatabaseField
    private boolean mJige;//及格

    @DatabaseField
    private long useTimes;//考试花费时长 单位秒



    protected SaveExamPagerBean(Parcel in) {
        dbId = in.readLong();
        userid = in.readString();
        exampagerid = in.readLong();
        createTime = in.readLong();
        examName = in.readString();
        mAllPager = in.readString();
        mErrorPager = in.readString();
        mRightPager = in.readString();
        mNotDoPager = in.readString();
        mMultiErrorPager = in.readString();
        mTrueFalseErrorPager = in.readString();
        mSimpleErrorPager = in.readString();
        mMultiRightPager = in.readString();
        mTrueFalseRightPager = in.readString();
        mSimpleRightPager = in.readString();
        mMultiPager = in.readString();
        mTrueFalsePager = in.readString();
        mSimplePager = in.readString();
        mScore = in.readLong();
        mJige = in.readByte() != 0;
        useTimes = in.readLong();
    }

    public static final Creator<SaveExamPagerBean> CREATOR = new Creator<SaveExamPagerBean>() {
        @Override
        public SaveExamPagerBean createFromParcel(Parcel in) {
            return new SaveExamPagerBean(in);
        }

        @Override
        public SaveExamPagerBean[] newArray(int size) {
            return new SaveExamPagerBean[size];
        }
    };

    public String getmMultiRightPager() {
        return mMultiRightPager;
    }

    public void setmMultiRightPager(String mMultiRightPager) {
        this.mMultiRightPager = mMultiRightPager;
    }

    public String getmTrueFalseRightPager() {
        return mTrueFalseRightPager;
    }

    public void setmTrueFalseRightPager(String mTrueFalseRightPager) {
        this.mTrueFalseRightPager = mTrueFalseRightPager;
    }

    public String getmSimpleRightPager() {
        return mSimpleRightPager;
    }

    public void setmSimpleRightPager(String mSimpleRightPager) {
        this.mSimpleRightPager = mSimpleRightPager;
    }

    public String getmSimplePager() {
        return mSimplePager;
    }

    public void setmSimplePager(String mSimplePager) {
        this.mSimplePager = mSimplePager;
    }

    public String getmTrueFalsePager() {
        return mTrueFalsePager;
    }

    public void setmTrueFalsePager(String mTrueFalsePager) {
        this.mTrueFalsePager = mTrueFalsePager;
    }

    public String getmMultiPager() {
        return mMultiPager;
    }

    public void setmMultiPager(String mMultiPager) {
        this.mMultiPager = mMultiPager;
    }

    public String getmSimpleErrorPager() {
        return mSimpleErrorPager;
    }

    public void setmSimpleErrorPager(String mSimpleErrorPager) {
        this.mSimpleErrorPager = mSimpleErrorPager;
    }

    public String getmMultiErrorPager() {
        return mMultiErrorPager;
    }

    public void setmMultiErrorPager(String mMultiErrorPager) {
        this.mMultiErrorPager = mMultiErrorPager;
    }

    public String getmTrueFalseErrorPager() {
        return mTrueFalseErrorPager;
    }

    public void setmTrueFalseErrorPager(String mTrueFalseErrorPager) {
        this.mTrueFalseErrorPager = mTrueFalseErrorPager;
    }

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


    public SaveExamPagerBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(dbId);
        dest.writeString(userid);
        dest.writeLong(exampagerid);
        dest.writeLong(createTime);
        dest.writeString(examName);
        dest.writeString(mAllPager);
        dest.writeString(mErrorPager);
        dest.writeString(mRightPager);
        dest.writeString(mNotDoPager);
        dest.writeString(mMultiErrorPager);
        dest.writeString(mTrueFalseErrorPager);
        dest.writeString(mSimpleErrorPager);
        dest.writeString(mMultiRightPager);
        dest.writeString(mTrueFalseRightPager);
        dest.writeString(mSimpleRightPager);
        dest.writeString(mMultiPager);
        dest.writeString(mTrueFalsePager);
        dest.writeString(mSimplePager);
        dest.writeLong(mScore);
        dest.writeByte((byte) (mJige ? 1 : 0));
        dest.writeLong(useTimes);
    }
}
