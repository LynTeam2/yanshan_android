package com.ycl.framework.utils.util;


import com.ycl.framework.base.FrameActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ZhangFeng on 16/5/6.
 */
public class BindHelper {
    //framework类库 反射 调用 主项目代码
    public static void showBindDialog(FrameActivity activity) {
        try {
            Class clazz = Class.forName("com.yh.cangke.util.Helper");
            Method method = clazz.getMethod("showBindPhoneDialog", FrameActivity.class);
            method.invoke(null, activity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
