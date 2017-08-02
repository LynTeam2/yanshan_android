package com.ycl.framework.db.business;

import android.content.Context;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ycl.framework.base.FrameApplication;
import com.ycl.framework.db.entity.UserDetailBean;
import com.ycl.framework.utils.sp.SavePreference;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dodozhou on 2017/3/17.
 */
public class UserDetailInfoBusiness extends BaseDbBusiness<UserDetailBean> {


    private static UserDetailInfoBusiness instance = null;

    private UserDetailInfoBusiness(Context context) {
        super(context);
        try {
            dao = helper.getDao(UserDetailBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserDetailInfoBusiness getInstance(Context context) {
        if (instance == null) {
            instance = new UserDetailInfoBusiness(context);
        }
        return instance;
    }


    //有就更新 没有就插入
    public void createOrUpdate(UserDetailBean nc) {
        try {
            UserDetailBean cache = queryBykey("user_id", nc.getUser_id() + "");
            if (cache != null) {
                nc.setDbId(cache.getDbId());
            }
            //补救方案   存 user_id
            SavePreference.save(FrameApplication.getFrameContext(), "local_user_id", nc.getUser_id() + "");
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

    public UserDetailBean queryBykey(String key) {
        return queryBykey("user_id", key);
    }

    public UserDetailBean queryBykey(String key, Object values) {
        QueryBuilder<UserDetailBean, Integer> qb = dao.queryBuilder();
        List<UserDetailBean> list;

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
        return new UserDetailBean();
    }


    //删除   根据id
    public void deleteItemWithId(long ids) {
        DeleteBuilder<UserDetailInfoBusiness, Integer> deleteBuilder = dao.deleteBuilder();
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
        QueryBuilder<UserDetailBean, Integer> qb = dao.queryBuilder();
        List<UserDetailBean> list;

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
