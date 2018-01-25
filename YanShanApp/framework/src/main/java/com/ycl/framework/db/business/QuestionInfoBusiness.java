package com.ycl.framework.db.business;

import android.content.Context;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ycl.framework.base.FrameApplication;
import com.ycl.framework.db.entity.QuestionBean;
import com.ycl.framework.db.entity.UserDetailBean;
import com.ycl.framework.utils.sp.SavePreference;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dodo on 2018/1/25.
 */

public class QuestionInfoBusiness extends  BaseDbBusiness<QuestionBean> {

    private static QuestionInfoBusiness instance = null;

    private QuestionInfoBusiness(Context context) {
        super(context);
        try {
            dao = helper.getDao(QuestionBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static QuestionInfoBusiness getInstance(Context context) {
        if (instance == null) {
            instance = new QuestionInfoBusiness(context);
        }
        return instance;
    }


    //有就更新 没有就插入
    public void createOrUpdate(QuestionBean qb) {
        try {
            QuestionBean cache = queryBykey(qb.getId()+"");
            if (cache.getId() > 0){
                qb.setDbId(cache.getDbId());
            }
            dao.createOrUpdate(qb);
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

    public QuestionBean queryBykey(String key) {
        return queryBykey("id", key);
    }

    public QuestionBean queryBykey(String key, Object values) {
        QueryBuilder<QuestionBean, Integer> qb = dao.queryBuilder();
        List<QuestionBean> list;
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
        return new QuestionBean();
    }

    //删除   根据id
    public void deleteItemWithId(long ids) {
        DeleteBuilder<QuestionInfoBusiness, Integer> deleteBuilder = dao.deleteBuilder();
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

}
