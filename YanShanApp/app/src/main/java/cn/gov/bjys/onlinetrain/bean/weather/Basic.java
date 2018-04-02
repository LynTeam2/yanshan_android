package cn.gov.bjys.onlinetrain.bean.weather;

/**
 * Created by Administrator on 2018/4/1 0001.
 */
public class Basic {

    private String cid;// : "CN101010100"
    private String location;// : "北京"
    private String parent_city;// : "北京"
    private String admin_area;// : "北京"
    private String cnty;// : "中国"
    private String lat;// : "39.90498734"
    private String lon;// : "116.40528870"
    private String tz;//: "8.0"

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParent_city() {
        return parent_city;
    }

    public void setParent_city(String parent_city) {
        this.parent_city = parent_city;
    }

    public String getAdmin_area() {
        return admin_area;
    }

    public void setAdmin_area(String admin_area) {
        this.admin_area = admin_area;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }
}
