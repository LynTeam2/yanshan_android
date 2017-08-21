package cn.gov.bjys.onlinetrain.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.utils.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;

/**
 * Created by dodozhou on 2017/8/7.
 */
public class UndefinedThirdFragment extends FrameFragment {

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
                gotoDownLoad();
                break;
            case R.id.pb:
                break;
            case R.id.pause:
                ToastUtil.showToast("pause");
                break;
        }
    }


    private void gotoDownLoad(){

    }
}
