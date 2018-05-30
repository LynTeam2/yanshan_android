package cn.gov.bjys.onlinetrain.act;

import android.view.View;
import android.widget.TextView;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.view.TitleHeaderView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.RoundImageViewByXfermode;


public class SimulatedExamEndActivity extends FrameActivity {

    @Bind(R.id.header)
    TitleHeaderView mHeader;

    @Bind(R.id.simulated_exam_avatar)
    RoundImageViewByXfermode mAvatar;

    @Bind(R.id.user_name)
    TextView  mUserName;

    @Bind(R.id.exam_score)
    TextView  mExamScore;

    @Bind(R.id.exam_time)
    TextView  mExamTime;


    @Bind(R.id.exam_analysis)
    TextView mExamAnalysis;

    @Override
    protected void setRootView() {
       setContentView(R.layout.layout_simulated_exam_end_layout);
    }

    @Override
    public void initViews() {
        super.initViews();
    }


    @OnClick({R.id.exam_analysis})
    public void onTabClick(View v){
        switch (v.getId()){
            case R.id.exam_analysis:
                //TODO 开始分析按钮
                break;
            default:
                break;
        }
    }
}
