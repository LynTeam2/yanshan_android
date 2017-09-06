package com.zls.www.check_version_lib;

import android.content.Context;
import android.util.Log;

import com.zls.www.check_version_lib.task.RemoteCheckVersion;
import com.zls.www.check_version_lib.utils.CheckVersionConstant;

/**
 * Created by dodozhou on 2017/9/5.
 */
public class UpdateChecker {
    public static final String TAG = UpdateChecker.class.getSimpleName();

    public static void checkForDialog(Context context) {
        if (context != null) {
          new RemoteCheckVersion(context, CheckVersionConstant.CheckVersionType.SHOW_DIALOG).start();
        } else {
            Log.e(TAG, "The arg context is null");
        }
    }

}
