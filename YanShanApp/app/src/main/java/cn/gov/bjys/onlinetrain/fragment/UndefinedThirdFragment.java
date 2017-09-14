package cn.gov.bjys.onlinetrain.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.ycl.framework.utils.util.ToastUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.api.UserApi;
import com.zls.www.mulit_file_download_lib.multi_file_download.HttpDownManager;
import com.zls.www.mulit_file_download_lib.multi_file_download.HttpProgressOnNextListener;
import com.zls.www.mulit_file_download_lib.multi_file_download.api.DownLoadApi;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.business.DataInfoBusiness;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.business.DownLoadInfoBusiness;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.entity.DataInfo;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.entity.DownLoadInfoBean;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dodozhou on 2017/8/7.
 */
public class UndefinedThirdFragment extends FrameFragment {

    public final static String TAG = UndefinedThirdFragment.class.getSimpleName();

    public final String video_url = "http://vfx.mtime.cn/Video/2017/02/18/mp4/170218171317773949.mp4";


    @Bind(R.id.start)
    CheckBox mStart;

    @Bind(R.id.pause)
    CheckBox mPause;

    @Bind(R.id.stop)
    CheckBox mStop;

    @Bind(R.id.pb)
    ProgressBar mProgressBar;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_download_layout, container, false);
    }


    @OnClick({R.id.start, R.id.pb, R.id.pause})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start:
                ToastUtil.showToast("start");
//                gotoDownLoad();
                gotoTestDownLoad();
                break;
            case R.id.pb:
                break;
            case R.id.pause:
                ToastUtil.showToast("pause");
                HttpDownManager.getInstance().pause(mRealInfo);
                break;
            case R.id.stop:
                ToastUtil.showToast("stop");
                HttpDownManager.getInstance().stopDown(mRealInfo);
                break;
        }
    }


    private void gotoDownLoad() {

        Call<ResponseBody> call = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).getSpeUrlService("http://vfx.mtime.cn/", UserApi.class)
                .getFileDownLoad();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "load succ");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "load fail");
            }
        });
    }

    DataInfo mInfo;
    DataInfo mRealInfo;
    public DataInfo preparedData() {
        DataInfo di = new DataInfo();
        di.setCountLength(0);
        di.setAllUrl("http://vfx.mtime.cn/"+"Video/2017/02/18/mp4/170218171317773949.mp4");
        di.setSavePath(BaseApplication.getAppContext().getFilesDir().getParent() + File.separator + "video");
        di.setListener(listener);
        return di;
    }

    private void gotoTestDownLoad() {
        if(mInfo == null) {
            mInfo = preparedData();
            }
        DownLoadInfoBean bean =  DownLoadInfoBusiness.getInstance(BaseApplication.getAppContext()).queryBykey(mInfo.getAllUrl());
        if(null != bean  && null !=  bean.getDataInfo()) {
            mInfo = bean.getDataInfo();
        }

         mRealInfo = mInfo;
        DataInfo info = DataInfoBusiness.getInstance(BaseApplication.getAppContext()).queryBykey(mInfo.getAllUrl());
        if(!TextUtils.isEmpty(info.getAllUrl())){
            mRealInfo = info;
            if(null == mRealInfo.getListener()){
                mRealInfo.setListener(listener);
            }

            if(null == mRealInfo.getService()){
//                mRealInfo.setService(DownLoadApi.class);
            }
        }

        HttpDownManager.getInstance().startDown(mRealInfo);
    }



    HttpProgressOnNextListener listener = new HttpProgressOnNextListener() {
        @Override
        public void onNext(Object o) {

        }

        @Override
        public void onStart() {
            ToastUtil.showToast("开始");
        }

        @Override
        public void onComplete(Object o) {
            ToastUtil.showToast("完成");
            if(o instanceof  DownLoadInfoBean){
                DownLoadInfoBusiness.getInstance(BaseApplication.getAppContext()).deleteItemWithAllUrl(((DownLoadInfoBean)o).getDataInfo().getAllUrl());
            }
        }



        @Override
        public void onError(Throwable e) {
            super.onError(e);
        }

        @Override
        public void onStop() {
            super.onStop();
        }

        @Override
        public void updateProgress(long readLength, long countLength) {
            Log.d(TAG, "readLength = " + readLength + "\n  countLength = " + countLength);
            int res = (int) ((readLength / (countLength * 1.0f)) * 100);
            mProgressBar.setProgress(res);
        }
    };

}
