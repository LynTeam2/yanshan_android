package com.ycl.framework.view;

//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.text.style.ImageSpan;
//import android.text.Spannable;
//import android.text.SpannableStringBuilder;
//import android.graphics.Matrix;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

public class EmoticonsEditText extends EditText {
    //private Matrix                 matrix;
    //    private static final String    REGEX_STR = "\\[[\u4E00-\u9FFF]+\\]";
    //    private SpannableStringBuilder builder;
    //    private EmotionLruCache        emotionCache;
    //    private BaseApplication        mApplication;

    public EmoticonsEditText(Context context) {
        super(context);
        init(context);
    }

    public EmoticonsEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public EmoticonsEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        //        mApplication = (BaseApplication) context.getApplicationContext();
        //        emotionCache = EmotionLruCache.getInstance();
        //        matrix = new Matrix();
        //        matrix.postScale(Const.MATEIX_SCALE, Const.MATEIX_SCALE);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!TextUtils.isEmpty(text)) {
            super.setText(text, type);
        } else {
            super.setText(text, type);
        }
    }

    //   @SuppressWarnings("unused")
    //    private CharSequence replace(CharSequence text) {
    //        try {
    //            builder = new SpannableStringBuilder(text);
    //            final Pattern sinaPatten = Pattern.compile(REGEX_STR);
    //            final Matcher matcher2 = sinaPatten.matcher(text);
    //            while (matcher2.find()) {
    //                if (mApplication.getmEmoticonsId().containsKey(matcher2.group())) {
    //                    int id = mApplication.getmEmoticonsId().get(matcher2.group());
    //                    Bitmap bitmapCache = emotionCache.getEmoteFromCache(matcher2.group());
    //                    if (bitmapCache == null) {
    //                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
    //                        bitmapCache = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
    //                            bitmap.getHeight(), matrix, true);
    //                        emotionCache.putEmoteToCache(matcher2.group(), bitmapCache);
    //                        bitmap.recycle();
    //                    }
    //                    ImageSpan span = new ImageSpan(getContext(), bitmapCache);
    //                    builder.setSpan(span, matcher2.start(), matcher2.end(),
    //                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    //                }
    //            }
    //            return builder;
    //        } catch (Exception e) {
    //            return text;
    //        }
    //    }
}
