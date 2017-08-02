package com.ycl.framework.system;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class AppConfig {
    private String mBlcAid;
    private String mMscAid;
    private String mIMSI;
    private String mIMEI;
    private String mMAC;
    private String mCaller;
    private String mOSID;
    private String mUserAgent;
    private String mVersion;
    private Context mContext;
    private String mUid;
    private String mSid = "";
    private String mDownloadFromID;
    private String mMacAddress;
    private String mResolution;
    private String mDisplaySize;
    private SimInfoManager mTelephonyManager;
    private String mFomartModel;

    public AppConfig(Context context) {
        this.mContext = context;
        this.mBlcAid = "108ViaFlyLite";
        this.mOSID = "Android";
        this.mTelephonyManager = new SimInfoManager(context);
        this.mCaller = "";
        this.mDisplaySize = this.getDisplaySize(context);
        int sdk = VERSION.SDK_INT;
        String cpu = "";
        if (sdk > 7) {
            cpu = Build.HARDWARE;
        }

        this.mUserAgent = this.GetBuildParams("MANUFACTURER") + "|" + this.GetBuildParams("MODEL") + "|" + this.GetBuildParams("PRODUCT") + "|ANDROID" + VERSION.RELEASE + "|" + this.mDisplaySize + "|" + cpu;

        try {
            this.mVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException var5) {
            this.mVersion = "1.1.1XXX";
        }

    }

    public String getFormatModel() {
        if (null == this.mFomartModel) {
            this.mFomartModel = this.GetBuildParams("MANUFACTURER") + this.GetBuildParams("MODEL");
            if (null == this.mFomartModel || this.mFomartModel.length() == 0) {
                this.mFomartModel = "UNKOWN";
            }

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < this.mFomartModel.length(); ++i) {
                char ch = this.mFomartModel.charAt(i);
                if (ch >= 48 && ch <= 57 || ch >= 65 && ch <= 90 || ch >= 97 && ch <= 122) {
                    sb.append(ch);
                }
            }

            this.mFomartModel = sb.toString();
        }

        return this.mFomartModel;
    }

    public void setSimInfoManager(SimInfoManager simInfoManager) {
        this.mTelephonyManager = simInfoManager;
        this.mIMSI = this.mTelephonyManager.getIMSINumber();
        this.mIMEI = this.mTelephonyManager.getDeviceId();
    }

    public void setOSId(String osId) {
        this.mOSID = osId;
    }

    public void setBlcAppId(String appId) {
        this.mBlcAid = appId;
    }

    public void setMscAppId(String appId) {
        this.mMscAid = appId;
    }

    public void setChannelId(String channelId) {
        this.mDownloadFromID = channelId;
    }

    private String getDisplaySize(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        boolean width = false;
        boolean height = false;
        int height1;
        int width1;
        if (display.getOrientation() == 0) {
            width1 = display.getWidth();
            height1 = display.getHeight();
        } else {
            width1 = display.getHeight();
            height1 = display.getWidth();
        }

        return width1 + "*" + height1;
    }

    private String GetBuildParams(String params) {
        try {
            Field e = Build.class.getField(params);
            Build bd = new Build();
            if (null != e) {
                return e.get(bd).toString();
            }
        } catch (Exception var4) {
            ;
        }

        return "";
    }

    public ApnAccessorType getApnType() {
        ConnectionManager conMgr = new ConnectionManager(this.mContext);
        return conMgr.getCurrentNetworkType() == 1 ? ApnAccessorType.WIFI : (new ApnManager(this.mContext, this.mTelephonyManager)).getAPNType();
    }

    public String getVersion() {
        return this.mVersion;
    }

    public String getBlcAid() {
        return this.mBlcAid;
    }

    public String getMscAid() {
        return this.mMscAid;
    }

    public String getIMSI() {
        if (this.mIMSI == null || this.mIMSI.length() < 1) {
            this.mIMSI = this.mTelephonyManager.getIMSINumber();
        }

        return this.mIMSI;
    }

    public String getCaller() {
        return this.mCaller;
    }

    public String getIMEI() {
        if (this.mIMEI == null || this.mIMEI.length() < 1) {
            this.mIMSI = this.mTelephonyManager.getIMSINumber();
            this.mIMEI = this.mTelephonyManager.getDeviceId();
        }

        return this.mIMEI;
    }

    public String getMAC() {
        if (null == this.mMAC) {
            String str_mac = this.getLocalMacAddress();
            if (null != str_mac && str_mac.length() > 0) {
                this.mMAC = str_mac;
            }
        }

        return this.mMAC;
    }

    public String getOSID() {
        return this.mOSID;
    }

    public String getUserAgent() {
        return this.mUserAgent;
    }

    public void setUid(String uid) {
        this.mUid = uid;
    }

    public String getUid() {
        return null == this.mUid ? "" : this.mUid;
    }

    public void setSid(String sid) {
        this.mSid = sid;
    }

    public String getSid() {
        return this.mSid;
    }

    public String getDownloadFromId() {
        return this.mDownloadFromID;
    }

    public String getLocalMacAddress() {
        if (this.mMacAddress == null || this.mMacAddress.length() < 1) {
            WifiManager wifi = (WifiManager) this.mContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            if (info != null) {
                this.mMacAddress = info.getMacAddress();
            }
        }

        return this.mMacAddress;
    }

    public String getSymResolution() {
        return this.mResolution;
    }

    public void setSymResolution(String resolution) {
        this.mResolution = resolution;
    }

    public String getDisplaySize() {
        return this.mDisplaySize;
    }

    public void setIMSI(String imsi) {
        this.mIMSI = imsi;
    }

    public void setIMEI(String imei) {
        this.mIMEI = imei;
    }

    public class OSId {
        public static final String PAD = "AndroidPad";
        public static final String PHONE = "Android";

        public OSId() {
        }
    }

    public class AppId {
        public static final String LINGXI_LITE = "108ViaFlyLite";
        public static final String LINGXI = "108ViaFly";
        public static final String YUDIAN = "100ViaFly";

        public AppId() {
        }
    }
}
