package com.ycl.framework.utils.helper;

import android.content.Context;
import android.content.ContextWrapper;

import com.ycl.framework.base.FrameActivity;

/**
 * Created by joe on 17/2/10.
 */

public class ContextHelper {

    //上下文ctx获取FrameActivity 的对象
    public static FrameActivity getRequiredActivity(Context ctx) {
        Context context = ctx;
        if (context == null) return null;
        while (context instanceof ContextWrapper) {
            if (context instanceof FrameActivity) {
                return (FrameActivity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
