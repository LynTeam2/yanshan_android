package cn.gov.bjys.onlinetrain.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.ycl.framework.base.FrameActivity;
import com.zls.www.statusbarutil.StatusBarUtil;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.TitleHeadNormalOne;
import cn.gov.bjys.onlinetrain.fragment.ClassFragment.HomeClassStudySecondFragment;
import cn.gov.bjys.onlinetrain.fragment.ClassFragment.HomeClassStudyThirdFragment;

/**
 * Created by Administrator on 2018/1/28 0028.
 */
public class HomeClassStudyThirdActivity extends FrameActivity {

    public static final String CLASS_TYPE = "TYPE";
    public static final int CLASS_GRID_TAG = 1;
    @Bind(R.id.frame_layout)
    FrameLayout mFrameLayout;
    @Bind(R.id.header)
    TitleHeadNormalOne header;
    private Fragment[] mFragments;


    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_class_layout);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }


    private String jsonName;

    @Override
    public void initViews() {
        super.initViews();
        Intent intent = getIntent();
        Bundle mBundle = intent.getExtras();
        String name = mBundle.getString("title");
        jsonName = mBundle.getString("jsonName");
        header.setTitleText("name");
        mFragments = new Fragment[]{
                getHomeClassStudyThirdFragment(jsonName)
        };
        showFragment();
    }

    private void showFragment() {
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        Intent recIntent = getIntent();
        int r = recIntent.getIntExtra(CLASS_TYPE, CLASS_GRID_TAG);
        switch (r) {
            case CLASS_GRID_TAG:
                fts.add(R.id.frame_layout, mFragments[0]).show(mFragments[0]).commit();
                break;
        }
    }

    public HomeClassStudyThirdFragment getHomeClassStudyThirdFragment(String jsonName) {
        return HomeClassStudyThirdFragment.newInstance(jsonName);
    }


}