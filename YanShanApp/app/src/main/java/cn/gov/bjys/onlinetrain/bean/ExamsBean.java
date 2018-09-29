package cn.gov.bjys.onlinetrain.bean;

import com.ycl.framework.db.entity.ExamBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/28 0028.
 */
public class ExamsBean {

    private List<CourseBean> courseList;
    private long id;// : 1,
    private String examName;//" : "测试1",
    private String startDate;// : "",
    private String endDate;// : "",
    private String createTime;//: "",
    private String updateTime;// : "",
    private String examType;//综合测验
    private String introduction;//本次考试是为了考察
    private int standard;//需要答对题目的数量 及格标准
    private int examDuration;//分钟单位 考试的时长
    private String role;
    private List<ExamBean> mcList;
    private List<ExamBean> scList;
    private List<ExamBean> tfList;
    private int examTfCount = 0;
    private int examScCount = 0;
    private int examMcCount = 0;


    public int getExamTfCount() {
        return examTfCount;
    }

    public void setExamTfCount(int examTfCount) {
        this.examTfCount = examTfCount;
    }

    public int getExamScCount() {
        return examScCount;
    }

    public void setExamScCount(int examScCount) {
        this.examScCount = examScCount;
    }

    public int getExamMcCount() {
        return examMcCount;
    }

    public void setExamMcCount(int examMcCount) {
        this.examMcCount = examMcCount;
    }

    public List<CourseBean> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseBean> courseList) {
        this.courseList = courseList;
    }

    public int getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(int examDuration) {
        this.examDuration = examDuration;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<ExamBean> getMcList() {
        return mcList;
    }

    public void setMcList(List<ExamBean> mcList) {
        this.mcList = mcList;
    }

    public List<ExamBean> getScList() {
        return scList;
    }

    public void setScList(List<ExamBean> scList) {
        this.scList = scList;
    }

    public List<ExamBean> getTfList() {
        return tfList;
    }

    public void setTfList(List<ExamBean> tfList) {
        this.tfList = tfList;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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


    public ExamsBean deepClone(){
        ExamsBean clone = new ExamsBean();
        clone.setCourseList(new ArrayList<>(this.courseList));
        clone.setCreateTime(this.createTime);
        clone.setEndDate(this.endDate);
        clone.setExamDuration(this.examDuration);
        clone.setExamMcCount(this.examMcCount);
        clone.setExamScCount(this.examScCount);
        clone.setExamTfCount(this.examTfCount);
        clone.setExamName(this.examName);
        clone.setStartDate(this.startDate);
        clone.setExamType(this.examType);
        clone.setId(this.id);
        clone.setIntroduction(this.introduction);
        clone.setRole(this.role);
        clone.setStandard(this.standard);
        clone.setMcList(new ArrayList<ExamBean>(this.mcList));
        clone.setTfList(new ArrayList<ExamBean>(this.tfList));
        clone.setScList(new ArrayList<ExamBean>(this.scList));
        clone.setUpdateTime(this.updateTime);
        return clone;
    }
}
