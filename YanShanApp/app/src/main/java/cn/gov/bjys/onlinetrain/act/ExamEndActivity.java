package cn.gov.bjys.onlinetrain.act;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.db.entity.ExamBean;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.util.GlideProxy;
import com.ycl.framework.view.TitleHeaderView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.RoundImageViewByXfermode;
import cn.gov.bjys.onlinetrain.bean.ExamsBean;
import cn.gov.bjys.onlinetrain.utils.ExamHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;

/**
 * Created by dodo on 2018/1/30.
 */

public class ExamEndActivity extends FrameActivity {
    @Override
    protected void setRootView() {
        setContentView(R.layout.layout_sumulated_exam_end_layout);
    }

    @Bind(R.id.header)
    TitleHeaderView header;
    @Bind(R.id.simulated_exam_avatar)
    RoundImageViewByXfermode simulated_exam_avatar;
    @Bind(R.id.user_name)
    TextView user_name;
    @Bind(R.id.exam_score)
    TextView exam_score;
    @Bind(R.id.exam_time)
    TextView exam_time;
    @Bind(R.id.start_exam)
    Button start_exam;

    @Override
    public void initViews() {
        super.initViews();
        String nickName = SavePreference.getStr(BaseApplication.getAppContext(), YSConst.UserInfo.USER_SAVE_NICK);
        if (!TextUtils.isEmpty(nickName)) {
            user_name.setText(nickName);
        }

        String url = SavePreference.getStr(BaseApplication.getAppContext(), YSConst.UserInfo.USER_AVATAR_PATH);
        if (!TextUtils.isEmpty(url)) {
            GlideProxy.loadImgForUrlPlaceHolderDontAnimate(simulated_exam_avatar, url, R.drawable.user_normal_avatar);
        }
        ExamsBean bean = ExamHelper.getInstance().getmExamsBean();
        exam_time.setText("");//考试时长 根据bean

        List<ExamBean> mExamPagers = ExamHelper.getInstance().getmExamPagers();
        int rightNums = 0;
        for(ExamBean examBean : mExamPagers){
            examBean.getDoRight();
            rightNums++;
        }
        int score = (int) ((rightNums/(mExamPagers.size()*1.0f))*100);
        exam_score.setText(score+"分");
    }

    @OnClick({R.id.start_exam})
    public void onTabClick(View v){
        switch (v.getId()){
            case R.id.start_exam:
                startAct(ExamAnalysisActivity.class);
                break;
        }
    }

}