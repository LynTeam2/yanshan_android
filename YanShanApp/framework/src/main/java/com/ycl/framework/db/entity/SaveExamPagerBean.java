package com.ycl.framework.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2018/1/30 0030.
 */

@DatabaseTable(tableName = "local_exam_pager_info")
public class SaveExamPagerBean  extends DBEntity{
    @DatabaseField(generatedId = true,columnName = "dbId")
    private long dbId; //数据库自增长id

    @DatabaseField(columnName = "userid")
    private String userid;

    @DatabaseField(columnName = "exampagerid")
    private long exampagerid;


    @DatabaseField
    private String mAllPager;//整张试卷
    @DatabaseField
    private String mErrorPager;//试卷错误部分
    @DatabaseField
    private String mRightPager;//试卷正确部分
    @DatabaseField
    private String mNotDoPager;//试卷未做部分

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
}
