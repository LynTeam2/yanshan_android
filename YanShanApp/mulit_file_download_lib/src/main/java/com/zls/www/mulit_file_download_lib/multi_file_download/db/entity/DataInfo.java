package com.zls.www.mulit_file_download_lib.multi_file_download.db.entity;

import android.text.TextUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ycl.framework.db.entity.DBEntity;

import com.zls.www.mulit_file_download_lib.multi_file_download.manager.HttpProgressOnNextListener;
import com.zls.www.mulit_file_download_lib.multi_file_download.api.DownLoadApi;




@DatabaseTable(tableName = "data_info")
public class DataInfo  extends DBEntity {

    /*存储位置*/
    @DatabaseField(canBeNull = true)
    private String savePath;
    @DatabaseField(id = true,columnName = "main_url")
    private String allUrl;
    /*文件总长度*/
    @DatabaseField
    private long countLength;
    /*下载长度*/
    @DatabaseField
    private long readLength;
    /*下载唯一的HttpService  DownLoadInterface下载接口之母*/
    private DownLoadApi service;
    /*回调监听*/
    private HttpProgressOnNextListener listener;
    /*超时设置*/
    @DatabaseField
    private  int DEFAULT_TIMEOUT = 6;
    /*下载接口的Uri地址*/
    @DatabaseField
    private String downLoadInterfaceUri;
    /*下载状态*/
    @DatabaseField
    private DownState state;



    public enum  DownState {
        START,
        DOWN,
        PAUSE,
        STOP,
        ERROR,
        FINISH,
    }


    public void setAllUrl(String url) {
        this.allUrl = url;
    }

    public String getDownLoadInterfaceUri() {
        return downLoadInterfaceUri;
    }

    public void setDownLoadInterfaceUri(String downLoadInterfaceUri) {
        this.downLoadInterfaceUri = downLoadInterfaceUri;
    }

    public int getDEFAULT_TIMEOUT() {
        return DEFAULT_TIMEOUT;
    }

    public void setDEFAULT_TIMEOUT(int DEFAULT_TIMEOUT) {
        this.DEFAULT_TIMEOUT = DEFAULT_TIMEOUT;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getBaseUrl() {
//        return baseUrl;
//    }
//
//    public void setBaseUrl(String baseUrl) {
//        this.baseUrl = baseUrl;
//    }

    public long getCountLength() {
        return countLength;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }

    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public DownLoadApi getService() {
        return service;
    }

    public void setService(DownLoadApi service) {
        this.service = service;
    }

    public HttpProgressOnNextListener getListener() {
        return listener;
    }

    public void setListener(HttpProgressOnNextListener listener) {
        this.listener = listener;
    }

    public DownState getState() {
        return state;
    }

    public void setState(DownState state) {
        this.state = state;
    }

    public String getAllUrl() {
        return this.allUrl;
    }



    @Override
    public boolean equals(Object obj) {
        String localUrl = allUrl;
        if(!TextUtils.isEmpty(obj.toString()) && !TextUtils.isEmpty(localUrl)){
            return localUrl.equals(obj);
        }else{
            return false;
        }
    }

}
