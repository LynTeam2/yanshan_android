package cn.gov.bjys.onlinetrain.act;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.ycl.framework.base.FrameActivity;
import com.zls.www.statusbarutil.StatusBarUtil;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.fragment.ClassFragment.ClassGridFragment;
import cn.gov.bjys.onlinetrain.fragment.ClassFragment.ClassLinearFragment;


/**
 * Created by Administrator on 2017/10/10 0010.
 */
public class ClassActivity extends FrameActivity {

    public static final String CLASS_TYPE = "TYPE";
    public static final int CLASS_GRID_TAG = 2;
    public static final int CLASS_LINEAR_TAG = 1;
    @Bind(R.id.frame_layout)
    FrameLayout mFrameLayout;
    private Fragment[] mFragments;

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_class_layout);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this,StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    @Override
    public void initViews() {
        super.initViews();
        mFragments = new Fragment[]{
                getClassGridFragment(),
                getClassLinearFragment()
        };
        showFragment();
    }

    private void showFragment() {
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        Intent recIntent = getIntent();
        int r = recIntent.getIntExtra(CLASS_TYPE, CLASS_LINEAR_TAG);
        switch (r) {
            case CLASS_GRID_TAG:
                fts.add(R.id.frame_layout, mFragments[0]).show(mFragments[0]).commit();
                break;
            case CLASS_LINEAR_TAG:
                fts.add(R.id.frame_layout, mFragments[1]).show(mFragments[1]).commit();
                break;
        }
    }

    public ClassGridFragment getClassGridFragment() {
        return ClassGridFragment.newInstance();
    }

    public ClassLinearFragment getClassLinearFragment() {
        return ClassLinearFragment.newInstance();
    }
}
