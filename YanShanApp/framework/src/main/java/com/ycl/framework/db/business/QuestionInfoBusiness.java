package com.ycl.framework.db.business;

import android.content.Context;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ycl.framework.db.entity.ExamBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dodo on 2018/1/25.
 */

public class QuestionInfoBusiness extends  BaseDbBusiness<ExamBean> {

    private static QuestionInfoBusiness instance = null;

    private QuestionInfoBusiness(Context context) {
        super(context);
        try {
            dao = helper.getDao(ExamBean.class);
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

    public List<ExamBean> queryAll(){
        QueryBuilder<ExamBean, Integer> qb = dao.queryBuilder();
        List<ExamBean> list;
        try {
           list =  qb.query();
           if(list != null && list.size() > 0){
               return list;
           }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.clearObjectCache();
            try {
                dao.closeLastIterator();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  new ArrayList<>();
    }


    //有就更新 没有就插入
    public void createOrUpdate(ExamBean qb) {
        try {
            ExamBean cache = queryBykey(qb.getUid()+"");
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

    public ExamBean queryBykey(String key) {
        return queryBykey("uid", key);
    }

    public ExamBean queryBykey(String key, Object values) {
        QueryBuilder<ExamBean, Integer> qb = dao.queryBuilder();
        List<ExamBean> list;
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
        return new ExamBean();
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
