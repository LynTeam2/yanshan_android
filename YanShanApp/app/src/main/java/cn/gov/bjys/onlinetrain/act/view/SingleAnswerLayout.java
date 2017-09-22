package cn.gov.bjys.onlinetrain.act.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.gov.bjys.onlinetrain.R;

/**
 * Created by dodozhou on 2017/9/21.
 */
public class SingleAnswerLayout extends RelativeLayout{



    public SingleAnswerLayout(Context context) {
        super(context);
        initViews();
    }

    public SingleAnswerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public SingleAnswerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SingleAnswerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews();
    }

    private ImageView mChoiceImg;
    private TextView mContent;

    private void initViews(){
        this.addView(View.inflate(getContext(), R.layout.view_single_answer_layout,null));
        mChoiceImg = (ImageView) findViewById(R.id.img_choice);
        mContent = (TextView) findViewById(R.id.content);
    }

    public void setChoiceImgRes(int res){
        mChoiceImg.setImageDrawable(getResources().getDrawable(res));
    }

    public void setContent(int res){
        mContent.setText(getResources().getString(res));
    }

    public void setContent(CharSequence content){
        mContent.setText(content);
    }

    public View findRootViewById(int ids){
        return findViewById(ids);
    }

    public void setCustomClick(int ids, View.OnClickListener listener){
        findViewById(ids).setOnClickListener(listener);
    }

}
