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

//自定义横条
public class DooLinear extends RelativeLayout {
    public DooLinear(Context context) {
        super(context);
        init();
    }

    public DooLinear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DooLinear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DooLinear(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private ImageView imgIcon;
    private ImageView imgNext;
    private TextView tvContent;

        private void init(){
           View view =  View.inflate(getContext(), R.layout.view_doolinear_layout, null);
            this.addView(view);

          imgIcon = (ImageView) findViewById(R.id.img);
          imgNext = (ImageView) findViewById(R.id.next_btn);
          tvContent = (TextView) findViewById(R.id.content);

        }

    public void setImgIcon(int res){
        imgIcon.setImageDrawable(getResources().getDrawable(res));
    }
    public void setImgNext(int res){
        imgNext.setImageDrawable(getResources().getDrawable(res));
    }

    public void setTvContent(String str){
        tvContent.setText(str);
    }
    public void setTvContent(int res){
        tvContent.setText(getResources().getString(res));
    }

    public void setCustomClick(int viewId,OnClickListener listener){
        findViewById(viewId).setOnClickListener(listener);
    }
}
