package com.ycl.framework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.ycl.framework.utils.string.DensityUtils;

/**
 * Page状态点 .
 * 这个是焦点图下面的小圆点工具，使用方法为：pageView.setCurrentPage(position);
 */
public class PageIndicatorView extends View {
    private int mCurrentPage = -1;
    private int mTotalPage = 0;

    private int icon_size;

    public PageIndicatorView(Context context) {
        this(context, null);
    }

    public PageIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        icon_size = DensityUtils.dp2px(10, context);
    }

    public void setTotalPage(int nPageNum) {
        mTotalPage = nPageNum;
        if (mCurrentPage >= mTotalPage)
            mCurrentPage = mTotalPage - 1;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int nPageIndex) {
        if (nPageIndex < 0 || nPageIndex >= mTotalPage)
            return;

        if (mCurrentPage != nPageIndex) {
            mCurrentPage = nPageIndex;
            this.invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xAAFFFFFF);

        Rect r = new Rect();
        this.getDrawingRect(r);

        int space = DensityUtils.dp2px(12, getContext());

        int x = (r.width() - (icon_size * mTotalPage + space * (mTotalPage - 1))) / 2;
        int y = (r.height() - icon_size) / 2;

        for (int i = 0; i < mTotalPage; i++) {
            if (i == mCurrentPage) {
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(x, y, icon_size / 2, paint);
            } else {
                paint.setStyle(Paint.Style.STROKE); //绘制空心圆
                int stokeWith = DensityUtils.dp2px(2, getContext());
                paint.setStrokeWidth(stokeWith);
                canvas.drawCircle(x, y, icon_size / 2 - stokeWith / 2, paint);

            }
            x += icon_size + space;
        }
    }
}
