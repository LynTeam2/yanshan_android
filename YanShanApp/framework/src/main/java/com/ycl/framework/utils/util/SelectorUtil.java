package com.ycl.framework.utils.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.ycl.framework.base.FrameApplication;

public class SelectorUtil {

    public static StateListDrawable getDrawable(Context ctx, int defaultid, int pressid) {
        StateListDrawable drawable = new StateListDrawable();
        //Non focused states
        drawable.addState(new int[]{-android.R.attr.state_focused,
                -android.R.attr.state_selected, -android.R.attr.state_pressed}, ContextCompat.getDrawable(ctx, defaultid));
        //Pressed
        drawable.addState(new int[]{android.R.attr.state_pressed}, ContextCompat.getDrawable(ctx, pressid));
        //selected
        drawable.addState(new int[]{android.R.attr.state_selected}, ContextCompat.getDrawable(ctx, pressid));
        return drawable;
    }

    public static StateListDrawable getDrawableChecked(Context ctx, int defaultid, int pressid) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{-android.R.attr.state_selected, -android.R.attr.state_pressed}, ContextCompat.getDrawable(ctx, defaultid));
        drawable.addState(new int[]{android.R.attr.state_selected,}, ContextCompat.getDrawable(ctx, pressid));
        //checked
        drawable.addState(new int[]{android.R.attr.state_checked}, ContextCompat.getDrawable(ctx, pressid));
        return drawable;
    }

    public static StateListDrawable getDrawableWithSelected(Context ctx, int defaultid, int pressid) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{-android.R.attr.state_selected, -android.R.attr.state_pressed}, ContextCompat.getDrawable(ctx, defaultid));
        //selected
        drawable.addState(new int[]{android.R.attr.state_selected}, ContextCompat.getDrawable(ctx, pressid));
        return drawable;
    }

    public static StateListDrawable getDrawableWithSelectedCommon(int defaultid, int pressid) {
        return getDrawableWithSelected(FrameApplication.getFrameContext(), defaultid, pressid);
    }

    public static StateListDrawable getStateDrawable(GradientDrawable defaultDrawable, GradientDrawable pressDrawable) {
        StateListDrawable drawable = new StateListDrawable();
        //Non focused states
        drawable.addState(new int[]{-android.R.attr.state_focused,
                -android.R.attr.state_selected, -android.R.attr.state_pressed}, defaultDrawable);
        //Pressed
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressDrawable);
        //selected
        drawable.addState(new int[]{android.R.attr.state_selected}, pressDrawable);
        //check
        return drawable;
    }


    public static ColorStateList getColorStateList(Context ctx, int defaultid, int pressid) {

        int[] colors = new int[]{ContextCompat.getColor(ctx, pressid), ContextCompat.getColor(ctx, defaultid)};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{};
        //        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
        //        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        //        states[2] = new int[] { android.R.attr.state_enabled };
        return new ColorStateList(states, colors);
    }

    public static ColorStateList getColorListState(Context ctx, int defaultid, int pressid) {

        int[] colors = new int[]{ContextCompat.getColor(ctx, pressid), ContextCompat.getColor(ctx, pressid),
                ContextCompat.getColor(ctx, defaultid)};
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_enabled};
        states[2] = new int[]{};
        //        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
        //        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        //        states[2] = new int[] { android.R.attr.state_enabled };
        return new ColorStateList(states, colors);
    }

    //部分api > 21
    public static ColorStateList getColorStateBtn(Context ctx, int defaultid, int pressid) {

        int[] colors = new int[]{ContextCompat.getColor(ctx, pressid),ContextCompat.getColor(ctx, pressid),ContextCompat.getColor(ctx, pressid),ContextCompat.getColor(ctx, defaultid)};
        int[][] states = new int[4][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_selected};
        states[3] = new int[]{};
        return new ColorStateList(states, colors);
    }

    public static ColorStateList getColorStateListSelected(int defaultid, int selectid) {

        int[] colors = new int[]{selectid, selectid,
                defaultid};
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{android.R.attr.state_pressed};
        states[2] = new int[]{};
        //        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
        //        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        //        states[2] = new int[] { android.R.attr.state_enabled };
        return new ColorStateList(states, colors);
    }


    public static GradientDrawable getShape(int fillColor, int roundRadius, int strokeWidth,
                                            int strokeColor) {

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }

    public static GradientDrawable getShapeRadii(int fillColor, float[] radii, int strokeWidth,
                                                 int strokeColor) {

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(fillColor);
        gd.setCornerRadii(radii);
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }


    public static StateListDrawable getDrawableWithDrawa(Context ctx, int pressid, int defaultid) {
        StateListDrawable drawable = new StateListDrawable();
        //Non focused states
        drawable.addState(new int[]{-android.R.attr.state_focused,
                -android.R.attr.state_selected, -android.R.attr.state_pressed}, ContextCompat.getDrawable(ctx, defaultid));
        //clickable
        drawable.addState(new int[]{android.R.attr.clickable}, ContextCompat.getDrawable(ctx, pressid));
        //clickable
        drawable.addState(new int[]{android.R.attr.state_focused}, ContextCompat.getDrawable(ctx, pressid));
        //Pressed
        drawable.addState(new int[]{android.R.attr.state_pressed}, ContextCompat.getDrawable(ctx, pressid));
        //Selected
        drawable.addState(new int[]{android.R.attr.state_selected}, ContextCompat.getDrawable(ctx, pressid));
        return drawable;
    }

    public static StateListDrawable getDrawableWithDrawa(Context ctx, int pressid, int defaultid, int resColor) {
        StateListDrawable drawable = new StateListDrawable();
        //Non focused states
        drawable.addState(new int[]{-android.R.attr.state_focused,
                -android.R.attr.state_selected, -android.R.attr.state_pressed}, ContextCompat.getDrawable(ctx, defaultid));
        //clickable
        drawable.addState(new int[]{android.R.attr.clickable}, tintDrawable(ContextCompat.getDrawable(ctx, pressid),ColorStateList.valueOf(resColor)));
        //clickable
        drawable.addState(new int[]{android.R.attr.state_focused}, tintDrawable(ContextCompat.getDrawable(ctx, pressid),ColorStateList.valueOf(resColor)));
        //Pressed
        drawable.addState(new int[]{android.R.attr.state_pressed}, tintDrawable(ContextCompat.getDrawable(ctx, pressid),ColorStateList.valueOf(resColor)));
        //Selected
        drawable.addState(new int[]{android.R.attr.state_selected}, tintDrawable(ContextCompat.getDrawable(ctx, pressid),ColorStateList.valueOf(resColor)));
        return drawable;
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }



    public static StateListDrawable getDrawableWithColor(Context ctx, int defaultid, int pressid) {
        StateListDrawable drawable = new StateListDrawable();
        //Non focused states
        drawable.addState(new int[]{-android.R.attr.state_focused,
                -android.R.attr.state_selected, -android.R.attr.state_pressed}, new ColorDrawable(defaultid));
        //Pressed
        drawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(pressid));
        return drawable;
    }


}