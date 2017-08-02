package com.ycl.framework.utils.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则检测Helper by joeYu on 17/6/22.
 */

public class CheckFromatHelper {

    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }
}
