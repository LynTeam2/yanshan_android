package cn.gov.bjys.onlinetrain.fragment.ExaminationFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ycl.framework.base.FrameFragment;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.ClientVideoPlayer;
import cn.gov.bjys.onlinetrain.bean.ExamBean;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by dodozhou on 2017/9/27.
 */
public class VideoExaminationFragment extends FrameFragment{
    public final static String TAG = VideoExaminationFragment.class.getSimpleName();

    public static VideoExaminationFragment newInstance(ExamBean bean) {
        Bundle args = new Bundle();
        args.putParcelable(TAG, bean);
        VideoExaminationFragment fragment = new VideoExaminationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Bind(R.id.client_player)
    ClientVideoPlayer mClientPlayer;
    @Bind(R.id.start_req)
    Button mStartReq;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_video_examination_layout, container, false);
        return view;
    }

    @OnClick({R.id.start_req})
    public void OnTableClick(View v){
     switch (v.getId()) {
         case R.id.start_req:
             mClientPlayer.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4"
                     , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛");
             break;
     }
    }

    @Override
    protected void initViews() {
        super.initViews();
    }


    @Override
    public void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if(!visible){
            //不可见
            JCVideoPlayer.releaseAllVideos();
        }
    }


}
