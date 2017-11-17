package cn.gov.bjys.onlinetrain.act.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dodo on 2017/11/17.
 */

public class DooCircleView extends View {
    public DooCircleView(Context context) {
        super(context);
    }

    public DooCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public DooCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DooCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private float w = 0;
    private float h = 0;
    private Paint mPaint;
    public void setResColor(int colorRes){
        if(mPaint == null){
            initPaint();
        }
        mPaint.setColor(getContext().getResources().getColor(colorRes));
    }

    private void initPaint(){
         mPaint = new Paint();
         mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if(w <=0 || h<=0){
            w = this.getWidth();
            h = this.getHeight();
        }
        if(mPaint == null){
            initPaint();
        }
       float minL = Math.min(w,h);
        canvas.drawCircle(w/2,h/2,minL/2,mPaint);

    }
}
