package com.ycl.framework.notification;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.ycl.framework.base.FrameApplication;

public class NotificationUtils {

    /**
     * 发送不会重复的通知
     *
     * @param context     上下文
     * @param title       标题
     * @param message     消息
     * @param SmallIconId 图标
     * @param activity    要启动的类
     * @param extras      传递的参数
     */
    @SuppressLint("NewApi")
    public static void sendNotification(Context context, String title,
                                        String message, int SmallIconId, Class<?> activity, Bundle extras) {

        Intent mIntent = new Intent(context, activity);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (extras != null) {
            mIntent.putExtras(extras);
        }

        int requestCode = (int) System.currentTimeMillis();

        PendingIntent mContentIntent = PendingIntent.getActivity(context,
                requestCode, mIntent, 0);

        Notification mNotification = new Notification.Builder(context)
                .setContentTitle(title).setSmallIcon(SmallIconId)
                .setContentIntent(mContentIntent).setContentText(message)
                .build();
        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotification.defaults = Notification.DEFAULT_ALL;
        //是否震动
//        long[] vibrate = {0, 100, 200, 300}; //0毫秒后开始振动，振动100毫秒后停止，再过200毫秒后再次振动300毫秒
//        mNotification.vibrate = vibrate;
        //音效
//          mNotification.sound = Uri.parse("android.resource://" + mContext.getPackageName() + "/"
//                + R.raw.sound_notification);

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(requestCode, mNotification);
    }

    @SuppressLint("NewApi")
    public static void showNotification(Context context, String title,
                                        String message, int SmallIconId) {


        int requestCode = (int) System.currentTimeMillis();

        PendingIntent mContentIntent = PendingIntent.getActivity(context,
                0, new Intent(), 0);

        Notification mNotification = new Notification.Builder(context)
                .setContentTitle(title).setSmallIcon(SmallIconId).setAutoCancel(true) //自动消失
                .setContentText(message).setContentIntent(mContentIntent)
                .build();
        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotification.defaults = Notification.DEFAULT_ALL;

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(requestCode, mNotification);
    }


    /**
     * 不会被清除的通知
     *
     * @param context 上下文
     * @param title   标题
     * @param info    详情
     */
    @SuppressWarnings("deprecation")
    public static void showNotification(Activity context, String title, String info,
                                        int SmallIconId) {
        NotificationManager barmanager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notice = new Notification(SmallIconId, title,
                System.currentTimeMillis());

        notice.flags |= Notification.FLAG_ONGOING_EVENT;

        // 将此通知放到通知栏的"Ongoing"即"正在运行"组中
        notice.flags |= Notification.FLAG_NO_CLEAR;

        // 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用
        notice.flags |= Notification.FLAG_SHOW_LIGHTS;

        Intent appIntent = new Intent(Intent.ACTION_MAIN);
        // appIntent.setAction(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        appIntent.setComponent(new ComponentName(context.getPackageName(),
                context.getPackageName() + "." + context.getLocalClassName()));

        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                appIntent, 0);

//        if(android.os.Build.VERSION.SDK_INT < 11)
//        {
//            notice.setLatestEventInfo(context, title, info, contentIntent);
//        }

        Notification.Builder builder = new Notification.Builder(context);

        notice = builder.setContentIntent(contentIntent).setContentTitle(title).setContentText(info).build();

        barmanager.notify(0, notice);
    }

    public static void showImNOtification(String senderStr, String contentStr, int SmallIconId, int pushId, Class cla) {
        NotificationManager mNotificationManager = (NotificationManager) FrameApplication.getFrameContext().getSystemService(FrameApplication.getFrameContext().NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(FrameApplication.getFrameContext());
        Intent notificationIntent = new Intent(FrameApplication.getFrameContext(), cla);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(FrameApplication.getFrameContext(), 0,
                notificationIntent, 0);
        mBuilder.setContentTitle(senderStr)//设置通知栏标题
                .setContentText(contentStr)
                .setContentIntent(intent) //设置通知栏点击意图
//                .setNumber(++pushNum) //设置通知集合的数量
                .setTicker(senderStr + ":" + contentStr) //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                .setSmallIcon(SmallIconId);//设置通知小ICON
        Notification notify = mBuilder.build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(pushId, notify);
    }


    /**
     * 清除所有通知
     *
     * @param context 上下文
     */
    public static void cancelNotification(Context context) {

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

}
