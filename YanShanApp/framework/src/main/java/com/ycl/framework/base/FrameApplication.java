package com.ycl.framework.base;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.bumptech.glide.Glide;
import com.facebook.stetho.Stetho;
import com.ycl.framework.module.GlideCatchUtil;
import com.ycl.framework.utils.util.Foreground;
import com.ycl.framework.utils.util.advanced.Utils;

/**
 * frameApplication on 2015/10/15.
 */
public class FrameApplication extends MultiDexApplication {

    protected static Context ctx;

    public static String deviceId;
    public static String Version;


    @Override
    public void onCreate() {
        super.onCreate();
        ctx = this.getApplicationContext();
        Utils.init(ctx);
        Foreground.init(this);
//      Stetho 调试
        Stetho.initializeWithDefaults(this);
//          拿设备的物理高度进行百分比化
//         AutoLayoutConifg.getInstance().useDeviceSize();

    }
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level >= ComponentCallbacks2.TRIM_MEMORY_MODERATE) {
            GlideCatchUtil.getInstance().clearCacheMemory();
        }
    }
    public static Context getFrameContext() {
        return ctx;
    }

}