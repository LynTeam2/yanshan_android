package cn.gov.bjys.onlinetrain.utils.multi_file_download.db.business;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.ycl.framework.db.DataBaseHelper;
import com.ycl.framework.db.entity.DBEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Db操作base abstract类 on 2015/10/14.
 */
public abstract class BaseDbBusiness<T extends DBEntity> {

    private final String TAG = "DBBusiness";
    protected Dao dao;

    protected DataBaseHelper helper;

    protected BaseDbBusiness(Context context) {
        helper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
    }

    public void insert(T entity) {
        try {
            dao.create(entity);
        } catch (SQLException ignored) {
        } finally {
            dao.clearObjectCache();
            try {
                dao.closeLastIterator();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void modify(T entity) {
        try {
            dao.update(entity);
        } catch (SQLException e) {
            Log.e(TAG, "修改失败");
        } finally {
            dao.clearObjectCache();
            try {
                dao.closeLastIterator();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public List<T> queryAll() {
        List<T> list = new ArrayList<>();
        try {
            list = dao.queryForAll();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "查询失败");
        } finally {
            dao.clearObjectCache();
            try {
                dao.closeLastIterator();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void delete(T entity) {
        try {
            dao.delete(entity);
        } catch (SQLException e) {
            Log.e(TAG, "删除失败");
        } finally {
            dao.clearObjectCache();
            try {
                dao.closeLastIterator();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteById(int id) {
        try {
            dao.deleteById(id);
        } catch (SQLException e) {
            Log.e(TAG, "删除失败");
        } finally {
            dao.clearObjectCache();
            try {
                dao.closeLastIterator();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
