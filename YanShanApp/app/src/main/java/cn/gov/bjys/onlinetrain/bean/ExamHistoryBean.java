package cn.gov.bjys.onlinetrain.bean;

/**
 * Created by lisheng.zhou on 2018/8/28.
 */

public class ExamHistoryBean {
    private long id;
    private long userId;
    private long unitId;
    private long examId;
    private int makeupFlag;
    private String startTime;
    private String endTime;
    private String createTime;
    private int examScore;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public int getMakeupFlag() {
        return makeupFlag;
    }

    public void setMakeupFlag(int makeupFlag) {
        this.makeupFlag = makeupFlag;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getExamScore() {
        return examScore;
    }

    public void setExamScore(int examScore) {
        this.examScore = examScore;
    }
}
