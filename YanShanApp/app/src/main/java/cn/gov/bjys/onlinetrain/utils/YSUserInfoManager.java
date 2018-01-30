package cn.gov.bjys.onlinetrain.utils;

import android.content.Context;
import android.text.TextUtils;

import com.ycl.framework.utils.sp.SavePreference;

import cn.gov.bjys.onlinetrain.BaseApplication;

/**
 * 管理用户信息的单例类
 */
public class YSUserInfoManager {

    private Context mContext;
    private static YSUserInfoManager sInstance;

    public YSUserInfoManager() {
        mContext = BaseApplication.getAppContext();
    }

    public static YSUserInfoManager getsInstance() {
        if (sInstance == null) {
            sInstance = new YSUserInfoManager();
        }
        return sInstance;
    }

    public void saveAuthToken(String authToken) {
        try {
            SavePreference.saveCommit(mContext, YSConst.UserInfo.KEY_USER_TOKEN, authToken);
        } catch (Exception ignore) {
        }
    }

    public String getAuthToken() {
        return SavePreference.getStr(mContext, YSConst.UserInfo.KEY_USER_TOKEN);
    }

    public void saveUserId(String userid) {
        try {
            SavePreference.saveCommit(mContext, YSConst.UserInfo.KEY_USER_ID, userid);
        } catch (Exception ignore) {
        }
    }

    public String getUserId(){
        return SavePreference.getStr(mContext, YSConst.UserInfo.KEY_USER_ID);
    }

    //是否登录
    //  true  登录
    public boolean isLogin() {
//        return !TextUtils.isEmpty(getAuthToken());
        return true;
    }


}
