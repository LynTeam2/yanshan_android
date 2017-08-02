package com.ycl.framework.bean;

/**
 * banner 广告 bean
 */
public class BannerBean {

    private int id;
    private String title;
    private String img_path1;//图片地址  web
    private String img_path2;//图片地址  手机
    private String img_url1;
    private String img_url2;
    private String img_url3;
    private String go_field; //跳转type  点击图片跳转字段名，枚举值有：imgurl1、imgurl2、imgurl3

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_path1() {
        return img_path1;
    }

    public void setImg_path1(String img_path1) {
        this.img_path1 = img_path1;
    }

    public String getImg_path2() {
        return img_path2;
    }

    public void setImg_path2(String img_path2) {
        this.img_path2 = img_path2;
    }

    public String getImg_url1() {
        return img_url1;
    }

    public void setImg_url1(String img_url1) {
        this.img_url1 = img_url1;
    }

    public String getImg_url2() {
        return img_url2;
    }

    public void setImg_url2(String img_url2) {
        this.img_url2 = img_url2;
    }

    public String getImg_url3() {
        return img_url3;
    }

    public void setImg_url3(String img_url3) {
        this.img_url3 = img_url3;
    }

    public String getGo_field() {
        return go_field;
    }

    public void setGo_field(String go_field) {
        this.go_field = go_field;
    }

    //获取 跳转标志  imgUrl值
    public String getGoTag() {
        switch (this.go_field) {
            case "imgurl1":
                return this.img_url1;
            case "imgurl2":
                return this.img_url2;
            case "imgurl3":
                return this.img_url3;
            default:
                return "1";
        }
    }

}
