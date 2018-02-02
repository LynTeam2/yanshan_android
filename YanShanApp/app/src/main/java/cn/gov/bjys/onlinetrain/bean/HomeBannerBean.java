package cn.gov.bjys.onlinetrain.bean;

/**
 * Created by dodozhou on 2017/9/25.
 */
public class HomeBannerBean {

    private int id;//" : 1,
    private String link;//" : "", //连接
    private String path;//" : "", //图片地址
    private String position;//" : "", //暂时没用，可以不考虑
    private String imgName;//" : "", //图片名称
    private String createTime;//" : ""

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
