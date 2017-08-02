package com.ycl.framework.db.business;

import android.content.Context;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ycl.framework.db.entity.UserInfoBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserInfoBusiness extends BaseDbBusiness<UserInfoBean> {
    private static UserInfoBusiness instance = null;

    private UserInfoBusiness(Context context) {
        super(context);
        try {
            dao = helper.getDao(UserInfoBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserInfoBusiness getInstance(Context context) {
        if (instance == null) {
            instance = new UserInfoBusiness(context);
        }
        return instance;
    }


    //有就更新 没有就插入
    public void createOrUpdate(UserInfoBean nc) {
        try {
            UserInfoBean cache = queryBykey(nc.getToken());
            if (cache != null) {
                nc.setDbId(cache.getDbId());
            }
            dao.createOrUpdate(nc);
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
    }

    public UserInfoBean queryBykey(String key) {
        QueryBuilder<UserInfoBean, Integer> qb = dao.queryBuilder();
        List<UserInfoBean> list;

        try {
            qb.where().eq("token", key);
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
        return null;
    }


    //删除   根据id
    public void deleteItemWithId(long ids) {
        DeleteBuilder<UserInfoBusiness, Integer> deleteBuilder = dao.deleteBuilder();
        try {
            deleteBuilder.where().eq("id", ids);
            deleteBuilder.delete();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dao.clearObjectCache();
            try {
                dao.closeLastIterator();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean containsItemWithId(long key) {
        QueryBuilder<UserInfoBean, Integer> qb = dao.queryBuilder();
        List<UserInfoBean> list;

        try {
            qb.where().eq("id", key);
            list = qb.query();//dao.query(qb.prepare());
            if (list.size() > 0) {
                return true;
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
        return false;
    }

}
