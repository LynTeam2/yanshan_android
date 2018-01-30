package cn.gov.bjys.onlinetrain.bean;

/**
 * Created by Administrator on 2018/1/28 0028.
 */
public class ExamsBean {
   private long id;// : 1,
   private String examName;//" : "测试1",
    private String startDate;// : "",
    private String endDate;// : "",
    private String createTime;//: "",
    private String updateTime;// : "",
    private String icon;//
    private String content;//

    private ExamsRole role;

    public ExamsRole getRole() {
        return role;
    }

    public void setRole(ExamsRole role) {
        this.role = role;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
