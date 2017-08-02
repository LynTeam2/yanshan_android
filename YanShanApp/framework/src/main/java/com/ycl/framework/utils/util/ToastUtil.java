package com.ycl.framework.utils.util;

import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.ycl.framework.base.FrameApplication;

/**
 * Toast 工具类  Created by joeYu on 16/12/5.
 */

public class ToastUtil {

    private static Toast mToast;

    public static void initToast() {
        mToast = Toast.makeText(FrameApplication.getFrameContext(), "", Toast.LENGTH_SHORT);

    }

    public static void showToast(String text) {
        if (TextUtils.isEmpty(text))
            return;
        if (mToast == null) {
            if (Looper.myLooper() == null) {
                try {
                    boolean canLooper = true;
                    try {
                        Looper.prepare();
                    } catch (Exception e) {
                        canLooper = false;
                    }
                    mToast = Toast.makeText(FrameApplication.getFrameContext(), text, Toast.LENGTH_SHORT);
                    mToast.show();
                    if (canLooper) {
                        Looper.loop();
                    }
                } catch (Exception ignore) {

                }
            } else {
                mToast = Toast.makeText(FrameApplication.getFrameContext(), text, Toast.LENGTH_SHORT);
                mToast.show();
            }
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.show();
        }


    }


    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

}
