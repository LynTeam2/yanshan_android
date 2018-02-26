package cn.gov.bjys.onlinetrain.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dodozhou on 2017/9/25.
 */
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

}
