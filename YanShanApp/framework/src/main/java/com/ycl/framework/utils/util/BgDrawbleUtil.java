package com.ycl.framework.utils.util;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import com.ycl.framework.base.FrameApplication;

import static com.sina.weibo.sdk.utils.ResourceManager.dp2px;

/**
 * Created by dodo on 2018/5/22.
 */

public class BgDrawbleUtil {

    /**
     * 设置圆角的背景
     *
     * @param context 上下文
     * @param v       View
     */
    public static void shapeSolid(Context context, View v, int pos) {
        GradientDrawable gd = (GradientDrawable) v.getBackground();
        int strokeWidth = 1; // 1dp 边框宽度
        int roundRadius = 8; // 8dp 圆角半径
        int strokeColor = 0xffff4984;//边框颜色
        int fillColor = 0xffffffff;//内部填充颜色
        if (pos == 1) {
            strokeColor = 0xff02FF13;//边框颜色
            fillColor = 0xff02FF13;//内部填充颜色
        }
        gd.setColor(fillColor);
        gd.setCornerRadius(dp2px(context, roundRadius));
        gd.setStroke(dp2px(context, strokeWidth), strokeColor);
    }



    public static void shapeCircleCorner(View v,int dp) {
        GradientDrawable gd = (GradientDrawable) v.getBackground();
        if(gd == null){
            gd = new GradientDrawable();
        }
        int roundRadius = dp; // 8dp 圆角半径
        int fillColor = 0xffffffff;//内部填充颜色
        gd.setColor(fillColor);
        gd.setCornerRadius(dp2px(FrameApplication.getFrameContext(), roundRadius));
        v.setBackground(gd);
    }




}
