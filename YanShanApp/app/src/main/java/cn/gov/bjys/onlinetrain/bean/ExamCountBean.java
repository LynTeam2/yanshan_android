package cn.gov.bjys.onlinetrain.bean;

/**
 * Created by lisheng.zhou on 2018/8/28.
 */

public class ExamCountBean {
    private long examCount;
    private boolean isValid;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public long getExamCount() {
        return examCount;
    }

    public void setExamCount(long examCount) {
        this.examCount = examCount;
    }
}
