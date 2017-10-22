package cn.gov.bjys.onlinetrain.act;

import android.animation.ObjectAnimator;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.ycl.framework.base.FrameActivity;
import com.zhy.autolayout.utils.AutoUtils;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.ExamBottomLayout;
import cn.gov.bjys.onlinetrain.adapter.DooExamStateFragmentAdapter;
import cn.gov.bjys.onlinetrain.bean.ExamBean;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by dodozhou on 2017/9/27.
 */
public class ExaminationActivity extends FrameActivity {

    @Bind(R.id.viewpager)
    ViewPager mViewPager;


    @Bind(R.id.exam_bottom_layout)
    ExamBottomLayout mExamBottomLayout;

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

    ObjectAnimator bottomLayoutAnimator;
    @Override
    public void initViews() {
        super.initViews();
//        prepareAnimator();

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // if (Math.abs(e1.getRawX() - e2.getRawX()) > 250) {
                // // System.out.println("水平方向移动距离过大");
                // return true;
                // }
                if (Math.abs(velocityY) < 100) {
                    // System.out.println("手指移动的太慢了");
                    return true;
                }

                // 手势向下 down
                if ((e2.getRawY() - e1.getRawY()) > 200) {
                    repeatBottomAnimator();//在此处控制关闭
                    return true;
                }
                // 手势向上 up
                if ((e1.getRawY() - e2.getRawY()) > 200) {
                    startBottomAnimator();
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

        mExamBottomLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });
    }
    private GestureDetector mGestureDetector;
    private void prepareAnimator(){

        float translationsY  = mExamBottomLayout.getTranslationY();
        bottomLayoutAnimator = ObjectAnimator.ofFloat(mExamBottomLayout,"translationY",translationsY,AutoUtils.getPercentWidthSize(1000))
        .setDuration(300);
    }

    private void startBottomAnimator(){
        if(bottomLayoutAnimator == null){
            prepareAnimator();
        }
        bottomLayoutAnimator.start();
    }

    private void repeatBottomAnimator(){
        if(bottomLayoutAnimator == null){
            prepareAnimator();
        }
        bottomLayoutAnimator.reverse();
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
