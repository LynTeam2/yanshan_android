package cn.gov.bjys.onlinetrain.act;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.db.business.QuestionInfoBusiness;
import com.ycl.framework.db.entity.ExamBean;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.util.GlideProxy;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.RoundImageViewByXfermode;
import cn.gov.bjys.onlinetrain.bean.ExamsBean;
import cn.gov.bjys.onlinetrain.bean.ExamsRole;
import cn.gov.bjys.onlinetrain.utils.ExamHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by dodo on 2017/11/17.
 */

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
        String nickName = SavePreference.getStr(BaseApplication.getAppContext(), YSConst.UserInfo.USER_SAVE_NICK);
        if (!TextUtils.isEmpty(nickName)) {
            user_name.setText(nickName);
        }

        String url = SavePreference.getStr(BaseApplication.getAppContext(), YSConst.UserInfo.USER_AVATAR_PATH);
        if (!TextUtils.isEmpty(url)) {
            GlideProxy.loadImgForUrlPlaceHolderDontAnimate(simulated_exam_avatar, url, R.drawable.user_normal_avatar);
        }

        ExamsBean bean = ExamHelper.getInstance().getmExamsBean();
        //TODO 设置值
        preparedExamDatas(bean);
    }

    /**
     * 准备考试试题
     */
    private void preparedExamDatas(ExamsBean bean) {
        Observable.just(bean)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe(new Action1<ExamsBean>() {
                    @Override
                    public void call(ExamsBean examsBean) {
                        ExamsRole role = examsBean.getRole();
                        //生成题目的规则
                        HashMap<String, Integer> anjianTypeMap = role.getAnjianType();
                        HashMap<String, Integer> questionTypeMap = role.getQuestionType();
                        HashMap<String, Integer> difficultyTypeMap = role.getDifficulty();

                        List<ExamBean> allExams = QuestionInfoBusiness.getInstance(BaseApplication.getAppContext()).queryAll();
                        List<ExamBean> randomExams = random(allExams);

                        for(ExamBean tempBean:randomExams){
                            String ajType = tempBean.getAjType();
                            String difficulty = tempBean.getDifficulty();
                            String questionType = tempBean.getQuestionType();
                            if (ajType.isEmpty() || difficulty.isEmpty() || questionType.isEmpty()){
                                break;
                            }
                            if (null != anjianTypeMap.get(ajType)
                                    && null != questionTypeMap.get(difficulty)
                                    && null != difficultyTypeMap.get(questionType)) {
                                if(mExamPagers == null){
                                    mExamPagers = new ArrayList<>();
                                }
                                mExamPagers.add(tempBean);

                                //安监类型
                                if(anjianTypeMap.get(ajType) == 1){
                                    anjianTypeMap.remove(ajType);
                                }else{
                                    anjianTypeMap.put(ajType, anjianTypeMap.get(ajType) - 1);
                                }

                                //题目类型
                                if(questionTypeMap.get(questionType) == 1){
                                    questionTypeMap.remove(questionType);
                                }else{
                                    questionTypeMap.put(questionType, questionTypeMap.get(questionType) - 1);
                                }
                                //难度类型
                                if(difficultyTypeMap.get(difficulty) == 1){
                                    difficultyTypeMap.remove(difficulty);
                                }else{
                                    difficultyTypeMap.put(difficulty, difficultyTypeMap.get(difficulty) - 1);
                                }
                            }
                        }
                        //使用单例保存考卷数据
                        ExamHelper.getInstance().setmExamPagers(mExamPagers);
                    }
                });
    }

    private List<ExamBean> mExamPagers;

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


    @OnClick({R.id.start_exam})
    public void onTabClick(View v) {
        switch (v.getId()) {
            case R.id.start_exam:
                startAct(ExaminationActivity.class);
                finish();
                break;
        }
    }
}
