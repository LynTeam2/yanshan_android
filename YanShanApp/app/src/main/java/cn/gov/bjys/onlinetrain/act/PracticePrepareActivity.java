package cn.gov.bjys.onlinetrain.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.view.TitleHeaderView;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.List;
import java.util.Random;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.api.ZipCallBackListener;
import cn.gov.bjys.onlinetrain.fragment.ExaminationFragments.VideoExaminationFragment;
import cn.gov.bjys.onlinetrain.fragment.PracticeFragment.ArticlePracticeFragment;
import cn.gov.bjys.onlinetrain.fragment.PracticeFragment.PracticeBaseFragment;
import cn.gov.bjys.onlinetrain.task.KeShiTask;

/**
 * Created by Administrator on 2017/12/31 0031.
 */
public class PracticePrepareActivity extends FrameActivity implements ZipCallBackListener {

    public final static int VIDEO = 2;
    public final static int ARTICLE = 1;

    @Bind(R.id.frame_layout)
    FrameLayout mFrameLayout;
    @Bind(R.id.header)
    TitleHeaderView mHeader;

    private PracticeBaseFragment[] mFragments;


    private KeShiTask mKeShiTask;
    @Override
    protected void setRootView() {
            setContentView(R.layout.activity_prepare_practice_layout);
    }
    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    private int type = ARTICLE;
    private int id;
    @Override
    public void initViews() {
        super.initViews();
        Intent intent = getIntent();
        Bundle mBundle = intent.getExtras();
        type = mBundle.getInt("type");
        id = mBundle.getInt("id");
        mKeShiTask = new KeShiTask(this, id);
        mKeShiTask.execute();

        mFragments = new PracticeBaseFragment[]{
                getVideoExaminationFragment(),
                getArticlePracticeFragment()
        };
        showFragment();
    }

    private void showFragment() {
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        switch (type) {
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


    @Override
    public void zipCallBackListener(List datas) {

    }

    @Override
    public void zipCallBackSuccess() {
        switch (type) {
            case VIDEO:
                mFragments[0].bindData();
                break;
            case ARTICLE:
                mFragments[1].bindData();
                break;
        }
    }

    @Override
    public void zipCallBackFail() {

    }
}
