package com.ycl.framework.db.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dodo on 2018/1/25.
 */
@DatabaseTable(tableName = "local_question_info")
public class ExamBean extends DBEntity implements Parcelable {

    public final static int TEXT_JUDGMENT_EXAM = 0;//判断题
    public final static int TEXT_SINGLE_EXAM = 1;//单选题
    public final static int TEXT_MULTIPLE_EXAM = 2;//多选题
    public final static int VIDEO_EXAM = 3;//视频题目


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
    @DatabaseField
    private int questionType;

    private boolean isDeal = false;//是否做题

    private List<Integer> mChoose = new ArrayList<>();

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public boolean isDeal() {
        return isDeal;
    }

    public void setDeal(boolean deal) {
        isDeal = deal;
    }

    public List<Integer> getmChoose() {
        return mChoose;
    }

    public void setmChoose(List<Integer> mChoose) {
        this.mChoose = mChoose;
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.dbId);
        dest.writeLong(this.id);
        dest.writeString(this.question);
        dest.writeString(this.difficulty);
        dest.writeString(this.ajType);
        dest.writeString(this.analysis);
        dest.writeString(this.answer);
        dest.writeString(this.createTime);
        dest.writeString(this.updateTime);
        dest.writeString(this.choiceA);
        dest.writeString(this.choiceB);
        dest.writeString(this.choiceC);
        dest.writeString(this.choiceD);
        dest.writeInt(this.questionType);
        dest.writeByte(this.isDeal ? (byte) 1 : (byte) 0);
        dest.writeList(this.mChoose);
    }

    public ExamBean() {
    }

    protected ExamBean(Parcel in) {
        this.dbId = in.readLong();
        this.id = in.readLong();
        this.question = in.readString();
        this.difficulty = in.readString();
        this.ajType = in.readString();
        this.analysis = in.readString();
        this.answer = in.readString();
        this.createTime = in.readString();
        this.updateTime = in.readString();
        this.choiceA = in.readString();
        this.choiceB = in.readString();
        this.choiceC = in.readString();
        this.choiceD = in.readString();
        this.questionType = in.readInt();
        this.isDeal = in.readByte() != 0;
        this.mChoose = new ArrayList<Integer>();
        in.readList(this.mChoose, Integer.class.getClassLoader());
    }

    public static final Creator<ExamBean> CREATOR = new Creator<ExamBean>() {
        @Override
        public ExamBean createFromParcel(Parcel source) {
            return new ExamBean(source);
        }

        @Override
        public ExamBean[] newArray(int size) {
            return new ExamBean[size];
        }
    };
}
