package com.zls.www.mulit_file_download_lib.multi_file_download.db.business;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.zls.www.mulit_file_download_lib.multi_file_download.db.entity.DataInfo;

/**
 * Created by dodozhou on 2017/8/30.
 */
public class DataInfoBusiness  extends BaseDbBusiness<DataInfo>  {

    public final static String TAG = DataInfoBusiness.class.getSimpleName();

    private static DataInfoBusiness instance = null;

    private Dao<DataInfo, String> mDataInfoDao;

    protected DataInfoBusiness(Context context) {
        super(context);
        try {
            dao = helper.getDao(DataInfo.class);
            mDataInfoDao = helper.getDao(DataInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataInfoBusiness getInstance(Context context) {
        if (instance == null) {
            instance = new DataInfoBusiness(context);
        }
        return instance;
    }


    public void addDownInfo(DataInfo di){
        try {
            dao.createOrUpdate(di);
//            helper.getDao(DataInfo.class).createOrUpdate(bean.getDataInfo());
//            helper.getDao(DataInfo.class).refresh(bean.getDataInfo());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 利用唯一下载url确定保存的bean
     * @param url
     * @return
     */
    public DataInfo queryBykey(String  url) {
        return queryBykey("main_url", url);
    }

    /**
     * 自由查询  where key = values
     * @param key
     * @param values
     * @return
     */

    public DataInfo queryBykey(String key, Object values) {
        QueryBuilder<DataInfo, String> qb = dao.queryBuilder();
        List<DataInfo> list;
        try {
            qb.where().eq(key, values);
            list = qb.query();//dao.query(qb.prepare());
            if (list.size() > 0) {
                return list.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.clearObjectCache();
            try {
                dao.closeLastIterator();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new DataInfo();
    }
}
