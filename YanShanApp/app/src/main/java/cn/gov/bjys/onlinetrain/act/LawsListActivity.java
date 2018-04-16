package cn.gov.bjys.onlinetrain.act;
import com.ycl.framework.base.FrameActivity;
import com.zls.www.statusbarutil.StatusBarUtil;
import cn.gov.bjys.onlinetrain.R;


public class LawsListActivity extends FrameActivity {

    @Override
    protected void setRootView() {
            setContentView(R.layout.activity_laws_list_layout);
    }
    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }
}
