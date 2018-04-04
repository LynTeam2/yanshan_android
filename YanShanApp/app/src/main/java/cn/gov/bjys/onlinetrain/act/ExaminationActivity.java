package cn.gov.bjys.onlinetrain.act;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ycl.framework.base.BasePopu;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.db.business.ExamPagerInfoBusiness;
import com.ycl.framework.db.entity.ExamBean;
import com.ycl.framework.db.entity.SaveExamPagerBean;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.view.TitleHeaderView;
import com.zhy.autolayout.utils.AutoUtils;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.pop.EndExamPop;
import cn.gov.bjys.onlinetrain.act.view.ExamBottomLayout;
import cn.gov.bjys.onlinetrain.adapter.DooExamBottomAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooExamStateFragmentAdapter;
import cn.gov.bjys.onlinetrain.bean.ExamXqBean;
import cn.gov.bjys.onlinetrain.bean.ExamsBean;
import cn.gov.bjys.onlinetrain.utils.DataHelper;
import cn.gov.bjys.onlinetrain.utils.ExamHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import cn.gov.bjys.onlinetrain.utils.YSUserInfoManager;


/**
 * Created by dodozhou on 2017/9/27.
 */
public class ExaminationActivity extends FrameActivity implements View.OnClickListener {

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
    private long mAllTimes = 50*60;
    private long mTimes = 50 * 60;
    private Timer mTimer;
    private Handler mHandler;
    private int mTimerType = TIMER_END;//定时器的情况

    public final static int TIMER_START = 1;

    public final static int TIMER_END = 2;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_examination_layout);
        StatusBarUtil.addStatusForFragment(this, findViewById(R.id.status_bar_layout));
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
    }


    public void initExamDatas() {
        initExamsBean();
        getmQuestionsList();//
        //初始化错误
        mErrorQuestionsList = new ArrayList<>();
        //初始化正确
        mRightQuestionsList = new ArrayList<>();
    }

    List<ExamBean> mQuestionsList;
    List<ExamBean> mErrorQuestionsList;
    List<ExamBean> mRightQuestionsList;
    ExamsBean mExamsBean;


    private List<ExamBean> getmQuestionsList() {
        if (mQuestionsList == null)
            mQuestionsList = ExamHelper.getInstance().getmExamPagers();
        return mQuestionsList;
    }

    private void initExamsBean() {
        mExamsBean = ExamHelper.getInstance().getmExamsBean();
        mTimes = mExamsBean.getExamDuration() * 60;//秒钟
        mAllTimes = mExamsBean.getExamDuration() * 60;//秒钟
    }

    public void CalAnswer(ExamBean bean, boolean isRight) {
        if (isRight) {
            mRightQuestionsList.add(bean);
        } else {
            mErrorQuestionsList.add(bean);
        }
        if (mRightQuestionsList.size() + mErrorQuestionsList.size() >= mQuestionsList.size()) {
            //结束弹框
//            handOfPaper();
        }
    }

    @Override
    public void initData() {
        super.initData();
//        List<ExamBean> list = prepareDatas();
        mExamAdapter = new DooExamStateFragmentAdapter(getSupportFragmentManager(), mQuestionsList);
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
        mViewPager.setCurrentItem(0, false);
        initTimer();
    }


    private void initTimer() {

        mHandler = new Handler(Looper.getMainLooper());
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                //定时器要做的事情
                if (mTimes > 0) {
                    mTimes--;
                } else {
                    mTimerType = TIMER_END;
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI线程
                        if (mTimerType == TIMER_END) {
                            exitPager();
                        } else {
                            setTimeToHeader();
                        }
                    }
                });
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTimerTask, 1000, 1000);
        mTimerType = TIMER_START;
        setTimeToHeader();//进入activity显示时间
    }

    private void setTimeToHeader() {
        String time = getTimesStr(mTimes);
        String content = "倒计时 " + time;
        mHeader.setTitleText(content);
    }

    private String getTimesStr(long time) {
        String ret = "";
        ret = ret + (time / 3600 == 0 ? "" : time / 3600 + ":");//小时数
        long fen = time % 3600;
        ret = ret + (fen / 60 > 9 ? fen / 60 : "0" + fen / 60) + ":";
        long miao = fen % 60;
        ret = ret + (miao % 60 > 9 ? miao % 60 : "0" + miao % 60);
        return ret;
    }

    private void cancelTimer() {
        if (null != mTimer) {
            mTimer.cancel();
            mTimer = null;
        }
    }


    private void setViewPagerAndExamBottom(int positions) {
        if (positions > mQuestionsList.size() - 1) {
            return;
        }
        mExamBottomLayout.setNowQuesitonContent(positions + 1, mQuestionsList.size());
        if (mViewPager != null) {
            mViewPager.setCurrentItem(positions, true);
        }
        if (mDooExamBottomAdapter != null) {
            List<ExamXqBean> datas = mDooExamBottomAdapter.getData();
            if (datas != null && !datas.isEmpty()) {
                ExamXqBean bean = datas.get(positions);
                for (ExamXqBean examXqBean : datas) {
                    if (examXqBean.getmType() == ExamXqBean.CHOICE)
                        examXqBean.setmType(ExamXqBean.NOMAL);
                }
                if (bean.getmType() == ExamXqBean.NOMAL) {
                    bean.setmType(ExamXqBean.CHOICE);
                }
            }
            mDooExamBottomAdapter.notifyDataSetChanged();
        }
    }

    private List<ExamXqBean> mDatas = new ArrayList<>();

    public void createBottomDatas() {
        mDatas.clear();
        for (int i = 0; i < mQuestionsList.size(); i++) {
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
        initExamDatas();
        createBottomDatas();
        ViewGroup.LayoutParams blp = mExamBottomLayout.getLayoutParams();
        blp.height = AutoUtils.getPercentHeightSize(1120);//重置高度
        mExamBottomLayout.setLayoutParams(blp);
        mExamBottomLayout.setmDatas(mDatas);
        mExamBottomLayout.getView(R.id.hand_of_paper).setOnClickListener(this);
        mExamBottomLayout.getView(R.id.show_all_layout).setOnClickListener(this);

        mDooExamBottomAdapter = mExamBottomLayout.getmDooExamBottomAdapter();
        mDooExamBottomAdapter.getData().get(0).setmType(ExamXqBean.CHOICE);//初始化
        mDooExamBottomAdapter.notifyDataSetChanged();
        mDooExamBottomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                setViewPagerAndExamBottom(position);
            }
        });
        mHeader.hideLeftImg();
        mHeader.setCustomClickListener(com.ycl.framework.R.id.iv_title_header_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handOfPaper();
            }
        });
        mBehavior = BottomSheetBehavior.from(mExamBottomLayout);
        mBehavior.setPeekHeight(AutoUtils.getPercentHeightSize(120));
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }


    private List<ExamBean> prepareDatas() {
        List<ExamBean> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int rInt = random.nextInt(3);
            ExamBean bean = new ExamBean();
            switch (rInt % 3) {
                case ExamBean.TEXT_JUDGMENT_EXAM:
                    bean.setExamBeanType(ExamBean.TEXT_JUDGMENT_EXAM);
                    break;
                case ExamBean.TEXT_SINGLE_EXAM:
                    bean.setExamBeanType(ExamBean.TEXT_SINGLE_EXAM);
                    break;
                case ExamBean.TEXT_MULTIPLE_EXAM:
                    bean.setExamBeanType(ExamBean.TEXT_MULTIPLE_EXAM);
                    break;
                case ExamBean.VIDEO_EXAM:
                    bean.setExamBeanType(ExamBean.VIDEO_EXAM);
                    break;
            }
            list.add(bean);
        }
        return list;
    }


    EndExamPop mEndExamPop;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hand_of_paper:
                //todo 交卷
                handOfPaper();
                break;

            case R.id.show_all_layout:
                //todo 显示整个layout
                mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED == mBehavior.getState() ?
                        BottomSheetBehavior.STATE_EXPANDED : BottomSheetBehavior.STATE_COLLAPSED);
                break;
        }
    }

    private void handOfPaper() {
        //todo 交卷
        if (mEndExamPop == null) {
            mEndExamPop = new EndExamPop(this);
            mEndExamPop.setOnPupClicListener(new BasePopu.OnPupClickListener() {
                @Override
                public void onPupClick(int position) {
                    if (EndExamPop.SURE_CLICK == position) {
                        exitPager();
                    }
                }
            });
        }
        mEndExamPop.showLocation(Gravity.CENTER);
    }


    //存下错题
    private void saveErrorList() {

        StringBuffer allErrorStr = new StringBuffer();
        String strs = SavePreference.getStr(this, YSConst.UserInfo.USER_ERROR_IDS + YSUserInfoManager.getsInstance().getUserId());
        String[] listStr = strs.split(",");

        List<String> allErrorList = new ArrayList<>();
        for (String temp : listStr) {
            if (TextUtils.isEmpty(temp))
                continue;
            allErrorList.add(temp);
        }
        for (ExamBean bean : mErrorQuestionsList) {
            String uid = bean.getUid();
            allErrorList.remove(uid);
            allErrorList.add(0, uid);
        }

        for (int i = 0; i < allErrorList.size(); i++) {
            if (i == allErrorList.size() - 1) {
                allErrorStr.append("" + allErrorList.get(i));
            } else {
                allErrorStr.append(allErrorList.get(i) + ",");
            }
        }

        SavePreference.save(this,
                YSConst.UserInfo.USER_ERROR_IDS + YSUserInfoManager.getsInstance().getUserId(),
                allErrorStr.toString());
    }

    //存下整张试卷
    public void saveExamPager() {
        //
        StringBuilder allSb = new StringBuilder();
        StringBuilder errorSb = new StringBuilder();
        StringBuilder notdoSb = new StringBuilder();
        StringBuilder rightSb = new StringBuilder();

        //
        StringBuilder mMultiSb = new StringBuilder();
        StringBuilder mMultiErrorSb = new StringBuilder();
        StringBuilder mMultiRightSb = new StringBuilder();
        StringBuilder mTurefalseSb = new StringBuilder();
        StringBuilder mTurefalseErrorSb = new StringBuilder();
        StringBuilder mTurefalseRightSb = new StringBuilder();
        StringBuilder mSimpleSb = new StringBuilder();
        StringBuilder mSimpleErrorSb = new StringBuilder();
        StringBuilder mSimpleRightSb = new StringBuilder();


        for (int i=0; i < mQuestionsList.size(); i++) {
            ExamBean examBean = mQuestionsList.get(i);
            String uid = examBean.getUid();
            int type = examBean.getDoRight();
            String questionType = examBean.getQuestionType();
            if(i == 0){
                allSb.append(uid+"");//allpager没有问题
            switch (type) {
                case ExamBean.ERROR:
                    errorSb.append(uid+"");
                    if("mc".equals(questionType)){
                        mMultiErrorSb.append(uid+"");
                    }else if("sc".equals(questionType)){
                        mSimpleErrorSb.append(uid+"");
                    }else if("tf".equals(questionType)){
                        mTurefalseErrorSb.append(uid+"");
                    }
                    break;
                case ExamBean.NOT_DO:
                    notdoSb.append(uid+"");
                    break;
                case ExamBean.RIGHT:
                    rightSb.append(uid+"");

                    if("mc".equals(questionType)){
                        mMultiRightSb.append(uid+"");
                    }else if("sc".equals(questionType)){
                        mSimpleRightSb.append(uid+"");
                    }else if("tf".equals(questionType)){
                        mTurefalseRightSb.append(uid+"");
                    }

                    break;
                }

                if("mc".equals(questionType)){
                    mMultiSb.append(uid+"");
                }else if("sc".equals(questionType)){
                    mSimpleSb.append(uid+"");
                }else if("tf".equals(questionType)){
                    mTurefalseSb.append(uid+"");
                }

            }else{
                allSb.append(","+uid);
                switch (type) {
                    case ExamBean.ERROR:
                        errorSb.append("," + uid);
                        if("mc".equals(questionType)){
                            mMultiErrorSb.append(","+uid);
                        }else if("sc".equals(questionType)){
                            mSimpleErrorSb.append(","+uid);
                        }else if("tf".equals(questionType)){
                            mTurefalseErrorSb.append("," + uid);
                        }
                        break;
                    case ExamBean.NOT_DO:
                        notdoSb.append("," + uid);
                        break;
                    case ExamBean.RIGHT:
                        rightSb.append("," + uid);

                        if("mc".equals(questionType)){
                            mMultiRightSb.append("," + uid);
                        }else if("sc".equals(questionType)){
                            mSimpleRightSb.append("," + uid);
                        }else if("tf".equals(questionType)){
                            mTurefalseRightSb.append("," + uid);
                        }
                        break;
                }

                if("mc".equals(questionType)){
                    mMultiSb.append(","+uid);
                }else if("sc".equals(questionType)){
                    mSimpleSb.append(","+uid);
                }else if("tf".equals(questionType)){
                    mTurefalseSb.append("," + uid);
                }
            }
        }

        SaveExamPagerBean bean = new SaveExamPagerBean();
        bean.setUserid(YSUserInfoManager.getsInstance().getUserId());
        bean.setExampagerid(mExamsBean.getId());
        bean.setExamName(mExamsBean.getExamName());
        bean.setmAllPager(DataHelper.clearEmptyString(allSb.toString()));
        bean.setmErrorPager(DataHelper.clearEmptyString(errorSb.toString()));
        bean.setmNotDoPager(DataHelper.clearEmptyString(notdoSb.toString()));
        bean.setmRightPager(DataHelper.clearEmptyString(rightSb.toString()));

        bean.setmMultiPager(DataHelper.clearEmptyString(mMultiSb.toString()));
        bean.setmMultiErrorPager(DataHelper.clearEmptyString(mMultiErrorSb.toString()));
        bean.setmMultiRightPager(DataHelper.clearEmptyString(mMultiRightSb.toString()));

        bean.setmSimplePager(DataHelper.clearEmptyString(mSimpleSb.toString()));
        bean.setmSimpleErrorPager(DataHelper.clearEmptyString(mSimpleErrorSb.toString()));
        bean.setmSimpleRightPager(DataHelper.clearEmptyString(mSimpleRightSb.toString()));

        bean.setmTrueFalsePager(DataHelper.clearEmptyString(mTurefalseSb.toString()));
        bean.setmTrueFalseErrorPager(DataHelper.clearEmptyString(mTurefalseErrorSb.toString()));
        bean.setmTrueFalseRightPager(DataHelper.clearEmptyString(mTurefalseRightSb.toString()));

        bean.setCreateTime(System.currentTimeMillis());
        bean.setUseTimes(mAllTimes - mTimes);//用户使用的做题时间

        //试卷名称
        bean.setExamName(mExamsBean.getExamName());

        //考试分数
        bean.setmScore((long) ((mRightQuestionsList.size()/(mQuestionsList.size()*1.0f))*100L));

        //是否及格
        bean.setmJige(mRightQuestionsList.size() > mExamsBean.getStandard());

        //插入数据库
        ExamPagerInfoBusiness.getInstance(this).createOrUpdate(bean);
    }




    private void exitPager() {
//        saveErrorList();//考试错题不需要放进我的错题中
        saveExamPager();
        cancelTimer();
        startAct(ExamEndActivity.class);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            handOfPaper();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    public List<ExamBean> getDatas() {
        return mExamAdapter.getmDatas();
    }

    public void userChoiceResult(ExamBean bean, int position) {
        int c = bean.getmChoose().get(0);
        String answer = bean.getAnswer();
        int i;
        switch (bean.getExamBeanType()) {
            case ExamBean.TEXT_JUDGMENT_EXAM:
//                i = SingleExamBean.Judgment.isTrue;
                if (answer.equals("true")) {
                    i = 0;
                } else {
                    i = 0;
                }
                if (c != i) {
                    //错
                    bean.setDoRight(ExamBean.ERROR);
                    CalAnswer(bean, false);
                    refreshBottomCircleUI(position, false);//更新圆圈UI
                    mExamBottomLayout.setErrorNum();//错题目数量自增一
                } else {
                    //对
                    bean.setDoRight(ExamBean.RIGHT);
                    CalAnswer(bean, true);
                    refreshBottomCircleUI(position, true);
                    mExamBottomLayout.setRightNums();//对题目数量自增一
                }
                break;
            case ExamBean.TEXT_SINGLE_EXAM:
//                i = SingleExamBean.SingleChoose.isTrue;
                if (answer.equals("choiceA")) {
                    i = 0;
                } else if (answer.equals("choiceB")) {
                    i = 1;
                } else if (answer.equals("choiceC")) {
                    i = 2;
                } else if (answer.equals("choiceD")) {
                    i = 3;
                } else {
                    i = 0;
                }


                if (c != i) {
                    //错
                    bean.setDoRight(ExamBean.ERROR);
                    CalAnswer(bean, false);
                    refreshBottomCircleUI(position, false);//更新圆圈UI
                    mExamBottomLayout.setErrorNum();//错题目数量自增一
                } else {
                    //对

                    bean.setDoRight(ExamBean.RIGHT);
                    CalAnswer(bean, true);

                    refreshBottomCircleUI(position, true);
                    mExamBottomLayout.setRightNums();//对题目数量自增一
                }
                break;
            case ExamBean.TEXT_MULTIPLE_EXAM:
                List<Integer> lists = bean.getmChoose();
//                int[] cc = cn.gov.bjys.onlinetrain.bean.SingleExamBean.MultiChoose.isTrue;
                List<Integer> cc = new ArrayList<>();
                String[] res = answer.split(",");
                for (String s : res) {
                    int k;
                    if (s.equals("choiceA")) {
                        k = 0;
                    } else if (s.equals("choiceB")) {
                        k = 1;
                    } else if (s.equals("choiceC")) {
                        k = 2;
                    } else if (s.equals("choiceD")) {
                        k = 3;
                    } else {
                        k = 0;
                    }
                    cc.add(k);
                }

                if (lists.size() == cc.size()) {
                    boolean isEq = false;
                    boolean isRealNotEq = false;
                    int count = 0;
                    for (int tempc : cc) {
                        isEq = false;
                        for (int k = 0; k < lists.size(); k++) {
                            int tempUserC = lists.get(k);
                            if (tempUserC == tempc) {
                                isEq = true;
                                break;
                            }
                            if (k == lists.size() - 1) {
                                if (!isEq) {
                                    isRealNotEq = true;
                                }
                            }
                        }
                    }
                    if (!isRealNotEq) {
                        //全对
                        bean.setDoRight(ExamBean.RIGHT);
                        CalAnswer(bean, true);
                        refreshBottomCircleUI(position, true);
                        mExamBottomLayout.setRightNums();//对题目数量自增一
                    } else {
                        bean.setDoRight(ExamBean.ERROR);
                        CalAnswer(bean, false);
                        refreshBottomCircleUI(position, false);//更新圆圈UI
                        mExamBottomLayout.setErrorNum();//错题目数量自增一
                    }
                } else {
                    bean.setDoRight(ExamBean.ERROR);
                    CalAnswer(bean, false);
                    refreshBottomCircleUI(position, false);//更新圆圈UI
                    mExamBottomLayout.setErrorNum();//错题目数量自增一
                }

                break;
        }
        setViewPagerAndExamBottom(position + 1);
    }


    public void refreshBottomCircleUI(int positions, boolean isRight) {
        if (mDooExamBottomAdapter != null) {
            List<ExamXqBean> datas = mDooExamBottomAdapter.getData();
            if (datas != null && !datas.isEmpty()) {
                ExamXqBean bean = datas.get(positions);
                bean.setmType(isRight ? ExamXqBean.RIGHT : ExamXqBean.FAIL);
            }
            mDooExamBottomAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void finish() {
        super.finish();

        if(mEndExamPop != null){
            mEndExamPop.dismiss();
            mEndExamPop = null;
        }

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
