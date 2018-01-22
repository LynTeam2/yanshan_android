package cn.gov.bjys.onlinetrain.act;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.view.TitleHeaderView;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.Random;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.fragment.ExaminationFragments.VideoExaminationFragment;
import cn.gov.bjys.onlinetrain.fragment.PracticeFragment.ArticlePracticeFragment;

/**
 * Created by Administrator on 2017/12/31 0031.
 */
public class PracticePrepareActivity extends FrameActivity {

    public final static int VIDEO = 0;
    public final static int ARTICLE = 1;

    @Bind(R.id.frame_layout)
    FrameLayout mFrameLayout;
    @Bind(R.id.header)
    TitleHeaderView mHeader;

    private Fragment[] mFragments;
    @Override
    protected void setRootView() {
            setContentView(R.layout.activity_prepare_practice_layout);
    }
    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }
    @Override
    public void initViews() {
        super.initViews();
        mFragments = new Fragment[]{
                getVideoExaminationFragment(),
                getArticlePracticeFragment()
        };
        showFragment();
    }

    private void showFragment() {
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        Random r = new Random();
        int answer = r.nextInt(2);
        switch (answer) {
            case VIDEO:
                mHeader.setTitleText("视频题");
                fts.add(R.id.frame_layout, mFragments[0]).show(mFragments[0]).commit();
                break;
            case ARTICLE:
                mHeader.setTitleText("阅读题");
                fts.add(R.id.frame_layout, mFragments[1]).show(mFragments[1]).commit();
                break;
        }
    }

    public VideoExaminationFragment getVideoExaminationFragment() {
        return VideoExaminationFragment.newInstance();
    }

    public ArticlePracticeFragment getArticlePracticeFragment() {
        return ArticlePracticeFragment.newInstance();
    }

}
