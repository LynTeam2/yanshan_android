package com.ycl.framework.view.roundedview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.ycl.framework.utils.util.YisLoger;

/**
 * Created by ZhangFeng on 16/6/13.
 */
public class ScaleRoundedImageView extends android.support.v7.widget.AppCompatImageView {
    private float scale;

    private Matrix matrix;
    private Paint paint;

    public ScaleRoundedImageView(Context context) {
        super(context);
        init();
    }

    public ScaleRoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScaleRoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        matrix = new Matrix();
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) ((widthSpecSize - getPaddingLeft() - getPaddingRight()) * scale + getPaddingTop() + getPaddingBottom());
        setMeasuredDimension(widthSpecSize, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable mDrawable = getDrawable();
        if (mDrawable == null)
            return; // couldn't resolve the URI

        if (mDrawable instanceof BitmapDrawable) {// Bug 等价于scaleType=centerCrop
            try {
                Bitmap bitmap = ((BitmapDrawable) mDrawable).getBitmap();

                float layoutRatio = getWidth() / 1.0f / getHeight();
                float bitmapRatio = bitmap.getWidth() / 1.0f / bitmap.getHeight();
                float scale;
                if (bitmapRatio > layoutRatio) {
                    scale = getHeight() / 1.0f / bitmap.getHeight();
                    int delta = (int) ((bitmap.getWidth() - bitmap.getHeight()*layoutRatio)/2);
                    canvas.translate(getPaddingLeft()-delta, getPaddingTop());
                } else {
                    scale = getWidth() / 1.0f / bitmap.getWidth();
                    int delta = (int) ((bitmap.getHeight() - bitmap.getWidth()/layoutRatio)/2);
                    canvas.translate(getPaddingLeft(), getPaddingTop()-delta);
                }
                matrix.reset();
                matrix.postScale(scale, scale);//等比缩放
                canvas.drawBitmap(bitmap, matrix, paint);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onDraw(canvas);
    }

        /**
         * 暂时只支持等比缩放高
         *
         * @param scale
         */

    public void setScale(float scale) {
        this.scale = scale;
//        requestLayout();
    }
}
