package cn.gov.bjys.onlinetrain.act;

import android.support.v4.view.ViewPager;

import com.ycl.framework.base.FrameActivity;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.adapter.DooExamStateFragmentAdapter;
import cn.gov.bjys.onlinetrain.bean.ExamBean;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by dodozhou on 2017/9/27.
 */
public class ExaminationActivity extends FrameActivity {

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    DooExamStateFragmentAdapter mExamAdapter;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_examination_layout);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this,StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    @Override
    public void initData() {
        super.initData();
       List<ExamBean> list = prepareDatas();
        mExamAdapter = new DooExamStateFragmentAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(mExamAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
             mViewPager.setCurrentItem(position,false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private List<ExamBean> prepareDatas(){
        List<ExamBean> list = new ArrayList<>();
        for(int i=0;i<100;i++){
            list.add(new ExamBean());
        }
        return list;
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
