package cn.gov.bjys.onlinetrain.fragment;

import android.os.Bundle;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.api.UserApi;
import cn.gov.bjys.onlinetrain.utils.multi_file_download.DownInfo;
import cn.gov.bjys.onlinetrain.utils.multi_file_download.HttpDownManager;
import cn.gov.bjys.onlinetrain.utils.multi_file_download.HttpProgressOnNextListener;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dodozhou on 2017/8/7.
 */
public class UndefinedThirdFragment extends FrameFragment  {

    public final static String TAG = UndefinedThirdFragment.class.getSimpleName();

    public final String video_url = "http://vfx.mtime.cn/Video/2017/02/18/mp4/170218171317773949.mp4";


    @Bind(R.id.start)
    CheckBox mStart;

    @Bind(R.id.pause)
    CheckBox mPause;

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
                break;
        }
    }


    private void gotoDownLoad(){

        Call<ResponseBody> call =  HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).getSpeUrlService("http://vfx.mtime.cn/", UserApi.class)
                .getFileDownLoad();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG,"load succ");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG,"load fail");
            }
        });
    }

    private void  gotoTestDownLoad(){

        DownInfo di = new DownInfo();
        di.setBaseUrl("http://vfx.mtime.cn/");
        di.setCountLength(0);
        di.setUrl("Video/2017/02/18/mp4/170218171317773949.mp4");
        di.setSavePath(BaseApplication.getAppContext().getFilesDir().getParent()+ File.separator + "video");
        di.setListener(listener);
        HttpDownManager.getInstance().startDown(di);
    }


    HttpProgressOnNextListener listener = new HttpProgressOnNextListener() {
        @Override
        public void onNext(Object o) {

        }

        @Override
        public void onStart() {

        }

        @Override
        public void onComplete() {

        }

        @Override
        public void updateProgress(long readLength, long countLength) {
            Log.d(TAG, "readLength = " + readLength +"\n  countLength = " +countLength);
        }
    };

}
