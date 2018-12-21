package cn.gov.bjys.onlinetrain.fragment.ExaminationFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.db.entity.ExamBean;
import com.ycl.framework.utils.util.advanced.SpannableStringUtils;

import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.ExaminationActivity;
import cn.gov.bjys.onlinetrain.act.PracticeActivity;
import cn.gov.bjys.onlinetrain.act.view.AnswerLayout;
import cn.gov.bjys.onlinetrain.act.view.DooQuestionAnalysisLayout;
import cn.gov.bjys.onlinetrain.act.view.RoundBackgroundColorSpan;

/**
 * Created by Administrator on 2017/12/30 0030.
 */
public class JudegmentExaminationFragment extends FrameFragment implements AnswerLayout.ClickResult {
    public final static String TAG = JudegmentExaminationFragment.class.getSimpleName();

    public static JudegmentExaminationFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(TAG, position);
        JudegmentExaminationFragment fragment = new JudegmentExaminationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    ExamBean mBean;
    @Bind(R.id.question_content)
    TextView question_content;

    @Bind(R.id.single_answer_layout)
    AnswerLayout single_answer_layout;

    @Bind(R.id.analysis_layout)
    DooQuestionAnalysisLayout analysis_layout;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_judgement_examination_layout, container, false);
        return view;
    }


    private int mPosition;

    @Override
    protected void initViews() {
        super.initViews();
        mPosition = getArguments().getInt(TAG);
        if (getActivity() instanceof ExaminationActivity) {
            mBean = ((ExaminationActivity) getActivity()).getDatas().get(mPosition);
        }


        if (getActivity() instanceof PracticeActivity) {
            mBean = ((PracticeActivity) getActivity()).getDatas().get(mPosition);
        }

//        question_content.setText("                             "+ SingleExamBean.Judgment.question);
//        question_content.setText("                             " + mBean.getQuestion());

        SpannableStringUtils.Builder builder = new SpannableStringUtils.Builder()
                .append("  判断题  ")
                .append("  ")
                .append("  ")
                .append(mBean.getQuestion());
        SpannableStringBuilder ssb = builder.create();
        ssb.setSpan(new RoundBackgroundColorSpan(Color.parseColor("#6490ff"),
                Color.parseColor("#FFFFFF")), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        question_content.setText(ssb);
        if (mBean.isDeal()) {
            //用户做答之后
            gotoResultLayout();
        } else {
            //用户未答题
            gotoDealLayout();
        }
        single_answer_layout.bindDatas(mBean);
        single_answer_layout.registerClickResult(this);
    }

    public void gotoResultLayout() {
        analysis_layout.setVisibility(View.VISIBLE);
        String as = mBean.getAnswer();
        analysis_layout.setmAnalysisAnswer("答案  " + getRealAnswer(as));
        analysis_layout.setmAnalysisContent(mBean.getAnalysis());
        analysis_layout.setmNandu(getDifficulty(mBean.getDifficulty()));
    }

    public String getRealAnswer(String input) {
        String ret = "";
        if ("正确".equals(input)) {
            ret = "A";

        } else if ("错误".equals(input)) {
            ret = "B";

        } else {
            ret = "";
        }
        return ret;
    }

    private String getDifficulty(String di){
        String ret ="";
        if("1".equals(di)){
            ret = "初级";
        }else if("2".equals(di)){
            ret = "中级";
        }else if("3".equals(di)){
            ret = "高级";
        }
        return ret;
    }


    public void gotoDealLayout() {

    }

    @Override
    public void clickRet(ExamBean bean) {


        if (getActivity() instanceof ExaminationActivity) {
            List<ExamBean> listDatas = ((ExaminationActivity) getActivity()).getDatas();
            listDatas.set(mPosition, bean);
            ((ExaminationActivity) getActivity()).userChoiceResult(bean, mPosition);
            gotoResultLayout();
        }


        if (getActivity() instanceof PracticeActivity) {
            List<ExamBean> listDatas = ((PracticeActivity) getActivity()).getDatas();
            listDatas.set(mPosition, bean);
            ((PracticeActivity) getActivity()).userChoiceResult(bean, mPosition);
            gotoResultLayout();
        }
    }


}
