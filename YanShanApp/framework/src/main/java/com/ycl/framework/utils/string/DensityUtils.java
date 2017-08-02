package com.ycl.framework.utils.string;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.ycl.framework.base.FrameApplication;


/**
 * 单位转换 工具类<br>
 */
public class DensityUtils {

    /**
     * dp转px
     */
    public static int dp2px(float dpVal, Context context) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources()
                        .getDisplayMetrics());
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources()
                        .getDisplayMetrics());
    }

    /**
     * px转dp
     */
    public static int px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxVal / scale);
    }

    /**
     * px转sp
     */
    public static float px2sp(Context context, float pxVal) {
        return (int) (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenW(Context ctx) {
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        // int w = aty.getWindowManager().getDefaultDisplay().getWidth();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenH(Context ctx) {
        DisplayMetrics dm;
        dm = ctx.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static String getStringInsert(String nums, char news_char, int location) {
        return nums.substring(0, location) + news_char + nums.substring(location, nums.length());
    }

    public static int getAutoSizePx(int oldPx) {
        try {
            ApplicationInfo appInfo = FrameApplication.getFrameContext().getPackageManager()
                    .getApplicationInfo(FrameApplication.getFrameContext().getPackageName(),
                            PackageManager.GET_META_DATA);
            int width = appInfo.metaData.getInt("design_width");
//            int height = appInfo.metaData.getInt("design_height");
            float scale = getScreenW(FrameApplication.getFrameContext()) / 1.0f / width;

            return (int) (scale * oldPx);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return oldPx;
    }

    //横屏状态
    public static int getAutoSizeLandPx(int oldPx) {
        try {
            ApplicationInfo appInfo = FrameApplication.getFrameContext().getPackageManager()
                    .getApplicationInfo(FrameApplication.getFrameContext().getPackageName(),
                            PackageManager.GET_META_DATA);
            int width = appInfo.metaData.getInt("design_width");
            float scale = getScreenH(FrameApplication.getFrameContext()) / 1.0f / width;

            return (int) (scale * oldPx);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return oldPx;
    }


}
