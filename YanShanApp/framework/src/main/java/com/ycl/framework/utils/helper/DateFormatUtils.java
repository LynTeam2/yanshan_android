package com.ycl.framework.utils.helper;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public class DateFormatUtils {
    //月日 时分
    public static String ConvertDateToCustomType(long l, String type) {
        Date d = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat(type);
        return format.format(d);
    }

    public static Date commonParse(String parse) {
        DateFormat dd = new SimpleDateFormat("yyyy.MM.dd");
        Date date = null;
        try {
            date = dd.parse(parse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getTimeOfVideo(long l) {
        return ConvertDateToCustomType(l, "yyyy年MM月dd日 HH:mm");
    }


    public static Date getDateParse(String parse, String type) {
        DateFormat dd = new SimpleDateFormat(type);
        Date date = null;
        try {
            date = dd.parse(parse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    //格式 Jul 6, 2017 10:53:38    "yyyy-MM-dd HH:mm"
    public static String getDataParseForStr(String parseContent, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy K:m:s a", Locale.ENGLISH);
        Date mDate = null;
        try {
            mDate = sdf.parse(parseContent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (mDate != null)
            return ConvertDateToCustomType(mDate.getTime(), type);
        return "xxx";
    }

    /**
     * @param date1 当前时间
     * @param date2 获取时间
     * @Method 获取时间-- 回复    2014-10-13
     */
    public static String getTimeOfSession(long date1, long date2, String sformat) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        //先判断是否同年
        int y1 = calendar1.get(Calendar.YEAR);
        int y2 = calendar2.get(Calendar.YEAR);
        //判断是否同日
        int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int maxDays = 0;
        int dxDay = 0; //相差多少天
        int dxYear = y1 - y2;
        if (dxYear > 0) {
            maxDays = calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
            dxDay = d1 - d2 + maxDays;
        } else if (dxYear < 0) {
            maxDays = calendar1.getActualMaximum(Calendar.DAY_OF_YEAR);
            dxDay = d1 - d2 - maxDays;
        } else {
            dxDay = d1 - d2;
        }

        if (dxDay == 0) {
            SimpleDateFormat sdFormat = new SimpleDateFormat(sformat);
            return sdFormat.format(calendar2.getTime());
        } else if (dxDay >= 1 && dxDay <= 7) {
            return dxDay + "天前";
        } else if (dxDay > 7) {
            if (dxYear == 0) {
                SimpleDateFormat format = new SimpleDateFormat("MM-dd");
                return format.format(calendar2.getTime());
            }
            if (dxYear > 0) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                return format.format(calendar2.getTime());
            }
        }
        return "";
    }

    /**
     * @param dates 获取时间
     * @Method 时间戳   获取时间-
     */
    public static String getTimeOfDynamic(final long dates) {
        long currentTime = System.currentTimeMillis(); // 当前时间
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(currentTime);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(dates);
        long xime = currentTime - dates;
        if (xime < 3600000) {
            long mins = getQuotientValues(xime, 60000);
            return mins < 1 ? "刚刚" : mins + "分钟前";
        } else if (xime < 86400000) {
            //判断是否同日
            int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
            int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
            if (d2 - d1 > 0) {
                //先判断是否同年
                int y1 = calendar1.get(Calendar.YEAR);
                int y2 = calendar2.get(Calendar.YEAR);
                SimpleDateFormat format;
                if (y1 - y2 > 0) {
                    format = new SimpleDateFormat("yyyy-MM-dd");
                } else
                    format = new SimpleDateFormat("MM-dd");
                return format.format(calendar2.getTime());
            } else {
                long hours = getQuotientValues(xime, 3600000);
                return hours + "小时前";
            }
        } else {
            //先判断是否同年
            int y1 = calendar1.get(Calendar.YEAR);
            int y2 = calendar2.get(Calendar.YEAR);
            SimpleDateFormat format;
            if (y1 - y2 > 0) {
                format = new SimpleDateFormat("yyyy-MM-dd");
            } else
                format = new SimpleDateFormat("MM-dd");
            return format.format(calendar2.getTime());
        }
    }


    /**
     * @param date1 当前时间
     * @param date2 获取时间
     * @Method 留言板 话题(default)    获取时间-    2014-10-13
     */
    public static String getTimeOfDynamic(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        //先判断是否同年
        int y1 = calendar1.get(Calendar.YEAR);
        int y2 = calendar2.get(Calendar.YEAR);
        //判断是否同日
        int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int maxDays = 0;
        int dxDay = 0; //相差多少天
        int dxYear = y1 - y2;
        if (dxYear > 0) {
            maxDays = calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
            dxDay = d1 - d2 + maxDays;
        } else if (dxYear < 0) {
            maxDays = calendar1.getActualMaximum(Calendar.DAY_OF_YEAR);
            dxDay = d1 - d2 - maxDays;
        } else {
            dxDay = d1 - d2;
        }

        long xtime = date1 - date2;

        if (dxDay == 0) {
            if (xtime < 3600000) {
                int mins = (int) ((date1 - date2) / 60000);
                return mins < 1 ? "刚刚" : mins + "分钟前";
            }
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            return format.format(calendar2.getTime());
        } else if (dxDay == 1) {
            if (xtime < 3600000)
                return (xtime) / 60000 + "分钟前";
            if (xtime < 86400000) {
                return (xtime / 3600000) + "小时前";
            }
            return "1天前";
        } else if (dxDay > 1 && dxDay <= 7) {
            return dxDay + "天前";
        } else if (dxDay > 7) {
            if (dxYear == 0) {
                SimpleDateFormat format = new SimpleDateFormat("MM-dd");
                return format.format(calendar2.getTime());
            }
            if (dxYear > 0) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                return format.format(calendar2.getTime());
            }
        }
        return "";
    }


    //取商   (去除余数)
    public static long getQuotientValues(long data, int divisor) {
        long a = data % divisor; //余数
        if (data % divisor > 0) {
            return (data - a) / divisor;
        }
        return data / divisor;
    }
}
