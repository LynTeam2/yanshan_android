package cn.gov.bjys.onlinetrain.fragment.ExaminationFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hp.hpl.sparta.Text;
import com.ycl.framework.base.FrameFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.ExaminationActivity;
import cn.gov.bjys.onlinetrain.act.PracticeActivity;
import cn.gov.bjys.onlinetrain.act.view.AnswerLayout;
import cn.gov.bjys.onlinetrain.act.view.DooQuestionAnalysisLayout;
import cn.gov.bjys.onlinetrain.bean.ExamBean;
import cn.gov.bjys.onlinetrain.bean.SingleExamBean;


/**
 * 单选题
 */
public class TextSingleExaminationFragment extends FrameFragment implements AnswerLayout.ClickResult {
    public final static String TAG = TextSingleExaminationFragment.class.getSimpleName();
    public final static String POSITION = "POSTION";


    public static TextSingleExaminationFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(TAG, position);
        TextSingleExaminationFragment fragment = new TextSingleExaminationFragment();
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
        View view = inflater.inflate(R.layout.fragment_text_single_examination_layout, container, false);
        return view;
    }


    private int mPosition;

    @Override
    protected void initViews() {
        super.initViews();
        mPosition = getArguments().getInt(TAG);
        if(getActivity() instanceof  ExaminationActivity) {
            mBean = ((ExaminationActivity)getActivity()).getDatas().get(mPosition);
        }


        if(getActivity() instanceof PracticeActivity) {
            mBean = ((PracticeActivity)getActivity()).getDatas().get(mPosition);
        }
        question_content.setText("                             "+ SingleExamBean.SingleChoose.question);
        if(mBean.isDeal()){
            //用户做答之后
            gotoResultLayout();
        }else{
            //用户未答题
            gotoDealLayout();
        }
        single_answer_layout.bindDatas(mBean);
        single_answer_layout.registerClickResult(this);
    }

    public void gotoResultLayout(){
        analysis_layout.setVisibility(View.VISIBLE);
        int i  = SingleExamBean.SingleChoose.isTrue;
        analysis_layout.setmAnalysisAnswer("答案  " + getRealAnswer(i));
        analysis_layout.setmAnalysisContent(SingleExamBean.SingleChoose.fx);
    }

    public String getRealAnswer(int input){
        switch (input){
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            default:
                return "";
        }
    }

    public void gotoDealLayout(){

    }

    @Override
    public void clickRet(ExamBean bean) {

        if(getActivity() instanceof  ExaminationActivity) {
            List<ExamBean> listDatas =  ((ExaminationActivity) getActivity()).getDatas();
            listDatas.set(mPosition,bean);
            ((ExaminationActivity) getActivity()).userChoiceResult(bean, mPosition);
            gotoResultLayout();
        }


        if(getActivity() instanceof PracticeActivity) {
            List<ExamBean> listDatas =  ((PracticeActivity) getActivity()).getDatas();
            listDatas.set(mPosition,bean);
            ((PracticeActivity) getActivity()).userChoiceResult(bean, mPosition);
            gotoResultLayout();
        }
    }
}

