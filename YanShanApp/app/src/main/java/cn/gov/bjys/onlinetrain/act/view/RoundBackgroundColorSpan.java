package cn.gov.bjys.onlinetrain.act.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

public class RoundBackgroundColorSpan extends ReplacementSpan {
    private int bgColor;
    private int textColor;
    public RoundBackgroundColorSpan(int bgColor, int textColor) {
        super();
        this.bgColor = bgColor;
        this.textColor = textColor;
    }
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return ((int)paint.measureText(text, start, end));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        int color1 = paint.getColor();
        paint.setColor(this.bgColor);
        Rect rect = new Rect();
        paint.getTextBounds( text.toString(),0,text.length(),rect);
        int h = rect.height();
        if(Math.abs(bottom -top) > h) {
            canvas.drawRoundRect(new RectF(x, top + 1, x + ((int) paint.measureText(text, start, end))+20, top + h +5), 10, 10, paint);
        }else{
            canvas.drawRoundRect(new RectF(x, top + 1, x + ((int) paint.measureText(text, start, end))+20, bottom - 1), 10, 10, paint);
        }
        paint.setColor(this.textColor);
        canvas.drawText(text, start, end, x+12, y, paint);
        paint.setColor(color1);
    }
}