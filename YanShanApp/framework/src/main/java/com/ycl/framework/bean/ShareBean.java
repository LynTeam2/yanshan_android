package com.ycl.framework.bean;

/**
 * 分享 Bean on 2016/10/24.
 */
public class ShareBean {
    private static long latestFeedId;

    private int typeShare; //分享类型
    private String title;//标题

    private String content;//内容

    private String imageUrl; // 图片url

    private int imageUrlType;//图片url   (0 本地  1  远程)

    private String tagUrl; // 目标url

    private byte[] thumb;// 缩略图

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTagUrl() {
        return tagUrl;
    }

    public void setTagUrl(String tagUrl) {
        this.tagUrl = tagUrl;
    }

    public byte[] getThumb() {
        return thumb;
    }

    public void setThumb(byte[] thumb) {
        this.thumb = thumb;
    }

    public int getTypeShare() {
        return typeShare;
    }

    public void setTypeShare(int typeShare) {
        this.typeShare = typeShare;
    }

    public int getImageUrlType() {
        return imageUrlType;
    }

    public void setImageUrlType(int imageUrlType) {
        this.imageUrlType = imageUrlType;
    }

    public static void setLatestFeedId(long latestFeedId) {
        ShareBean.latestFeedId = latestFeedId;
    }

    public static long getLatestFeedId() {
        return latestFeedId;
    }
}
