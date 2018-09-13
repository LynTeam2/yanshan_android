package cn.gov.bjys.onlinetrain.bean;

/**
 * Created by lisheng.zhou on 2018/8/28.
 */

public class ExamDetailBean {
    private long questionId;
    private String questionType;
    private String ajType;
    private String answer;//用户的答案
    private long result;//0没做，1正确，2错误
    private String uid;



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getAjType() {
        return ajType;
    }

    public void setAjType(String ajType) {
        this.ajType = ajType;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public long getResult() {
        return result;
    }

    public void setResult(long result) {
        this.result = result;
    }
}
