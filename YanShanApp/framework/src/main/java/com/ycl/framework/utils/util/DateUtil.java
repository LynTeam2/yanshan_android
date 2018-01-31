package com.ycl.framework.utils.util;

import java.text.SimpleDateFormat;

/**
 * Created by dodo on 2018/1/31.
 */
public  class DateUtil {
    public static String  formatToString(long time){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(time);
        return dateString;
    }

    public static String  formatNianToFen(long time){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(time);
        return dateString;
    }


    public static String  formatToStringStatus(long time){
        SimpleDateFormat formatter = new SimpleDateFormat(" HH:mm");
        String dateString = formatter.format(time);
        return dateString;
    }

    public static String formatYourSelf(long time,SimpleDateFormat sdf){
        return sdf.format(time);
    }


}