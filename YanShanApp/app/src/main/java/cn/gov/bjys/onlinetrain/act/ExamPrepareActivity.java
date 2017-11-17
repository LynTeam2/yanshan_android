package cn.gov.bjys.onlinetrain.act;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.helper.ContextHelper;
import com.zls.www.statusbarutil.StatusBarUtil;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;

/**
 * Created by dodo on 2017/11/17.
 */

public class ExamPrepareActivity extends FrameActivity {

    @Bind(R.id.start_exam)
    Button start_exam;

    @Override
    protected void setRootView() {
        setContentView(R.layout.layout_simulated_exam_layout);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    @Override
    public void initViews() {
        super.initViews();

    }

    @OnClick({R.id.start_exam})
    public void onTabClick(View v){
        switch (v.getId()){
            case R.id.start_exam:
                startAct(ExaminationActivity.class);
                break;
        }
    }
}
