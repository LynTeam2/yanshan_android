package cn.gov.bjys.onlinetrain.act;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hp.hpl.sparta.Text;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.helper.ContextHelper;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.util.GlideProxy;
import com.zls.www.statusbarutil.StatusBarUtil;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.RoundImageViewByXfermode;
import cn.gov.bjys.onlinetrain.utils.YSConst;

/**
 * Created by dodo on 2017/11/17.
 */

public class ExamPrepareActivity extends FrameActivity {

    @Bind(R.id.start_exam)
    Button start_exam;

    @Bind(R.id.simulated_exam_avatar)
    RoundImageViewByXfermode simulated_exam_avatar;

    @Bind(R.id.user_name)
    TextView user_name;

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
        String nickName = SavePreference.getStr(BaseApplication.getAppContext(), YSConst.UserInfo.USER_SAVE_NICK);
        if(!TextUtils.isEmpty(nickName)) {
            user_name.setText(nickName);
        }

        String url = SavePreference.getStr(BaseApplication.getAppContext(), YSConst.UserInfo.USER_AVATAR_PATH);
        if(!TextUtils.isEmpty(url)) {
            GlideProxy.loadImgForUrlPlaceHolderDontAnimate(simulated_exam_avatar, url, R.drawable.user_normal_avatar);
        }

    }



    @OnClick({R.id.start_exam})
    public void onTabClick(View v){
        switch (v.getId()){
            case R.id.start_exam:
                startAct(ExaminationActivity.class);
                finish();
                break;
        }
    }
}
