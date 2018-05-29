package cn.gov.bjys.onlinetrain.act.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import cn.gov.bjys.onlinetrain.R;

/**
 * Created by dodozhou on 2017/10/30.
 */
public class DooQuestionAnalysisLayout<T> extends DooRootLayout {
    public DooQuestionAnalysisLayout(Context context) {
        super(context);
    }

    public DooQuestionAnalysisLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DooQuestionAnalysisLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
/*
    public DooQuestionAnalysisLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    private TextView mAnalysisAnswer;
//    private RatingBar mRatingBar;
    private TextView mAnalysisContent;
    private TextView mNandu;


    @Override
    public void initViews() {
        mAnalysisAnswer = (TextView) findViewById(R.id.analysis_answer);
//        mRatingBar = (RatingBar) findViewById(R.id.rating_star);
        mAnalysisContent = (TextView) findViewById(R.id.analysis_content);
        mNandu = (TextView) findViewById(R.id.nan_du);
    }

    public void setmAnalysisAnswer(String str){
        mAnalysisAnswer.setText(str);
    }

    public void setmAnalysisContent(String str){
        mAnalysisContent.setText(str);
    }

    public void setmNandu(String str){
        mNandu.setText(str);
    }

    @Override
    public View getRootView() {
        return View.inflate(getContext(), R.layout.layout_question_analysis_layout, null);
    }



    public void bindDatas(T t){

    }

}
