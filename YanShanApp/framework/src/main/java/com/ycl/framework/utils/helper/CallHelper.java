package com.ycl.framework.utils.helper;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.utils.util.YisLoger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Call;

/**
 * CallHelper 网络框架帮助类 by joe on 17/3/2.
 */

public class CallHelper {


    private static Map<String, List<Call>> callsMap = new ConcurrentHashMap<String, List<Call>>();


    /**
     * 保存请求集合
     *
     * @param call
     */
    public static void putCall(Call call, Object tag) {
        String tagIndex = getTags(tag);
        if (tagIndex == null) {
            YisLoger.logTagW("callKey", "call's tag is null");
            ToastUtil.showToast("null");
            return;
        }
        List<Call> callList = callsMap.get(tagIndex);
        if (null == callList) {
            callList = new LinkedList<Call>();
            callList.add(call);
            callsMap.put(tagIndex, callList);
        } else {
            callList.add(call);
        }

    }


    private static String getTags(Object object) {
        String tag = null;
        if (object == null)
            return null;
        if (object instanceof Activity) {
            Activity activity = (Activity) object;
            tag = activity.getClass().getSimpleName();
        }
        if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            tag = fragment.getActivity().getClass().getSimpleName();
        }
        return tag;
    }


    /**
     * 取消请求
     *
     * @param clazz
     */
    public static void cancelCall(String clazz) {
        List<Call> callList = callsMap.get(clazz);
        if (null != callList) {
            YisLoger.logTag("packet", "clear     " + clazz + "   ——  maps");
            for (Call call : callList) {
                if (!call.isCanceled())
                    call.cancel();
            }
            callsMap.remove(clazz);
        }
    }

}
