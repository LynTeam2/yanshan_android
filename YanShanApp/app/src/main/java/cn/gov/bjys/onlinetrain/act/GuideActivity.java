package cn.gov.bjys.onlinetrain.act;

import android.os.Bundle;

import com.ycl.framework.base.FrameActivity;
import com.zls.www.statusbarutil.StatusBarUtil;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.customview.CustomTutorialFragment;

/**
 * Created by dodozhou on 2017/8/2.
 */
public class GuideActivity extends FrameActivity {

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_sliding_tutorial);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
    }

    //activity重置  数据重置
    @Override
    protected boolean initBaseParams(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
        }
        replaceTutorialFragment();
        return true;
    }

    public void replaceTutorialFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new CustomTutorialFragment())
                .commit();
    }



}