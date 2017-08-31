package cn.gov.bjys.onlinetrain.utils.multi_file_download.db.business;

import android.content.Context;
import android.text.TextUtils;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ycl.framework.base.FrameApplication;
import com.ycl.framework.db.business.UserDetailInfoBusiness;
import com.ycl.framework.db.entity.UserDetailBean;
import com.ycl.framework.utils.sp.SavePreference;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.gov.bjys.onlinetrain.utils.multi_file_download.db.entity.DataInfo;
import cn.gov.bjys.onlinetrain.utils.multi_file_download.db.entity.DownLoadInfoBean;

/**
 * Created by dodozhou on 2017/8/24.
 */
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
            dao.createOrUpdate(bean);
//            helper.getDao(DataInfo.class).createOrUpdate(bean.getDataInfo());
//            helper.getDao(DataInfo.class).refresh(bean.getDataInfo());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //有就更新 没有就插入
    public void createOrUpdate(DataInfo nc) {

        DownLoadInfoBean bean = new DownLoadInfoBean();
        try {
            DownLoadInfoBean cache = queryBykey("data_info_bean", nc.getAllUrl());
            if (cache.getDataInfo() != null && !TextUtils.isEmpty(cache.getDataInfo().getAllUrl())) {
                bean.setDbId(cache.getDbId());
            }
            bean.setDataInfo(nc);
            dao.createOrUpdate(bean);
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
        return new DownLoadInfoBean();
    }


    //删除   根据id
    public void deleteItemWithAllUrl(String url) {
        DeleteBuilder<DownLoadInfoBusiness, Integer> deleteBuilder = dao.deleteBuilder();
        try {
            deleteBuilder.where().eq("dataInfo", url);
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
