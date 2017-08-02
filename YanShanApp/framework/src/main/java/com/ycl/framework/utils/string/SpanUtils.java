package com.ycl.framework.utils.string;

import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Span工具类  on 2015/12/7.
 */
@Deprecated
public class SpanUtils {

    public static void addSpanForDynamic(final TextView textView, final int startColor, final int endColor, int colors, View.OnClickListener listener, View.OnClickListener listener2) {
        String mString = textView.getText().toString();
        SpannableString sp = new SpannableString(mString);
        sp.setSpan(new IntentSpan(listener), startColor, endColor,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(new IntentSpan(listener2), endColor, mString.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        sp.setSpan(
                new ForegroundColorSpan(colors), startColor, endColor, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(
                new ForegroundColorSpan(0xff929292), endColor, mString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(sp);

        textView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                CharSequence text = ((TextView) v).getText();
                Spannable stext = Spannable.Factory.getInstance().newSpannable(text);
                Layout layout = ((TextView) v).getLayout();
                int x = (int) event.getX();
                int y = (int) event.getY();
                int line = layout.getLineForVertical(y);
                int offset = layout.getOffsetForHorizontal(line, x);
                ClickableSpan[] link = stext.getSpans(offset, offset, ClickableSpan.class);
                if (link.length != 0) {
                    if (MotionEvent.ACTION_UP == event.getAction()) {
                        link[0].onClick(v);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public static void addSpanForComment(final TextView textView, final String mString, final int index1, final int index2, View.OnClickListener listener, View.OnClickListener listener2) {
        SpannableString sp = new SpannableString(mString);
        sp.setSpan(new IntentSpan(listener), 0, index1,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        if (listener2 != null) {
            sp.setSpan(new IntentSpan(listener2), index1, mString.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        sp.setSpan(
                new ForegroundColorSpan(0xff5d95d4), 0, index1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(
                new ForegroundColorSpan(0xff333333), index1, index2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        sp.setSpan(
                new ForegroundColorSpan(0xFFAAAAAA), index2, mString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(DensityUtils.sp2px(textView.getContext(), 13), false), 0, index2,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(DensityUtils.sp2px(textView.getContext(), 11), false), index2, mString.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(sp);

        textView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                CharSequence text = ((TextView) v).getText();
                Spannable stext = Spannable.Factory.getInstance().newSpannable(text);
                Layout layout = ((TextView) v).getLayout();
                int x = (int) event.getX();
                int y = (int) event.getY();
                int line = layout.getLineForVertical(y);
                int offset = layout.getOffsetForHorizontal(line, x);
                ClickableSpan[] link = stext.getSpans(offset, offset, ClickableSpan.class);
                if (link.length != 0) {
                    if (MotionEvent.ACTION_UP == event.getAction()) {
                        link[0].onClick(v);
                    }
                    return true;
                }
                return false;
            }
        });

    }





}
