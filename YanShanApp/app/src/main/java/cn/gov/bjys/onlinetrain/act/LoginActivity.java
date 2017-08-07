package cn.gov.bjys.onlinetrain.act;

import com.ycl.framework.base.FrameActivity;
import com.zls.www.statusbarutil.StatusBarUtil;

import cn.gov.bjys.onlinetrain.R;

/**
 * Created by dodozhou on 2017/8/7.
 */
public class LoginActivity extends FrameActivity {

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }
}
