package cn.gov.bjys.onlinetrain.act;

import com.ycl.framework.base.FrameActivity;

import cn.gov.bjys.onlinetrain.R;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by dodozhou on 2017/9/27.
 */
public class ExaminationActivity extends FrameActivity {
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_examination_layout);
    }



    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
