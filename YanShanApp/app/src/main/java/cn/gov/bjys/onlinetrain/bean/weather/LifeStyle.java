package cn.gov.bjys.onlinetrain.bean.weather;

/**
 * Created by Administrator on 2018/4/1 0001.
 */
public class LifeStyle {
  private String   brf;// : "舒适"
  private String   txt;// : "今天夜间不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"
  private String   type;// : "comf"

    public String getBrf() {
        return brf;
    }

    public void setBrf(String brf) {
        this.brf = brf;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
