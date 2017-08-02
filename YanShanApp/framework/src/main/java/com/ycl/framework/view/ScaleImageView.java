package com.ycl.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.ycl.framework.view.roundedview.RoundedImageView;

/**
 * Created by ZhangFeng on 16/6/13.
 */
public class ScaleImageView extends ImageView {
    private float scale = 1;

    public ScaleImageView(Context context) {
        super(context);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if(heightSpecSize<widthSpecSize){//当高度小于宽度时，按照比例完整显示
            widthSpecSize = (int) ((heightSpecSize-getPaddingTop()-getPaddingBottom())*scale+getPaddingLeft()+getPaddingRight());
        }else {
            heightSpecSize = (int) ((widthSpecSize-getPaddingLeft()-getPaddingRight())*scale+getPaddingTop()+getPaddingBottom());
        }
        setMeasuredDimension(widthSpecSize, heightSpecSize);
    }

    public void setScale(float scale){
        this.scale = scale;
    }
}
