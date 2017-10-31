package cn.gov.bjys.onlinetrain.act;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ycl.framework.base.BasePopu;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.view.TitleHeaderView;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.pop.EndExamPop;
import cn.gov.bjys.onlinetrain.act.view.ExamBottomLayout;
import cn.gov.bjys.onlinetrain.adapter.DooExamBottomAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooExamStateFragmentAdapter;
import cn.gov.bjys.onlinetrain.bean.ExamBean;
import cn.gov.bjys.onlinetrain.bean.ExamXqBean;
import cn.jzvd.JZVideoPlayer;


/**
 * Created by dodozhou on 2017/9/27.
 */
public class ExaminationActivity extends FrameActivity implements View.OnClickListener{

    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.exam_bottom_layout)
    ExamBottomLayout mExamBottomLayout;

    @Bind(R.id.header)
    TitleHeaderView mHeader;

    BottomSheetBehavior mBehavior;
    DooExamStateFragmentAdapter mExamAdapter;
    DooExamBottomAdapter mDooExamBottomAdapter;

    TimerTask mTimerTask;
    private long mTimes = 50*60;
    private Timer mTimer;
    private Handler mHandler;
    private int mTimerType = TIMER_END;//定时器的情况

    public final static int TIMER_START = 1;

    public final static int TIMER_END = 2;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_examination_layout);
        StatusBarUtil.addStatusForFragment(this,findViewById(R.id.status_bar_layout));
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this,null);
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
             setViewPagerAndExamBottom(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0,false);
        initTimer();
    }

    private void initTimer(){

        mHandler = new Handler(Looper.getMainLooper());
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
             //定时器要做的事情
                if(mTimes > 0){
                    mTimes--;
                }else{
                    mTimerType = TIMER_END;
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                    //UI线程
                        if(mTimerType == TIMER_END){
                            cancelTimer();
                            //TODO 定时器时间结束
                        }else{
                            setTimeToHeader();
                        }
                    }
                });
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTimerTask, 1000, 1000);
        mTimerType = TIMER_START;
    }
    private void setTimeToHeader(){
        String time = getTimesStr(mTimes);
        String content = "倒计时 "+time;
        mHeader.setTitleText(content);
    }

    private String getTimesStr(long time){
        String ret = "";
        ret = ret + (time/3600 == 0 ? "" : time/3600 +":");//小时数
        long fen = time % 3600;
        ret = ret  + (fen/60 > 9 ? fen/60 :"0" + fen/60) +  ":";
        long miao = fen%60;
        ret = ret  + (miao%60 > 9 ? miao%60 :"0" + miao%60);
        return ret;
    }

    private void cancelTimer(){
        if(null != mTimer){
            mTimer.cancel();
            mTimer = null;
        }
    }


    private void setViewPagerAndExamBottom(int positions){
        if(mViewPager != null) {
            mViewPager.setCurrentItem(positions, true);
        }
        if(mDooExamBottomAdapter != null){
          List<ExamXqBean> datas =  mDooExamBottomAdapter.getData();
            if(datas != null && !datas.isEmpty()){
                ExamXqBean bean = datas.get(positions);
                for(ExamXqBean examXqBean:datas){
                    if(examXqBean.getmType() == ExamXqBean.CHOICE)
                    examXqBean.setmType(ExamXqBean.NOMAL);
                }
                if(bean.getmType() == ExamXqBean.NOMAL){
                    bean.setmType(ExamXqBean.CHOICE);
                }
            }
            mDooExamBottomAdapter.notifyDataSetChanged();
        }
    }

    private List<ExamXqBean> mDatas = new ArrayList<>();

    public void createAllTestDatas(){
        for(int i = 0; i<100; i++){
            ExamXqBean bean = new ExamXqBean();
            bean.setmPosition(i);
            bean.setmSpanSize(1);
            bean.setmType(ExamXqBean.NOMAL);
            mDatas.add(bean);
        }
    }


    @Override
    public void initViews() {
        super.initViews();
//        prepareAnimator();
        createAllTestDatas();
        mExamBottomLayout.setmDatas(mDatas);
        mExamBottomLayout.getView(R.id.hand_of_paper).setOnClickListener(this);
        mExamBottomLayout.getView(R.id.show_all_layout).setOnClickListener(this);
        mExamBottomLayout.post(new Runnable() {
            @Override
            public void run() {
            }
        });
        mDooExamBottomAdapter = mExamBottomLayout.getmDooExamBottomAdapter();
        mDooExamBottomAdapter.getData().get(0).setmType(ExamXqBean.CHOICE);//初始化
        mDooExamBottomAdapter.notifyDataSetChanged();
        mDooExamBottomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                setViewPagerAndExamBottom(position);
            }
        });
        mBehavior = BottomSheetBehavior.from(mExamBottomLayout);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }


    private List<ExamBean> prepareDatas(){
        List<ExamBean> list = new ArrayList<>();
        for(int i=0;i<100;i++){
            ExamBean bean = new ExamBean();
            switch (i%2){
                case ExamBean.VIDEO_EXAM:
                    bean.setType(ExamBean.VIDEO_EXAM);
                    break;
                case ExamBean.TEXT_SINGLE_EXAM:
                    bean.setType(ExamBean.TEXT_SINGLE_EXAM);
                    break;
            }
            list.add(bean);
        }
        return list;
    }



    EndExamPop mEndExamPop;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hand_of_paper:
                //todo 交卷
                if(mEndExamPop == null){
                    mEndExamPop = new EndExamPop(this);
                    mEndExamPop.setOnPupClicListener(new BasePopu.OnPupClickListener() {
                        @Override
                        public void onPupClick(int position) {
                            if(EndExamPop.SURE_CLICK == position){
                                cancelTimer();
                            }
                        }
                    });
                }
                mEndExamPop.showLocation(Gravity.CENTER);
                break;

            case R.id.show_all_layout:
                //todo 显示整个layout
                mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED == mBehavior.getState() ?
                        BottomSheetBehavior.STATE_EXPANDED : BottomSheetBehavior.STATE_COLLAPSED);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }


    public List<ExamBean> getDatas(){
        return  mExamAdapter.getmDatas();
    }

    @Override
    public void finish() {
        super.finish();
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }
}
