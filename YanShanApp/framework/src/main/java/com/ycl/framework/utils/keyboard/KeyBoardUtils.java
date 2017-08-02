package com.ycl.framework.utils.keyboard;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软键盘工具类
 */
public class KeyBoardUtils {
    /**
     * 打开软键盘
     *
     * @param mEditText 输入框(好像关系不大)
     * @param mContext  上下文
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框(好像关系不大)
     * @param mContext  上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }


    public static void closeKeyBoard(FragmentActivity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputMethodManager im = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (im != null) {
            View v = activity.getWindow().getCurrentFocus();    //获取获得焦点的view，  关闭该view的 键盘
            if (v != null) {
                im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
