package com.ycl.framework.system;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.telephony.TelephonyManager;


public class SimInfoManager {
    protected TelephonyManager mTelephonyManager;

    public SimInfoManager(Context context) {
        this.mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);//("phone");
    }

    public SimInfoManager() {
    }

    public String getIMSINumber() {
        return this.mTelephonyManager.getSubscriberId();
    }

    public String getNetworkOperatorName() {
        return this.mTelephonyManager.getNetworkOperatorName();
    }

    public int getSimState() {
        return this.mTelephonyManager.getSimState();
    }

    public String getSimOperatorName() {
        return this.mTelephonyManager.getSimOperatorName();
    }

    public String getSimOperator() {
        return this.mTelephonyManager.getSimOperator();
    }

    public String getMCCNumber() {
        String simOperator = this.getSimOperator();
        return simOperator != null && simOperator.length() >= 5 ? simOperator.substring(0, 3) : null;
    }

    public String getMNCNumber() {
        String simOperator = this.getSimOperator();
        return simOperator != null && simOperator.length() >= 5 ? simOperator.substring(3, 5) : null;
    }

    public SimType getSimType() {
        String mnc = this.getMNCNumber();
        if (mnc != null && !mnc.equals("")) {
            if (mnc != null) {
                if (mnc.equals("00") || mnc.equals("02") || mnc.equals("07")) {
                    return SimType.China_Mobile;
                }

                if (mnc.equals("01")) {
                    return SimType.China_Unicom;
                }

                if (mnc.equals("03") || mnc.equals("05")) {
                    return SimType.China_Telecom;
                }
            }

            return SimType.Unknown;
        } else {
            return SimType.Null;
        }
    }

    public String getDeviceId() {
        return this.mTelephonyManager.getDeviceId();
    }

    public int getMobileNetworkType() {
        return this.mTelephonyManager != null ? this.mTelephonyManager.getNetworkType() : -1;
    }

    public int getPhoneType() {
        return this.mTelephonyManager != null ? this.mTelephonyManager.getPhoneType() : -1;
    }
}
