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

/**
 * 答题界面
 */
public class AnswerLayout extends LinearLayout implements View.OnClickListener {
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
     * @param list
     */
    private List<String> list;
    public void bindDatas(final List<String> list){
        this.list = list;
        Handler handler = new Handler(getContext().getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                AnswerLayout.this.removeAllViews();
                mLayoutList.clear();
                for(int i=0; i < list.size(); i++){
                    String s = list.get(i);
                    SingleAnswerLayout layout =  new SingleAnswerLayout(mContext);
                    layout.setContent(s);
                    layout.setChoiceImgRes(R.mipmap.ic_launcher);
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
        if(i == list.size() - 1) {
            layout.setChoiceImgRes(R.mipmap.wx_login_icon);
        }else{
            layout.setChoiceImgRes(R.mipmap.wx_login_icon);
            mLayoutList.get(list.size() - 1).setChoiceImgRes(R.mipmap.wx_login_icon);
        }
        isClicked = true;
    }
}
