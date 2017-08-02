package com.ycl.framework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 遮罩  点击效果 ImageView
 */
public class MaskImageView extends ImageView {

    private boolean maskTag = true; //蒙版
    private boolean strokeTag = false; //线框

    public MaskImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MaskImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaskImageView(Context context) {
        super(context);
    }

    public void setMaskTag(boolean tags) {
        maskTag = tags;
    }

    public void setStrokeTag(boolean tags) {
        strokeTag = tags;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (maskTag) {
                    Drawable drawable = getDrawable();
                    if (drawable != null)
                        drawable.mutate().setColorFilter(0xFFcccccc, PorterDuff.Mode.MULTIPLY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:

                clearMode();

                break;
        }
        return super.onTouchEvent(event);
    }

    private void clearMode() {
        if (maskTag) {
            Drawable drawableUp = getDrawable();
            if (drawableUp != null) {
                drawableUp.clearColorFilter();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (strokeTag) {
            Rect rect = canvas.getClipBounds();
            Paint paint = new Paint();
            paint.setColor(0xffEBEBEB);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            canvas.drawRect(rect, paint);
        }
    }
}
