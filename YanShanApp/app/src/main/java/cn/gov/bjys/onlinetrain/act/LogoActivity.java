package cn.gov.bjys.onlinetrain.act;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.sp.SavePreference;
import com.zls.www.statusbarutil.StatusBarUtil;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.utils.YSConst;

/**
 * Created by Administrator on 2017/8/2 0002.
 */
public class LogoActivity extends FrameActivity {
    public final static String TAG = LogoActivity.class.getSimpleName();

    @Bind(R.id.count_down)
    Button mCountDown;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_logo_layout);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this,StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    @Override
    public void initViews() {
        super.initViews();
        initCountDownTimer();
        mCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countDownTimer != null){
                    countDownTimer.cancel();
                }
                startNextActivity();
                LogoActivity.this.finish();
            }
        });
        }

    //定时器
    CountDownTimer countDownTimer;

    public void initCountDownTimer() {
        countDownTimer = new CountDownTimer(3200, 1000) {
            int count = 3;

            @Override
            public void onTick(long millisUntilFinished) {
                if (count <= 1) {
                    count = 1;
                }

                mCountDown.setText(--count+"秒后可进入");
                Log.d(TAG,count+"秒后可进入");
            }

            @Override
            public void onFinish() {
                startNextActivity();
                if(countDownTimer != null) {
                    countDownTimer.cancel();
                }
                LogoActivity.this.finish();
            }
        };
        countDownTimer.start();
    }


    public void startNextActivity() {
        boolean notFirstLogin = SavePreference.getBoolean(this, YSConst.NOT_FIRST_LOGIN);
        if (notFirstLogin) {
            startAct(LoginActivity.class);
        } else {
            startAct(GuideActivity.class);
        }
    }
}
