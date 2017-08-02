package com.ycl.framework.utils.io;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.util.ToastUtil;

/**
 * BitmapLoadTask
 * by yuchaoliang on 16/5/16.
 */
public class BitmapLoadTask extends AsyncTask<String, Void, StringBuilder> {

    private FrameActivity activity;

    @Override
    protected StringBuilder doInBackground(String... params) {
        StringBuilder sp = new StringBuilder();
        for (String item : params) {
            String thumStr = ImageUtils.savePhotoToSDCard(item, activity.getApplicationContext());
            if (TextUtils.isEmpty(thumStr))
                return null;
            else {
                sp.append(thumStr);
                sp.append(";");
            }
        }
        return sp;
    }


    @Override
    protected void onPostExecute(StringBuilder stringBuilder) {
        if (stringBuilder == null) {
            activity.dismissProgressDialog();
            ToastUtil.showToast("图片获取失败  ");
            return;
        }

    }

    public void executeDynamic(FrameActivity act, String[] str) {
        this.activity = act;
        execute(str);
    }


}
