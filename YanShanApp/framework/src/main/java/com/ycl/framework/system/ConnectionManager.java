package com.ycl.framework.system;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ycl.framework.utils.util.YisLoger;

public class ConnectionManager {
    static final String TAG = "ConnectionMgr";
    private ConnectivityManager mConMgr = null;
    private NetworkInfo mNetworkInfo = null;

    public ConnectionManager(Context context) {
        try {
            this.mConMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null != this.mConMgr) {
                this.mNetworkInfo = this.mConMgr.getActiveNetworkInfo();
            }
        } catch (Exception var3) {

            YisLoger.e("", "", var3);
        }

    }

    public int getCurrentNetworkType() {
        int networkType = -1;
        if (null != this.mNetworkInfo) {
            networkType = this.mNetworkInfo.getType();
        }

        return networkType;
    }

    public boolean isNetworkConnected() {
        return this.mNetworkInfo != null ? this.mNetworkInfo.isConnected() : false;
    }

    public String getNetworkExtraInfo() {
        return this.mNetworkInfo != null ? this.mNetworkInfo.getExtraInfo() : null;
    }

    public String getNetworkSubtypeName() {
        return this.mNetworkInfo != null ? this.mNetworkInfo.getSubtypeName() : null;
    }
}
