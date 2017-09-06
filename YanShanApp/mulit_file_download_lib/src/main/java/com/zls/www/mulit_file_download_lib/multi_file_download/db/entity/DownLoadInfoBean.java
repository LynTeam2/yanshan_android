package com.zls.www.mulit_file_download_lib.multi_file_download.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ycl.framework.db.entity.DBEntity;

/**
 * Created by dodozhou on 2017/8/24.
 */

@DatabaseTable(tableName = "local_download_info")
public class DownLoadInfoBean extends DBEntity {



    @DatabaseField(generatedId = true)
    private long dbId; //数据库自增长id

    @DatabaseField(canBeNull = true,foreign = true, columnName = "data_info_bean")
    private DataInfo dataInfo;//保存下载中断后的数据




    public DownLoadInfoBean(){

    }

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public DataInfo getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(DataInfo dataInfo) {
        this.dataInfo = dataInfo;
    }

    @Override
    public boolean equals(Object obj) {
            return  dataInfo.equals(obj);
    }
}
