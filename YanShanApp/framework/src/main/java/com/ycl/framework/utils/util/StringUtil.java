package com.ycl.framework.utils.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.ycl.framework.base.FrameApplication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.jar.Manifest;

/**
 * String的工具 by joeYu on 16/10/14.
 * 获取device info
 */

public class StringUtil {

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                result = rest == PackageManager.PERMISSION_GRANTED;
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, android.Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(fstream, 1024);
                mac = in.readLine();
            } catch (IOException e) {
            } finally {
                try {
                    fstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            FrameApplication.deviceId = device_id;
            YisLoger.logTag("device_params",json.toString());
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取app版本
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            YisLoger.logTagE(null, "VersionInfo" + e);
        }
        return versionName;
    }

    //获取渠道号
    public static String getChannelSource(Context context) {
        String msg = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            Bundle bundle = appInfo.metaData;

            msg = bundle.getString("UMENG_CHANNEL");
            if (msg == null) {
                int channel = bundle.getInt("UMENG_CHANNEL");
                msg = String.valueOf(channel);
            }
            return msg;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            msg = "offical";
        }
        return msg;
    }

}
