package com.zls.www.check_version_lib.bean;

/**
 * Created by dodozhou on 2017/9/5.
 */
public class CheckVersionBean {

    private String updateMessage;
    private String url;
    private int versionCode;

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}
