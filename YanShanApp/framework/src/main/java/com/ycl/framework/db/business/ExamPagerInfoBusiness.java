package com.ycl.framework.db.business;

import android.content.Context;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ycl.framework.db.entity.ExamBean;
import com.ycl.framework.db.entity.SaveExamPagerBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30 0030.
 */
public class ExamPagerInfoBusiness extends  BaseDbBusiness<SaveExamPagerBean> {
    private static ExamPagerInfoBusiness instance = null;

    private ExamPagerInfoBusiness(Context context) {
        super(context);
        try {
            dao = helper.getDao(ExamBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ExamPagerInfoBusiness getInstance(Context context) {
        if (instance == null) {
            instance = new ExamPagerInfoBusiness(context);
        }
        return instance;
    }

    public List<SaveExamPagerBean> queryAll(){
        QueryBuilder<SaveExamPagerBean, Integer> qb = dao.queryBuilder();
        List<SaveExamPagerBean> list;
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
    public void createOrUpdate(SaveExamPagerBean qb) {
        try {
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

    public List<SaveExamPagerBean> queryBykey(String userid,long pagerid) {
        return queryBykey("userid", userid, "exampagerid",pagerid);
    }

    public List<SaveExamPagerBean> queryBykey(String key, Object values,String key2,Object values2) {
        QueryBuilder<SaveExamPagerBean, Integer> qb = dao.queryBuilder();
        List<SaveExamPagerBean> list;
        try {
            qb.where().eq(key, values).and().eq(key2,values2);
            qb.orderBy("dbId",false);
            list = qb.query();//dao.query(qb.prepare());
            if (list.size() > 0) {
                return list;
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
            return new ArrayList<>();
    }

    //删除   根据userid pagerid
    public void deleteItemWithId(String ids,long pagerid) {
        DeleteBuilder<ExamPagerInfoBusiness, Integer> deleteBuilder = dao.deleteBuilder();
        try {
            deleteBuilder.where().
                    eq("userid", ids)
            .and().eq("exampagerid",pagerid);
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
