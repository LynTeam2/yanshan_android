package com.ycl.framework.db.business;

import android.content.Context;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ycl.framework.db.entity.VideoBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class VideoInfoBusiness extends BaseDbBusiness<VideoBean> {
    private static VideoInfoBusiness instance = null;

    private VideoInfoBusiness(Context context) {
        super(context);
        try {
            dao = helper.getDao(VideoBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static VideoInfoBusiness getInstance(Context context) {
        if (instance == null) {
            instance = new VideoInfoBusiness(context);
        }
        return instance;
    }


    //有就更新 没有就插入
    public void createOrUpdate(VideoBean nc) {
        try {
            VideoBean cache = queryBykey(nc.getId());
            if (cache != null) {
                nc.setLoacl_id(cache.getLoacl_id());
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

    public VideoBean queryBykey(long key) {
        QueryBuilder<VideoBean, Integer> qb = dao.queryBuilder();
        List<VideoBean> list;

        try {
            qb.where().eq("video_local_key", key);
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
        DeleteBuilder<VideoInfoBusiness, Integer> deleteBuilder = dao.deleteBuilder();
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
