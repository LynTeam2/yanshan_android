package cn.gov.bjys.onlinetrain.bean;

import java.util.List;

/**
 * Created by lisheng.zhou on 2018/8/28.
 */

public class ExamPagerHistoryBean {
    private long examId;//
    private long makeupFlag;//
    private long startTime;//
    private long endTime;//
    private long examScore;//
    private String examName;//
    private List<ExamDetailBean> examDetailList;

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public long getMakeupFlag() {
        return makeupFlag;
    }

    public void setMakeupFlag(long makeupFlag) {
        this.makeupFlag = makeupFlag;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getExamScore() {
        return examScore;
    }

    public void setExamScore(long examScore) {
        this.examScore = examScore;
    }

    public List<ExamDetailBean> getExamDetailList() {
        return examDetailList;
    }

    public void setExamDetailList(List<ExamDetailBean> examDetailList) {
        this.examDetailList = examDetailList;
    }
}
