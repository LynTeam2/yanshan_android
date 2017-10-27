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
import cn.jzvd.JZVideoPlayerStandard;


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

             break;
     }
    }

    @Override
    protected void initViews() {
        super.initViews();
        ClientVideoPlayer jzVideoPlayerStandard = (ClientVideoPlayer) findViews(viewRoot, R.id.video_player);
        jzVideoPlayerStandard.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "饺子闭眼睛");
//        jzVideoPlayerStandard.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
    }


    @Override
    public void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if(!visible){
            //不可见
        }
    }


}
