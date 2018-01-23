package com.zls.www.check_version_lib.task;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.zls.www.mulit_file_download_lib.multi_file_download.db.entity.DataInfo;

/**
 * Created by dodozhou on 2017/9/6.
 */
public class NotificationDownLoadInfo {


    private NotificationDownLoadInfo mInstance;

    private DataInfo mDataInfo;

    private Context mContext;

    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;

    private NotificationDownLoadInfo(Context context,DataInfo dataInfo) {
        mContext = context;
        mDataInfo = dataInfo;
        initAll();
    }

    public NotificationDownLoadInfo getInstance(Context context,DataInfo dataInfo) {
        synchronized (mInstance) {
            if (mInstance == null) {
                mInstance = new NotificationDownLoadInfo(context,dataInfo);
            }
        }
        return mInstance;
    }





    private void initAll() {

        mNotifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(mContext);
        String appName = mContext.getString(mContext.getApplicationInfo().labelRes);
        int icon = mContext.getApplicationInfo().icon;
        mBuilder.setContentTitle(appName).setSmallIcon(icon);

    }



}
