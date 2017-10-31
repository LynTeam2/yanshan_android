package cn.gov.bjys.onlinetrain.act;

import android.view.View;
import android.widget.TextView;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.view.TitleHeaderView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.RoundImageViewByXfermode;

/**
 * Created by dodozhou on 2017/10/30.
 */
public class SimulatedExamConfirmActivity extends FrameActivity {

    @Bind(R.id.header)
    TitleHeaderView mHeader;

    @Bind(R.id.simulated_exam_avatar)
    RoundImageViewByXfermode mSimulatedExamAvatar;

    @Bind(R.id.user_name)
    TextView mUserName;

    @Bind(R.id.exam_type_name)
    TextView mExamTypeName;

    @Bind(R.id.exam_time)
    TextView mExamTime;

    @Bind(R.id.exam_value)
    TextView mExamValue;

    @Bind(R.id.exam_biaozhun)
    TextView mExamBiaoZhun;

    @Bind(R.id.exam_hint)
    TextView mExamHint;

    @Bind(R.id.start_exam)
    TextView mExamStart;



    @Override
    protected void setRootView() {
        setContentView(R.layout.layout_simulated_exam_layout);
    }

    @Override
    public void initViews() {
        super.initViews();
        initAllViews();
    }

    private void initAllViews(){
        mHeader.setTitleText("模拟考试");
        //TODO
    }

    @OnClick({R.id.start_exam})
    public void onTabClick(View v){
        switch (v.getId()){
            case R.id.start_exam:
                //TODO 考试去
                break;
        }
    }
}
