package cn.gov.bjys.onlinetrain.bean;

import java.util.HashMap;

/**
 * Created by dodo on 2018/1/29.
 */

public class ExamsRole {
    private HashMap<String, Integer> anjianType;
    private HashMap<String, Integer> questionType;
    private HashMap<String, Integer> difficulty;

    public HashMap<String, Integer> getAnjianType() {
        return anjianType;
    }

    public void setAnjianType(HashMap<String, Integer> anjianType) {
        this.anjianType = anjianType;
    }

    public HashMap<String, Integer> getQuestionType() {
        return questionType;
    }

    public void setQuestionType(HashMap<String, Integer> questionType) {
        this.questionType = questionType;
    }

    public HashMap<String, Integer> getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(HashMap<String, Integer> difficulty) {
        this.difficulty = difficulty;
    }
}
