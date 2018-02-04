package cn.gov.bjys.onlinetrain.bean;

import java.util.HashMap;

/**
 * Created by dodo on 2018/1/29.
 */

public class ExamsRole {
    private HashMap<String, Integer> ajType;
    private HashMap<String, Integer> questionType;
    private HashMap<String, Integer> difficulty;

    public HashMap<String, Integer> getAjType() {
        return ajType;
    }

    public void setAjType(HashMap<String, Integer> ajType) {
        this.ajType = ajType;
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
