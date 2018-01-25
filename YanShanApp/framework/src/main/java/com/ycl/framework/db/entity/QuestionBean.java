package com.ycl.framework.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dodo on 2018/1/25.
 */
@DatabaseTable(tableName = "local_question_info")
public class QuestionBean extends DBEntity{

    @DatabaseField(generatedId = true)
    private long dbId; //数据库自增长id

    @DatabaseField(columnName = "id")
    private long id;

    @DatabaseField
    private String question;

    @DatabaseField
    private String difficulty;

    @DatabaseField
    private String ajType;

    @DatabaseField
    private String analysis;

    @DatabaseField
    private String answer;

    @DatabaseField
    private String createTime;

    @DatabaseField
    private String updateTime;

    @DatabaseField
    private String choiceA;
    @DatabaseField
    private String choiceB;
    @DatabaseField
    private String choiceC;
    @DatabaseField
    private String choiceD;

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getAjType() {
        return ajType;
    }

    public void setAjType(String ajType) {
        this.ajType = ajType;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }
}
