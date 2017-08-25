package cn.gov.bjys.onlinetrain.utils.multi_file_download.db.entity;

import android.text.TextUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ycl.framework.db.entity.DBEntity;

import cn.gov.bjys.onlinetrain.utils.multi_file_download.HttpProgressOnNextListener;
import cn.gov.bjys.onlinetrain.utils.multi_file_download.api.DownLoadApi;

/**
 * Created by dodozhou on 2017/8/24.
 */

@DatabaseTable(tableName = "local_download_info")
public class DownLoadInfoBean extends DBEntity {



    @DatabaseField(generatedId = true)
    private long dbId; //数据库自增长id
    /*存储位置*/
    @DatabaseField
    private String savePath;
    /*下载url*/
    @DatabaseField
    private String url;
    /*基础url*/
    @DatabaseField
    private String baseUrl;
    /*唯一的总长度下载地址*/
    @DatabaseField
    private String allUrl;
    /*文件总长度*/
    @DatabaseField
    private long countLength;
    /*下载长度*/
    @DatabaseField
    private long readLength;
    /*下载唯一的HttpService  DownLoadInterface下载接口之母*/
    @DatabaseField
    private DownLoadApi service;
    /*回调监听*/
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private HttpProgressOnNextListener listener;
    /*超时设置*/
    @DatabaseField
    private  int DEFAULT_TIMEOUT = 6;
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

    public DownLoadInfoBean(){

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

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
        return this.baseUrl + this.url;
    }


    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId){
        this.dbId = dbId;
    }

    @Override
    public boolean equals(Object obj) {
     String localUrl = this.baseUrl + this.url;
        if(!TextUtils.isEmpty(obj.toString()) && !TextUtils.isEmpty(localUrl)){
            return localUrl.equals(obj);
        }else{
            return false;
        }
    }
}
