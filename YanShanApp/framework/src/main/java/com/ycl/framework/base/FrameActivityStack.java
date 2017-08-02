package com.ycl.framework.base;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出<br>
 */
public class FrameActivityStack {
    private static Stack<Activity> activityStack;
    private static final FrameActivityStack instance = new FrameActivityStack();

    private FrameActivityStack() {
    }

    public static FrameActivityStack create() {
        return instance;
    }

    /**
     * 获取当前Activity栈中元素个数
     */
    public int getCount() {
        return activityStack.size();
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（栈顶Activity）
     */
    public Activity topActivity() {
        if (activityStack == null) {
            throw new NullPointerException(
                    "Activity stack is Null,your Activity must extend KJActivity");
        }
        if (activityStack.isEmpty()) {
            return null;
        }
        return activityStack.lastElement();
    }

    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     */
    public Activity findActivity(Class<?> cls) {
        Activity activity = null;
        for (Activity aty : activityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return activity;
    }

    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishActivityLast() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();//此处不用finish
        }
    }


    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     *
     * @param cls 类
     */
    public void finishOthersActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (!(activity.getClass().equals(cls))) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束所有Activity  除了 act
     */
    public void finishAllActExcept(Activity act) {
        for (Activity activity : activityStack) {
            if (activity == act)
                continue;
            if (!activity.isFinishing()) {
                activityStack.remove(activity);
                activity.finish();
            }
        }
    }

    /**
     * 应用程序退出
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            Runtime.getRuntime().exit(0);
        } catch (Exception e) {
            Runtime.getRuntime().exit(-1);
        }
    }

    /**
     * 清楚栈--activity
     */
    public void removeActivityStack(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 查找  activity
     */
    public boolean containsActivityStack(Activity activity) {
        return activityStack.contains(activity);
    }


    /**
     * 结束指定的Activity(重载)
     */
    public boolean containsClaStack(Class<?> cls) {
        if (activityStack == null)
            return false;
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 结束所有Activity  除了 act
     */
    public void finishAllKeep(Class<?> cla) {
        Activity act = null;
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cla)) {
                act = activity;
                break;
            }
        }
        if (act == null) {
            finishAllActivity();
        } else {
            for (Activity activity : activityStack) {
                if (!activity.isFinishing()) {
                    if (act != activity)
                        activity.finish();
                }
            }
            activityStack.clear();
            activityStack.add(act);
        }
    }

}