package com.ycl.framework.system;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;

import com.ycl.framework.utils.util.YisLoger;

import java.util.Locale;

public class ApnManager {
    private static final String TAG = "ApnManager";
    private Context mContext;
    private SimType mSimType;
    private String mMccNumber;
    private String mMncNumber;
    private static final String WAP_PROXY = "10.0.0.172";
    private static final String WAP_PROXY2 = "010.000.000.172";
    private static final String TELECOM_WAP_PROXY = "10.0.0.200";
    private static final String TELECOM_WAP_PROXY2 = "010.000.000.200";
    private static final String PROXY_PORT = "80";
    private APNEntity mApnEntity;

    public ApnManager(Context context, SimInfoManager telephonyManager) {
        this.mContext = context;
        this.mSimType = telephonyManager.getSimType();
        this.mMccNumber = telephonyManager.getMCCNumber();
        this.mMncNumber = telephonyManager.getMNCNumber();
    }

    public boolean checkAPNisTrue(APNEntity apn, APNType type) {
        if (apn == null) {
            YisLoger.logTagE("ApnManager", "checkAPNisTrue false,apn is null");
            return false;
        } else if (this.mMccNumber.equals(apn.getMcc()) && this.mMncNumber.equals(apn.getMnc())) {
            return APNType.Wap == type && !this.isWapApnTrue(apn) ? false : (APNType.Net == type && !this.isNetApnTrue(apn) ? false : this.isNetApnTrue(apn) || this.isWapApnTrue(apn));
        } else {
            YisLoger.logTagW("ApnManager", "checkAPNisTrue false, apn.mcc or apn.mnc is wrong");
            return false;
        }
    }

    public ApnAccessorType getAPNType() {
        try {
            APNEntity e = this.getDefaultAPN();
            if (e != null) {
                if (e.getApn() == null) {
                    return ApnAccessorType.UNKNOWN;
                }

                if ("wifi".equalsIgnoreCase(e.getApn())) {
                    return ApnAccessorType.WIFI;
                }

                switch (this.mSimType.ordinal()) {//ApnManager.SyntheticClass_1.$SwitchMap$com$iflytek$mobileXCorebusiness$base$system$SimType[this.mSimType.ordinal()]
                    case 1:
                        if (this.isWapApn(e)) {
                            return ApnAccessorType.CMWAP;
                        }

                        return ApnAccessorType.CMNET;
                    case 2:
                        if (this.isWapApn(e)) {
                            return ApnAccessorType.UNIWAP;
                        }

                        return ApnAccessorType.UNINET;
                    case 3:
                        if (this.isWapApn(e)) {
                            return ApnAccessorType.CTWAP;
                        }

                        return ApnAccessorType.CTNET;
                }
            }
        } catch (Exception var2) {
            YisLoger.w("ApnManager", "getAPNType error", var2);
        }

        return ApnAccessorType.UNKNOWN;
    }

    public boolean isWapApn(APNEntity apn) {
        if (apn == null) {
            return false;
        } else {
            String proxy = apn.getProxy();
            String mmsProxy = apn.getMmsProxy();
            return proxy != null && !proxy.equals("") ? true : mmsProxy != null && !mmsProxy.equals("");
        }
    }

    private boolean isNetApn(APNEntity apn) {
        return !this.isWapApn(apn);
    }

    private boolean isWapApnTrue(APNEntity apn) {
        if (!this.isWapApn(apn)) {
            return false;
        } else {
            String proxy = apn.getProxy();
            String port = apn.getPort();
            String mmsProxy = apn.getMmsProxy();
            String mmsPort = apn.getMmsPort();
            switch (this.mSimType.ordinal()) {
                case 1:
                case 2:
                    if (proxy != null && !proxy.equals("")) {
                        if (!"10.0.0.172".equals(proxy) && !"010.000.000.172".equals(proxy)) {
                            return false;
                        }

                        if (!"80".equals(port)) {
                            return false;
                        }
                    }

                    if (mmsProxy != null && !mmsProxy.equals("")) {
                        if (!"10.0.0.172".equals(mmsProxy) && !"010.000.000.172".equals(mmsProxy)) {
                            return false;
                        }

                        if (!"80".equals(mmsPort)) {
                            return false;
                        }
                    }
                    break;
                case 3:
                    if (proxy != null && !proxy.equals("")) {
                        if (!"10.0.0.200".equals(proxy) && !"010.000.000.200".equals(proxy)) {
                            return false;
                        }

                        if (!"80".equals(port)) {
                            return false;
                        }
                    }

                    if (mmsProxy != null && !mmsProxy.equals("")) {
                        if (!"10.0.0.200".equals(mmsProxy) && !"010.000.000.200".equals(proxy)) {
                            return false;
                        }

                        if (!"80".equals(mmsPort)) {
                            return false;
                        }
                    }

                    if (apn.getUser() == null || apn.getUser().equals("") || apn.getPassword() == null || apn.getPassword().equals("")) {
                        return false;
                    }
            }

            return true;
        }
    }

    private boolean isNetApnTrue(APNEntity apn) {
        return !this.isNetApn(apn) ? false : this.mSimType != SimType.China_Telecom || apn.getUser() != null && !apn.getUser().equals("") && apn.getPassword() != null && !apn.getPassword().equals("");
    }

    public APNEntity getDefaultAPN() {
        try {
            ConnectivityManager e = (ConnectivityManager) this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = e.getActiveNetworkInfo();
            this.mApnEntity = new APNEntity();
            if (null == networkInfo) {
                this.mApnEntity.setApn((String) null);
                YisLoger.logTag("ApnManager", "getDefaultAPN | network info is null");
            } else if (networkInfo.getType() == 1) {
                this.mApnEntity.setApn("wifi");
            } else {
                this.mApnEntity.setApn(networkInfo.getExtraInfo());
                this.mApnEntity.setProxy(Proxy.getDefaultHost());
                this.mApnEntity.setPort(Integer.toString(Proxy.getDefaultPort()));
                this.correctApn(this.mApnEntity);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return this.mApnEntity;
    }

    private boolean isNeedCorrect(String apnString) {
        return !"3gwap".equals(apnString) && !"3gnet".equals(apnString) && !"uniwap".equals(apnString) && !"uninet".equals(apnString) && !"cmwap".equals(apnString) && !"cmnet".equals(apnString) && !"ctwap".equals(apnString) && !"ctnet".equals(apnString);
    }

    private void correctApn(APNEntity entity) {
        String apnString = entity.getApn();
        String host = Proxy.getDefaultHost();
        if (apnString == null || this.isNeedCorrect(apnString.toLowerCase(Locale.getDefault()))) {
            switch (this.mSimType.ordinal()) {
                case 1:
                    if (host == null) {
                        entity.setApn("cmnet");
                    } else {
                        entity.setApn("cmwap");
                    }
                    break;
                case 2:
                    if (host == null) {
                        entity.setApn("3gnet");
                    } else {
                        entity.setApn("3gwap");
                    }
                    break;
                case 3:
                    if (host == null) {
                        entity.setApn("ctnet");
                    } else {
                        entity.setApn("ctwap");
                    }
                    break;
                default:
                    if (host == null && ("10.0.0.200".equals(host) || "010.000.000.200".equals(host))) {
                        entity.setApn("ctwap");
                    }
            }

        }
    }
}
