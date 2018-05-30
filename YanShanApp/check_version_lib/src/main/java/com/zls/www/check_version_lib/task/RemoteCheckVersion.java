package com.zls.www.check_version_lib.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.zls.www.check_version_lib.R;
import com.zls.www.check_version_lib.bean.CheckVersionBean;
import com.zls.www.check_version_lib.utils.AppUtils;
import com.zls.www.check_version_lib.utils.CheckVersionConstant;

import com.zls.www.check_version_lib.view.*;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RemoteCheckVersion {
    public final static String TAG = RemoteCheckVersion.class.getSimpleName();

    private int mType = CheckVersionConstant.CheckVersionType.SHOW_NORMAL;
    private Context mContext;

    private ProgressDialog mDialog;

    public RemoteCheckVersion(Context context, int type) {
        this.mContext = context;
        this.mType = type;

    }

    interface CheckVersionInterface {
        @GET
        Observable<String> checkVersionInfo(@Url String url);


    }


    private final String BASE_URL = "https://raw.githubusercontent.com/";
    private final String OTHERS_URL = "feicien/android-auto-update/develop/extras/update.json";

    public void start() {
        showDialog();
        Observable<String> obs = HRetrofitNetHelper.getInstance(mContext)
                .getSpeUrlService(BASE_URL, CheckVersionInterface.class)
                .checkVersionInfo(OTHERS_URL);
        obs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted()");
                        mDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError()");
                        mDialog.dismiss();
                    }

                    @Override
                    public void onNext(String s) {
                        CheckVersionBean bean = FastJSONParser.getBean(s, CheckVersionBean.class);
                        int nowVersion = AppUtils.getVersionCode(mContext);
                        if (bean.getVersionCode() > nowVersion) {
                            showDialog(mContext,bean.getUpdateMessage(),bean.getUrl());
                        }
                    }
                });
    }


    /**
     * Show dialog
     */
    private void showDialog(Context context, String content, String apkUrl) {
        UpdateDialog.show(context, content, apkUrl);
    }

    public void showDialog() {
        switch (mType) {
            case CheckVersionConstant.CheckVersionType.SHOW_DIALOG:
                if (mDialog == null) {
                    mDialog = new ProgressDialog(mContext);
                }
                mDialog.setMessage(mContext.getString(R.string.android_auto_update_dialog_checking));
                mDialog.show();
                break;
            default:
                break;
        }
    }
}
