package com.ycl.framework.utils.util;

import android.text.TextUtils;
import android.util.Log;


/**
 * log管理	@author yis
 */
public final class YisLoger {
//    public static boolean IS_DEBUG = true;
//    public static boolean DEBUG_LOG = true;
//    public static boolean SHOW_ACTIVITY_STATE = true;

    public static boolean IS_DEBUG = true;
    public static boolean DEBUG_LOG = true;
    public static boolean SHOW_ACTIVITY_STATE = false;

    private final static String TAG = "test";
    private final static String URL_TAG = "other";

    public static void openDebutLog(boolean enable) {
        IS_DEBUG = enable;
        DEBUG_LOG = enable;
    }

    public static void openActivityState(boolean enable) {
        SHOW_ACTIVITY_STATE = enable;
    }

    public static void debug(String msg) {
        if (IS_DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void debugUrl(String msg) {
        if (IS_DEBUG) {
            Log.d(URL_TAG, msg);
        }
    }

    public static void log(String packName, String state) {
        debugLog(packName, state);
    }

    public static void debug(String msg, Throwable tr) {
        if (IS_DEBUG) {
            Log.i(TAG, msg, tr);
        }
    }

    public static void state(String packName, String state) {
        if (SHOW_ACTIVITY_STATE) {
            Log.d("activity_state", packName + state);
        }
    }

    public static void stateFg(String packName, String state) {
        if (SHOW_ACTIVITY_STATE) {
            Log.d("fragment_state", packName + state);
        }
    }

    public static void debugLog(String packName, String state) {
        if (DEBUG_LOG) {
            Log.d(TAG, packName + state);
        }
    }

    public static void exception(Exception e) {
        if (DEBUG_LOG) {
            e.printStackTrace();
        }
    }

    public static void logUrl(String url) {
        if (DEBUG_LOG) {
            Log.i("url_test", url);
        }
    }


    public static void debug(String msg, Object... format) {
        debug(String.format(msg, format));
    }

    public static void logTag(String tag, String msg) {
        if (DEBUG_LOG) {
            Log.d(tag, msg);//部分手机日志不开启
        }
    }

    public static void logTagI(String tag, String msg) {
        if (DEBUG_LOG) {
            Log.i(tag, msg);
        }
    }

    public static void logTagW(String tag, String msg) {
        if (DEBUG_LOG) {
            Log.w(tag, msg);
        }
    }

    public static void logTagV(String tag, String msg) {
        if (DEBUG_LOG) {
            Log.v(tag, msg);
        }
    }

    public static void logTagE(String tag, String msg) {
        if (DEBUG_LOG) {
            if (TextUtils.isEmpty(tag))
                Log.e(TAG, msg);
            else
                Log.e(tag, msg);
        }
    }

    public static int d(String tag, String msg, Throwable tr) {
        int result = 0;
        if (DEBUG_LOG) {
            result = Log.d(tag, msg, tr);
        }

        return result;
    }


    public static int e(String tag, String msg, Throwable tr) {
        int result = 0;
        if (DEBUG_LOG) {
            result = Log.e(tag, msg, tr);
        }
        return result;
    }

    public static int w(String tag, String msg, Throwable tr) {
        int result = 0;
        if (DEBUG_LOG) {
            result = Log.w(tag, msg, tr);
        }

        return result;
    }
}
