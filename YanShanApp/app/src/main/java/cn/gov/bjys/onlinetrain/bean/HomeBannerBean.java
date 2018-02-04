package cn.gov.bjys.onlinetrain.bean;

/**
 * Created by dodozhou on 2017/9/25.
 */
public class HomeBannerBean {
   private String createTime;//": 1517573969000,
      private int id;//": 1,
       private String imgName;//": "banner_1.png",
        private String link;//: "",
       private String  path;//": "banner_1.png"

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
