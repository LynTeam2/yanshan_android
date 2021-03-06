package com.ycl.framework.utils.util;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.ycl.framework.base.FrameApplication;
import com.ycl.framework.utils.helper.CallHelper;

/**
 * 前后台判断util
 */
public class Foreground implements FrameApplication.ActivityLifecycleCallbacks {

    //单例
    private static Foreground instance = new Foreground();

    //
//	private static String TAG = Foreground.class.getSimpleName();
//	private final int CHECK_DELAY = 500;
//
//	//用于判断是否程序在前台
//	private boolean foreground = false, paused = true;
//	//handler用于处理切换activity时的短暂时期可能出现的判断错误
//	private Handler handler = new Handler();
//	private Runnable check;
//
    public static void init(Application app) {
        app.registerActivityLifecycleCallbacks(instance);
    }

    public static Foreground get() {
        return instance;
    }

    private Foreground() {
    }

//	@Override
//	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//
//	}
//
//	@Override
//	public void onActivityDestroyed(Activity activity) {
//
//	}
//
//	@Override
//	public void onActivityPaused(Activity activity) {
//		paused = true;
//	    if (check != null)
//	        handler.removeCallbacks(check);
//	    handler.postDelayed(check = new Runnable(){
//	        @Override
//	        public void run() {
//	            if (foreground && paused) {
//	                foreground = false;
//	                Log.i(TAG, "went background");
//	            } else {
//	                Log.i(TAG, "still foreground");
//	            }
//	        }
//	    }, CHECK_DELAY);
//
//	}
//
//	@Override
//	public void onActivityResumed(Activity activity) {
//		paused = false;
//	    foreground = true;
//	    if (check != null)
//	        handler.removeCallbacks(check);
//
//	}
//
//	@Override
//	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//
//	}
//
//	@Override
//	public void onActivityStarted(Activity activity) {
//
//	}
//
//	@Override
//	public void onActivityStopped(Activity activity) {
//
//	}
//
//	public boolean isForeground(){
//	    return foreground;
//	}


    private boolean isForeground;
    private int activityCount = 0;

    //判断前后台 策略修改

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        YisLoger.state(activity.getClass().getName(), "---------  onActivityCreated ");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        YisLoger.state(activity.getClass().getName(), "---------  onActivityStarted ");
        activityCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        YisLoger.state(activity.getClass().getName(), "---------  onActivityResumed ");
        isForeground = true;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        YisLoger.state(activity.getClass().getName(), "---------  onActivityPaused ");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        YisLoger.state(activity.getClass().getName(), "---------  onActivityStopped ");
        activityCount--;
        {
            if (activityCount == 0) {
                isForeground = false;
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        YisLoger.state(activity.getClass().getName(), "---------  onActivitySaveInstanceState ");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        YisLoger.state(activity.getClass().getName(), "---------   onActivityDestroyed ");
        if (activity.getClass().getSimpleName().contains("VideoDetailActivity")) {
            return;
        }
        CallHelper.cancelCall(activity.getClass().getSimpleName());
    }


    public boolean getIsForeround() {
        return isForeground;
    }

}
