package cn.gov.bjys.onlinetrain.act.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ycl.framework.utils.util.advanced.SpannableStringUtils;

import java.util.ArrayList;
import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.adapter.DooExamBottomAdapter;
import cn.gov.bjys.onlinetrain.bean.ExamXqBean;

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
    private TextView mErrorNums;//错题数
    private TextView mRightNums;//对题目数


    private int mRightNum = 0;
    private int mErrorNum = 0;

    @Override
    public void initViews() {

        mNowQuestion = (TextView) findViewById(R.id.now_question);
        setNormalQuestionContent(0);//设置"0/100"
        mHandOfPaper = (LinearLayout) findViewById(R.id.hand_of_paper);
        mShowAllLayout = (LinearLayout) findViewById(R.id.show_all_layout);
        mErrorNums = (TextView) findViewById(R.id.error_nums);
        mRightNums   = (TextView) findViewById(R.id.right_nums);
        initRecyView();
    }

    public void setRightNums(){
        mRightNum++;
        mRightNums.setText(mRightNum+"");
    }

    public void setErrorNum(){
        mErrorNum ++;
        mErrorNums.setText(mErrorNum+"");
    }

    RecyclerView mRecyclerView;
    DooExamBottomAdapter mDooExamBottomAdapter;
    private List<ExamXqBean> mDatas = new ArrayList<>();
    private void initRecyView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mDooExamBottomAdapter = new DooExamBottomAdapter(mDatas);

        final GridLayoutManager manager = new GridLayoutManager(getContext(),6);
        mDooExamBottomAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return mDatas.get(position).getmSpanSize();
            }
        });
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mDooExamBottomAdapter);
    }


    public List<ExamXqBean> getmDatas() {
        return mDatas;
    }

    public void setmDatas(final List<ExamXqBean> mDatas) {
        this.mDatas = mDatas;
        if(Thread.currentThread() == Looper.getMainLooper().getThread()) {
            refreshUI(mDatas);
        }else {
            Handler mHandler =new Handler(Looper.getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                 refreshUI(mDatas);
                }
            });
        }
    }

    private void refreshUI(List<ExamXqBean> mDatas){
        setNowQuesitonContent(1,mDatas.size());
        mDooExamBottomAdapter.setNewData(mDatas);
    }
    public List<ExamXqBean> createExamxqDatas(int size){
      return   prepareDatas(size);
    }


    public List<ExamXqBean> prepareDatas(int size){
        mDatas.clear();
        for(int i=0; i<size;i++){
            ExamXqBean bean = new ExamXqBean();
            bean.setmPosition(i);
            bean.setmSpanSize(1);
            bean.setmType(ExamXqBean.NOMAL);
            mDatas.add(bean);
        }
        return mDatas;
    }

    @Override
    public View getRootView() {
        return View.inflate(getContext(), R.layout.view_exambottom_layout, null);
    }

    public final static int NORMAL_ALL_QUESTIONS = 100;
    public void setNormalQuestionContent(int nowPostion){
            setNowQuesitonContent(nowPostion,NORMAL_ALL_QUESTIONS);
    }

    public void setNowQuesitonContent(int nowPostion,int allNums){

        SpannableStringBuilder ssb = new SpannableStringUtils.Builder()
                .append(nowPostion + "").setForegroundColor(getResources().getColor(R.color.normal_black))
                .append("/"+allNums).setForegroundColor(getResources().getColor(R.color.normal_gray))//.setFontSize(sizeSmall)
                .create();
        mNowQuestion.setText(ssb);
    }

    public View getView(int id){
       return  findViewById(id);
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

    public DooExamBottomAdapter getmDooExamBottomAdapter(){
        return mDooExamBottomAdapter;
    }
}
