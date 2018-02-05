package cn.gov.bjys.onlinetrain.bean;

import com.ycl.framework.db.entity.ExamBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/27 0027.
 */
public class CourseBean {

    private int id;//  1,
    private String courseName;//" : "课程123",
    private String ajType;//" : "危险化学品",
    private String courseType;//" : "1", // 1/2 1阅读 2视频
    private String content;// "阅读内容/视频连接",
    private String introduction;//
    private String icon;
    private String iconName;
    private String createTime;
    private String updateTime;
    private boolean homePage;//true

    private List<ExamBean> mcList;
    private List<ExamBean> scList;
    private List<ExamBean> tfList;

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

    public boolean isHomePage() {
        return homePage;
    }

    public void setHomePage(boolean homePage) {
        this.homePage = homePage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAjType() {
        return ajType;
    }

    public void setAjType(String ajType) {
        this.ajType = ajType;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
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
}
