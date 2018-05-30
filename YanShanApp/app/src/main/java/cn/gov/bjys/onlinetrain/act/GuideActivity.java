package cn.gov.bjys.onlinetrain.act;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.util.ToastUtil;
import com.zls.www.statusbarutil.StatusBarUtil;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.customview.CustomTutorialFragment;


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

    @Override
    public void initViews() {
        super.initViews();
    }
}