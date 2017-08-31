package cn.gov.bjys.onlinetrain.utils.multi_file_download.db.business;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import cn.gov.bjys.onlinetrain.utils.multi_file_download.db.entity.DataInfo;
import cn.gov.bjys.onlinetrain.utils.multi_file_download.db.entity.DownLoadInfoBean;

/**
 * Created by dodozhou on 2017/8/30.
 */
public class DataInfoBusiness  extends BaseDbBusiness<DataInfo>  {

    public final static String TAG = DataInfoBusiness.class.getSimpleName();

    private static DataInfoBusiness instance = null;

    private Dao<DataInfo, String> mDataInfoDao;

    protected DataInfoBusiness(Context context) {
        super(context);
        try {
            dao = helper.getDao(DataInfo.class);
            mDataInfoDao = helper.getDao(DataInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataInfoBusiness getInstance(Context context) {
        if (instance == null) {
            instance = new DataInfoBusiness(context);
        }
        return instance;
    }
}
