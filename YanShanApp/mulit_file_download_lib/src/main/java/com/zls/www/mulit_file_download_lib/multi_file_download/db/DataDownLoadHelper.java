package com.zls.www.mulit_file_download_lib.multi_file_download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.zls.www.mulit_file_download_lib.multi_file_download.db.entity.DataInfo;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.entity.DownLoadInfoBean;

public class DataDownLoadHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "downLoadInfo.db";
    private static final int DATABASE_VERSION = 1;  //通过更改版本号  进行迭代

    private Map<String, Dao> daos = new HashMap<String, Dao>();

    public DataDownLoadHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
        try {
            TableUtils.createTable(arg1, DownLoadInfoBean.class);
            TableUtils.createTable(arg1, DataInfo.class);
        } catch (SQLException e) {
            Log.e(DataDownLoadHelper.class.getName(), "创建数据库失败", e);
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource arg1, int arg2, int arg3) {
        try {
            TableUtils.dropTable(connectionSource, DownLoadInfoBean.class, true);
            TableUtils.dropTable(connectionSource, DataInfo.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }


    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
