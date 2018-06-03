package cn.gov.bjys.onlinetrain.utils;

import java.util.HashMap;
import java.util.Map;


public class MapParamsHelper {
    public static Map<String, Object> getBaseParamsMap() {
        Map<String, Object> params = new HashMap<String, Object>();
        return params;
    }


    //登录 Map
    public static Map<String, Object> getLogin(String loginId, String psd) {
        Map<String, Object> params = getBaseParamsMap();
        params.put("username", loginId);
        params.put("password", psd);
        return params;
    }

    //更新昵称
    public static Map<String, Object> uploadNickName(String nickName) {
        Map<String, Object> params = getBaseParamsMap();
        params.put("nickname", nickName);
        return params;
    }
    //更新头像
    public static Map<String, Object> uploadIcon(String icon) {
        Map<String, Object> params = getBaseParamsMap();
        params.put("icon", icon);
        return params;
    }
    //更新安全豆
    public static Map<String, Object> uploadWealthValue(long beanCount) {
        Map<String, Object> params = getBaseParamsMap();
        params.put("beanCount", beanCount);
        return params;
    }

}
