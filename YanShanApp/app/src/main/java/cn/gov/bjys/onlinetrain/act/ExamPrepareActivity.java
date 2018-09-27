package cn.gov.bjys.onlinetrain.act;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.db.business.QuestionInfoBusiness;
import com.ycl.framework.db.entity.ExamBean;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.util.BgDrawbleUtil;
import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.GlideProxy;
import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.view.TitleHeaderView;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.pop.UnPassAllCourseHintPop;
import cn.gov.bjys.onlinetrain.act.view.RoundImageViewByXfermode;
import cn.gov.bjys.onlinetrain.api.BaseResponse;
import cn.gov.bjys.onlinetrain.api.UserApi;
import cn.gov.bjys.onlinetrain.bean.CourseBean;
import cn.gov.bjys.onlinetrain.bean.CourseProcess;
import cn.gov.bjys.onlinetrain.bean.ExamCountBean;
import cn.gov.bjys.onlinetrain.bean.ExamsBean;
import cn.gov.bjys.onlinetrain.bean.ExamsRole;
import cn.gov.bjys.onlinetrain.bean.SearchBean;
import cn.gov.bjys.onlinetrain.utils.ExamHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import cn.gov.bjys.onlinetrain.utils.YSUserInfoManager;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;



public class ExamPrepareActivity extends FrameActivity {

    @Bind(R.id.start_exam)
    Button start_exam;

    @Bind(R.id.simulated_exam_avatar)
    RoundImageViewByXfermode simulated_exam_avatar;

    @Bind(R.id.user_name)
    TextView user_name;

    @Override
    protected void setRootView() {
        setContentView(R.layout.layout_simulated_exam_layout);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    @Bind(R.id.header)
    TitleHeaderView mHeader;

    @Bind(R.id.exam_type_name) //综合试题
            TextView exam_type_name;

    @Bind(R.id.exam_time) //90分钟
            TextView exam_time;

    @Bind(R.id.exam_value)//90分
            TextView exam_value;

    @Bind(R.id.exam_biaozhun)//根据企业安全标准
            TextView exam_biaozhun;

    @Bind(R.id.exam_hint)//温馨提示
            TextView exam_hint;

    @Override
    public void initViews() {
        super.initViews();
        String nickName = YSUserInfoManager.getsInstance().getUserBean().getNickname();
        if (!TextUtils.isEmpty(nickName)) {
            user_name.setText(nickName);
        }

        String url = YSUserInfoManager.getsInstance().getUserBean().getIcon();
        if (!TextUtils.isEmpty(url)) {
            GlideProxy.loadImgForUrlPlaceHolderDontAnimate(simulated_exam_avatar, url, R.drawable.user_normal_avatar);
        }

        ExamsBean bean = ExamHelper.getInstance().getmExamsBean();
        //TODO 设置值
        mHeader.setTitleText(bean.getExamName());
        exam_type_name.setText(bean.getExamType());
        exam_time.setText(bean.getExamDuration() + "分钟");
        exam_value.setText("答对" + bean.getStandard() + "题，方可合格");
        exam_hint.setText("温馨提示：本次考试属于模拟真实考试环境总分100分，考核需要" + "答对" + bean.getStandard() + "题，方可合格" + "，中途考试交卷不可继续考试");
        preparedExamDatas(bean);
        mExamsBean = bean;
   }

    /**
     * 准备考试试题
     */
    private void preparedExamDatas(ExamsBean bean) {
        Observable.just(bean)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe(new Subscriber<ExamsBean>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ExamsBean examsBean) {
                        // 考试题库，从exam对应的json中获得 tfList scList mcList三个字段
                        List<ExamBean> tfList = examsBean.getTfList();
                        List<ExamBean> scList = examsBean.getScList();
                        List<ExamBean> mcList = examsBean.getMcList();

                        // 考试算法生成的题目列表
                        List<ExamBean> examTfList = new ArrayList<>();
                        List<ExamBean> examScList = new ArrayList<>();
                        List<ExamBean> examMcList = new ArrayList<>();

                        int examTfCount = examsBean.getExamTfCount();
                        int examScCount = examsBean.getExamScCount();
                        int examMcCount = examsBean.getExamMcCount();


                            // 随机乱序
                            tfList = random(tfList);
                            scList = random(scList);
                            mcList = random(mcList);

                            /**
                             * 从题库中抽取题目 下面的代码就是从tfList中抽取前 examTfCount数量的题目，
                             * 因为已经是乱序的，所以可以取前examTfCount数量的题目
                             */

                            examTfList = tfList.subList(0, examTfCount);
                            examScList = scList.subList(0, examScCount);
                            examMcList = mcList.subList(0, examMcCount);

                        mExamPagers.clear();
                        mExamPagers.addAll(examTfList);
                        mExamPagers.addAll(examScList);
                        mExamPagers.addAll(examMcList);
                        //使用单例保存考卷数据
                        ExamHelper.getInstance().setmExamPagers(mExamPagers);
                        exam_biaozhun.post(new Runnable() {
                            @Override
                            public void run() {
                                exam_biaozhun.setText(""+mExamPagers.size());
                            }
                        });
                    }
                });
    }

    private List<ExamBean> mExamPagers = new ArrayList<>();

    public static List<ExamBean> random(List<ExamBean> list) {
        int count = list.size();
        List<ExamBean> randomList = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int num = rand.nextInt(count - i);
            randomList.add(list.get(num));
            list.remove(num);
        }
        return randomList;
    }

    UnPassAllCourseHintPop  mUnPassAllCourseHintPop;


    private ExamsBean mExamsBean;
    @OnClick({R.id.start_exam})
    public void onTabClick(View v) {
        switch (v.getId()) {
            case R.id.start_exam:
                if(true) {
//                if(checkIsPassAllKeShi()) {
                    //全通过
                    getExamCount();
                }else{
                    //未通过
                    if(mUnPassAllCourseHintPop == null){
                        View rootView = LayoutInflater.from(ExamPrepareActivity.this).inflate(R.layout.pop_unpass_hint_layout, null);
                        BgDrawbleUtil.shapeCircleCorner(rootView.findViewById(R.id.linear),12);
                        mUnPassAllCourseHintPop = new UnPassAllCourseHintPop(ExamPrepareActivity.this, rootView);
                    }
                    mUnPassAllCourseHintPop.bindDatas(mHints);
                    mUnPassAllCourseHintPop.showLocation(Gravity.CENTER);
                }
                break;
        }
    }


    /**
     * 是否完成了所有课时 无论对错 并且记录提示语
     * @return
     */

    private List<String>  mHints = new ArrayList<>();
    private boolean checkIsPassAllKeShi(){
        mHints.clear();
        int unPassCount = 0;
        String allPassKeShiStr = SavePreference.getStr(this, YSConst.UserInfo.USER_PASS_KESHI_IDS + YSUserInfoManager.getsInstance().getUserId());
        String[] ids = allPassKeShiStr.split(",");
        List<CourseProcess> courseProcessList = YSUserInfoManager.getsInstance().getUserBean().getCourseProcessList();
        List<String> tempIds = new ArrayList<>();
        for(String id: ids){
            tempIds.add(id);
        }
        for(CourseProcess cp : courseProcessList){
            tempIds.add(cp.getCourseId()+"");
        }
        List<CourseBean> needPassCourses =  mExamsBean.getCourseList();
        for(CourseBean temp:needPassCourses){
            int needId = temp.getId();
            boolean isPass = false;
            for(String id : tempIds){
                if(id.equals(needId+"")){
                    isPass = true;
                    break;
                }
            }
            //未通过情况记录
            if(!isPass){
                unPassCount++;
                mHints.add(temp.getAjType()+":"+temp.getCourseName());//记录未通过课程
            }
        }
        if(unPassCount <= 0){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 第一版的题目生成规则   目前弃用
     * @param examsBean
     */
    @Deprecated
    private void caculateOldOne(ExamsBean examsBean){
        {
            String roleStr = examsBean.getRole();
            ExamsRole role = FastJSONParser.getBean(roleStr, ExamsRole.class);
            //生成题目的`规则
            HashMap<String, Integer> anjianTypeMap = role.getAjType();
            HashMap<String, Integer> questionTypeMap = role.getQuestionType();
            HashMap<String, Integer> difficultyTypeMap = role.getDifficulty();

            List<ExamBean> allExams = QuestionInfoBusiness.getInstance(BaseApplication.getAppContext()).queryAll();
            List<ExamBean> randomExams = random(allExams);
            mExamPagers.clear();
            for (ExamBean tempBean : randomExams) {
                String ajType = tempBean.getAjType();
                String difficulty = tempBean.getDifficulty();
                String questionType = tempBean.getQuestionType();
                if (ajType.isEmpty() || difficulty.isEmpty() || questionType.isEmpty()) {
                    break;
                }
                if (null != anjianTypeMap.get(ajType)
                        && null != difficultyTypeMap.get(difficulty)
                        && null != questionTypeMap.get(questionType)) {
                    mExamPagers.add(tempBean);

                    //安监类型
                    if (anjianTypeMap.get(ajType) == 1) {
                        anjianTypeMap.remove(ajType);
                    } else {
                        anjianTypeMap.put(ajType, anjianTypeMap.get(ajType) - 1);
                    }

                    //题目类型
                    if (questionTypeMap.get(questionType) == 1) {
                        questionTypeMap.remove(questionType);
                    } else {
                        questionTypeMap.put(questionType, questionTypeMap.get(questionType) - 1);
                    }
                    //难度类型
                    if (difficultyTypeMap.get(difficulty) == 1) {
                        difficultyTypeMap.remove(difficulty);
                    } else {
                        difficultyTypeMap.put(difficulty, difficultyTypeMap.get(difficulty) - 1);
                    }
                }
            }
            //使用单例保存考卷数据
            ExamHelper.getInstance().setmExamPagers(mExamPagers);
        }
    }

    private void getExamCount(){
        rx.Observable<BaseResponse<String>> obs;
        obs = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).
                getSpeUrlService(YSConst.BaseUrl.BASE_URL, UserApi.class)
                .getExamCounts(mExamsBean.getId());
        obs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                        if ("1".equals(stringBaseResponse.getCode())) {
                            //suc
                            String res = stringBaseResponse.getResults();
                            ExamCountBean bean = FastJSONParser.getBean(res, ExamCountBean.class);
                            if(bean.getExamCount() < 2){
                                ExamHelper.getInstance().setmExamCount(bean.getExamCount());
                                switch ((int) bean.getExamCount()){
                                    case 0:
                                        ToastUtil.showToast("首次考试，请认真答题");
                                        break;
                                    case 1:
                                        ToastUtil.showToast("二次补考，请认真答题");
                                        break;
                              }

                              if(bean.isValid()) {
                                  startExamPager();
                              }else{
                                  ToastUtil.showToast("当前考试关闭，无法考试");
                              }
                            }else{
                                ToastUtil.showToast("考试次数用尽，无法再次考试");
                            }
                         } else {
                            //fail
                        }
                    }
                });

    }

    //进入考试页面
    private void startExamPager(){
        startAct(ExaminationActivity.class);
        finish();
    }



    /**
     * 第2版的题目生成规则   目前弃用
     * @param examsBean
     */
    @Deprecated
    private void caculateOldTwo(ExamsBean examsBean){
        // 考试题库，从exam对应的json中获得 tfList scList mcList三个字段
        List<ExamBean> tfList = examsBean.getTfList();
        List<ExamBean> scList = examsBean.getScList();
        List<ExamBean> mcList = examsBean.getMcList();
        // 记录三类题目数量和总数
        int tfCount = tfList.size();
        int scCount = scList.size();
        int mcCount = mcList.size();
        float sumCount = tfCount + scCount + mcCount;
        // 考试算法生成的题目列表
        List<ExamBean> examTfList = new ArrayList<>();
        List<ExamBean> examScList = new ArrayList<>();
        List<ExamBean> examMcList = new ArrayList<>();
        // 该考试需要从题库中抓去的三类题目数量和总数;
        int examTfCount;
        int examScCount;
        int examMcCount;
        int examSumCount = 0;
        boolean flag = false;
        // 取半策略 考试题目控制在20-50之间,题库只有20题的时候，直接取题库所有题目
        if (20 == sumCount) {
            examTfList = tfList;
            examScList = scList;
            examMcList = mcList;
        } else {
            if (20 < sumCount && sumCount <= 40) {
                examSumCount = 20;
            } else if (40 < sumCount && sumCount < 100) {
                // 向上取整
                examSumCount = (int)Math.ceil((sumCount) / 2);
            } else if (100 < sumCount) {
                examSumCount = 50;
            }

            // 通过该类型题目在题库中的占比，获取出该类型考试需要抽取多少题目
            if (0 != tfCount && 0 == sumCount%tfCount) {
                examTfCount = (int) (tfCount/sumCount * examSumCount);
            } else {
                // 向下取整
                examTfCount = (int)Math.floor(tfCount/sumCount * examSumCount);
                flag = !flag;
            }

            if (0 != scCount && 0 == sumCount%scCount) {
                examScCount = (int) (scCount/sumCount * examSumCount);
            } else {
                // 向上取整
                examScCount = flag?(int)Math.ceil(scCount/sumCount * examSumCount):(int)Math.floor(scCount/sumCount * examSumCount);
                flag = !flag;
            }

            if (0 != mcCount && 0 == sumCount%mcCount) {
                examMcCount = (int) (mcCount/sumCount * examSumCount);
            } else {
                // 向下取整
                examMcCount = flag?(int)Math.ceil(mcCount/sumCount * examSumCount):(int)Math.floor(mcCount/sumCount * examSumCount);
            }

            // 随机乱序
            tfList = random(tfList);
            scList = random(scList);
            mcList = random(mcList);

            /**
             * 从题库中抽取题目 下面的代码就是从tfList中抽取前 examTfCount数量的题目，
             * 因为已经是乱序的，所以可以取前examTfCount数量的题目
             */

            examTfList = tfList.subList(0, examTfCount);
            examScList = scList.subList(0, examScCount);
            examMcList = mcList.subList(0, examMcCount);
        }
        mExamPagers.clear();
        mExamPagers.addAll(examTfList);
        mExamPagers.addAll(examScList);
        mExamPagers.addAll(examMcList);
        //使用单例保存考卷数据
        ExamHelper.getInstance().setmExamPagers(mExamPagers);
    }
}
