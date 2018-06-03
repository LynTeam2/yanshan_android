package com.zls.www.mulit_file_download_lib.multi_file_download.db.business;

import android.content.Context;
import android.text.TextUtils;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ycl.framework.base.FrameApplication;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.entity.DataInfo;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.entity.DownLoadInfoBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DownLoadInfoBusiness extends BaseDbBusiness<DownLoadInfoBean> {

    public final static String TAG = DownLoadInfoBusiness.class.getSimpleName();

    private static DownLoadInfoBusiness instance = null;

    protected DownLoadInfoBusiness(Context context) {
        super(context);
        try {
            dao = helper.getDao(DownLoadInfoBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DownLoadInfoBusiness getInstance(Context context) {
        if (instance == null) {
            instance = new DownLoadInfoBusiness(context);
        }
        return instance;
    }


    public void addDownLoadInfo(DataInfo di){
        DownLoadInfoBean bean = new DownLoadInfoBean();
        bean.setDataInfo(di);
        List<DownLoadInfoBean>  beanList = new ArrayList<>();
        try {
           beanList = dao.queryBuilder().
                    where().
                    eq("data_info_bean", di.getAllUrl())
                   .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(beanList.size() > 0){
            bean.setDbId(beanList.get(0).getDbId());
        }
        try {
            //先将datainfo加入数据库
            DataInfoBusiness.getInstance(FrameApplication.getFrameContext()).addDownInfo(bean.getDataInfo());
            //再把downLoadingInfobean加入数据库
            dao.createOrUpdate(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 利用唯一下载url确定保存的bean
     * @param url
     * @return
     */
    public DownLoadInfoBean queryBykey(String  url) {
        return queryBykey("data_info_bean", url);
    }

    /**
     * 自由查询  where key = values
     * @param key
     * @param values
     * @return
     */

    public DownLoadInfoBean queryBykey(String key, Object values) {
        QueryBuilder<DownLoadInfoBean, Integer> qb = dao.queryBuilder();
        List<DownLoadInfoBean> list;
        try {
            qb.where().eq(key, values);
            list = qb.query();//dao.query(qb.prepare());
            if (list.size() > 0) {
                //恢复 DownLoadInfoBean 里面的  datainfo
                DownLoadInfoBean bean  = list.get(0);
                DataInfoBusiness.getInstance(FrameApplication.getFrameContext()).dao.refresh(bean.getDataInfo());
                return bean;
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
        return new DownLoadInfoBean();
    }



    //删除  DownLoadInfoBean 根据allUrl
    public void deleteItemWithAllUrl(String url) {
        //先删除DownLoadInfoBean表
        DeleteBuilder<DownLoadInfoBusiness, Integer> deleteBuilder = dao.deleteBuilder();
        try {
            deleteBuilder.where().eq("data_info_bean", url);
            deleteBuilder.delete();
            //删除完了之后  删除DataInfo表
            DataInfoBusiness.getInstance(FrameApplication.getFrameContext()).deleteItemWithAllUrl(url);
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


    public boolean containsItemWithAllUrl(String allUrl) {
        QueryBuilder<DownLoadInfoBean, Integer> qb = dao.queryBuilder();
        List<DownLoadInfoBean> list;

        try {
            qb.where().eq("dataInfo", allUrl);
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
