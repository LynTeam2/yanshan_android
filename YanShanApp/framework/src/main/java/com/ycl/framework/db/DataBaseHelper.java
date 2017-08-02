package com.ycl.framework.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ycl.framework.db.entity.UserDetailBean;
import com.ycl.framework.db.entity.VideoBean;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "localCache.db";
    private static final int DATABASE_VERSION = 10;  //通过更改版本号  进行迭代

    private Map<String, Dao> daos = new HashMap<String, Dao>();

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
        try {
            TableUtils.createTable(arg1, UserDetailBean.class);
            TableUtils.createTable(arg1, VideoBean.class);
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "创建数据库失败", e);
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource arg1, int arg2, int arg3) {
        try {
            TableUtils.dropTable(connectionSource, UserDetailBean.class, true);
            TableUtils.dropTable(connectionSource, VideoBean.class, true);
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
