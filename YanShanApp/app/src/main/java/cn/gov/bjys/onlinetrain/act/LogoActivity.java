package cn.gov.bjys.onlinetrain.act;

import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.Button;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.sp.SavePreference;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.utils.YSConst;

/**
 * Created by Administrator on 2017/8/2 0002.
 */
public class LogoActivity extends FrameActivity {

    @Bind(R.id.count_down)
    Button mCountDown;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_logo_layout);
    }

    @Override
    public void initViews() {
        super.initViews();
        initCountDownTimer();
        }

//定时器

    CountDownTimer countDownTimer;

    public void initCountDownTimer() {
        countDownTimer = new CountDownTimer(3000, 1000) {
            int count = 3;

            @Override
            public void onTick(long millisUntilFinished) {
                if (count <= 1) {
                    count = 1;
                }
                mCountDown.setText(count+"秒后可进入");
            }

            @Override
            public void onFinish() {
                startNextActivity();
            }
        };
        countDownTimer.start();
    }


    public void startNextActivity() {
        boolean isFirstLogin = SavePreference.getBoolean(this, YSConst.IS_FIRST_LOGIN);
        if (isFirstLogin) {
            startAct(GuideActivity.class);
        } else {
            startAct(MainActivity.class);
        }
    }
}
