package cn.gov.bjys.onlinetrain.act.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.ExamBean;

/**
 * 答题界面
 * 1、处理方式UI变换交给Layout处理
 * 2、选择的结果逻辑交给上层Fragment实现
 */
public class AnswerLayout extends LinearLayout implements View.OnClickListener {

   public  interface ClickResult{
       public void clickRet(int[] ints);
    }

    private Context mContext;
    public AnswerLayout(Context context) {
        super(context);
        mContext = context;
        initViews();
    }

    public AnswerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
    }

    public AnswerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initViews();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnswerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initViews();
    }
    private List<SingleAnswerLayout> mLayoutList;

    private void initViews(){
        if(mLayoutList == null){
            mLayoutList = new ArrayList<>();
        }
        this.setOrientation(LinearLayout.VERTICAL);
    }


    /**
     * UI线程 避免超时任务
     * @param bean
     */
    public void bindDatas(ExamBean bean){
        Handler handler = new Handler(getContext().getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                AnswerLayout.this.removeAllViews();
                mLayoutList.clear();
                for(int i=0; i < 4; i++){
                    String s = "this is "+i;
                    SingleAnswerLayout layout =  new SingleAnswerLayout(mContext);
//                    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    rlp.setMargins(AutoUtils.getPercentWidthSize(70),AutoUtils.getPercentWidthSize(70),AutoUtils.getPercentWidthSize(70),0);
//                    layout.setLayoutParams(rlp);
                 //如果使用LayoutInflater来加载，要把数据attach到一个Parent上去，
                    // 这样才能保留LayouParam一样的信息。
                    // 如果单纯的使用inflater而不设置Parent，则会丢失LayouParam信息

                    layout.setContent(s);
                    switch (i){
                        case 0:
                            layout.setTextById("A",R.id.text_choice);
                            break;
                        case 1:
                            layout.setTextById("B", R.id.text_choice);
                            break;
                        case 2:
                            layout.setTextById("C",R.id.text_choice);
                            break;
                        case 3:
                            layout.setTextById("D",R.id.text_choice);
                            break;
                    }
                    layout.setTag(i);
                    layout.setOnClickListener(AnswerLayout.this);
                    AnswerLayout.this.addView(layout);
                    mLayoutList.add(layout);
                }
            }
        });
    }

    public void addOneOrderData(){


    }

    boolean isClicked = false;
    @Override
    public void onClick(View v) {
       int i = (int) v.getTag();
        if(isClicked){
            return;
        }
        SingleAnswerLayout layout = mLayoutList.get(i);
        layout.findViewById(R.id.text_choice).setBackground(getResources().getDrawable(R.drawable.bg_exam_choice_select));
        isClicked = true;
    }
}
