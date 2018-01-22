package cn.gov.bjys.onlinetrain.fragment.ExaminationFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.utils.util.ToastUtil;

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
 * Created by Administrator on 2017/12/30 0030.
 */
public class TextMultExaminationFragment extends FrameFragment implements AnswerLayout.ClickResult{
    public final static String TAG = TextMultExaminationFragment.class.getSimpleName();
    public static TextMultExaminationFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(TAG, position);
        TextMultExaminationFragment fragment = new TextMultExaminationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Bind(R.id.start_req1)
    Button start_req1;

    ExamBean mBean;
    @Bind(R.id.question_content)
    TextView question_content;

    @Bind(R.id.single_answer_layout)
    AnswerLayout single_answer_layout;

    @Bind(R.id.analysis_layout)
    DooQuestionAnalysisLayout analysis_layout;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_multi_examination_layout, container, false);
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
        question_content.setText("                             "+ SingleExamBean.MultiChoose.question);
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
        start_req1.setVisibility(View.GONE);
        int[] i  = SingleExamBean.MultiChoose.isTrue;
        analysis_layout.setmAnalysisAnswer("答案  " + getRealAnswerMulti(i));
        analysis_layout.setmAnalysisContent(SingleExamBean.MultiChoose.fx);
    }

    @OnClick({R.id.start_req1})
    public void onTabClick(View v){
        switch (v.getId()){
            case R.id.start_req1:
                if(mBean.getmChoose().size() < 2){
                    ToastUtil.showToast("请至少选择2个答案");
                }else {
                    mBean.setDeal(true);
                    single_answer_layout.sureMulti(mBean);
                    start_req1.setVisibility(View.GONE);
                }
                    break;
        }
    }

    public String getRealAnswerMulti(int[] i){
        String ret = "";
        for(int k=0;k<i.length;k++){
            String s;
            s = k != i.length-1?getRealAnswer(i[k]) + "、":getRealAnswer(i[k]);
            ret = ret+s;
        }
        return  ret;
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

