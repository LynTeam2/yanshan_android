package cn.gov.bjys.onlinetrain.act.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ycl.framework.utils.util.advanced.SpannableStringUtils;

import cn.gov.bjys.onlinetrain.R;

/**
 * Created by dodozhou on 2017/10/20.
 */
public class ExamBottomLayout extends DooRootLayout implements View.OnClickListener {
    public ExamBottomLayout(Context context) {
        super(context);
    }

    public ExamBottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExamBottomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExamBottomLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private LinearLayout mHandOfPaper;//交卷
    private LinearLayout mShowAllLayout;//点击显示全部区域
    private TextView mNowQuestion;//目前题目的位置


    @Override
    public void initViews() {

        mNowQuestion = (TextView) findViewById(R.id.now_question);
        setNormalQuestionContent(0);//设置"0/100"
        mHandOfPaper = (LinearLayout) findViewById(R.id.hand_of_paper);
        mShowAllLayout = (LinearLayout) findViewById(R.id.show_all_layout);
        initRecyView();
    }

    RecyclerView mRecyclerView;
    private void initRecyView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);

        mRecyclerView.setAdapter();
    }

    @Override
    public View getRootView() {
        return View.inflate(getContext(), R.layout.view_exambottom_layout, null);
    }

    public final static int NORMAL_ALL_QUESTIONS = 100;
    private void setNormalQuestionContent(int nowPostion){
            setNowQuesitonContent(nowPostion,NORMAL_ALL_QUESTIONS);
    }

    private void setNowQuesitonContent(int nowPostion,int allNums){

        SpannableStringBuilder ssb = new SpannableStringUtils.Builder()
                .append(nowPostion + "").setForegroundColor(getResources().getColor(R.color.normal_black))
                .append("/"+allNums).setForegroundColor(getResources().getColor(R.color.normal_gray))//.setFontSize(sizeSmall)
                .create();
        mNowQuestion.setText(ssb);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hand_of_paper:
            //todo 交卷
                break;

            case R.id.show_all_layout:
            //todo 显示整个layout
                break;
        }
    }
}
