package cn.gov.bjys.onlinetrain.fragment.ExaminationFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hp.hpl.sparta.Text;
import com.ycl.framework.base.FrameFragment;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.AnswerLayout;
import cn.gov.bjys.onlinetrain.bean.ExamBean;


/**
 * 单选题
 */
public class TextSingleExaminationFragment extends FrameFragment {
    public final static String TAG = TextSingleExaminationFragment.class.getSimpleName();

    public static TextSingleExaminationFragment newInstance(ExamBean bean) {
        Bundle args = new Bundle();
        args.putParcelable(TAG, bean);
        TextSingleExaminationFragment fragment = new TextSingleExaminationFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Bind(R.id.question_content)
    TextView question_content;

    @Bind(R.id.single_answer_layout)
    AnswerLayout single_answer_layout;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_text_single_examination_layout, container, false);
        return view;
    }



    @Override
    protected void initViews() {
        super.initViews();
        Bundle mBundle =   getArguments();
        ExamBean bean =  mBundle.getParcelable(TAG);
        single_answer_layout.bindDatas(bean);
    }


}

