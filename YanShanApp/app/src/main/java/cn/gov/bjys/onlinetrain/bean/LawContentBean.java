package cn.gov.bjys.onlinetrain.bean;

import java.util.List;

/**
 * Created by dodo on 2018/5/4.
 */

public class LawContentBean {
    private String createTime;// : "2018-05-04"
    private String fileName;//: "异构数据库同步复制技术研究与实现.pdf"
    private String filePath;// : "http://39.104.118.75/resource/uploadFile/1525424140679.pdf"
    private long id;//: 4
    private String lawName;// : "打完的"
    private String lawType;//: "0"
    private String updateTime;// : "2018-05-04"


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLawName() {
        return lawName;
    }

    public void setLawName(String lawName) {
        this.lawName = lawName;
    }

    public String getLawType() {
        return lawType;
    }

    public void setLawType(String lawType) {
        this.lawType = lawType;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public static class Second{
        private List<LawContentBean>  content;

        public List<LawContentBean> getContent() {
            return content;
        }

        public void setContent(List<LawContentBean> content) {
            this.content = content;
        }
    }


    public static class First{
        private Second laws;

        public Second getLaws() {
            return laws;
        }

        public void setLaws(Second laws) {
            this.laws = laws;
        }
    }
}
