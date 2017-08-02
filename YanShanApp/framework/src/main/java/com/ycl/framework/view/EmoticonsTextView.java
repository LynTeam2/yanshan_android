package com.ycl.framework.view;

import android.content.Context;
import android.graphics.Matrix;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.ycl.framework.base.FrameApplication;
import com.ycl.framework.utils.emotion.EmojiParser;
import com.ycl.framework.utils.emotion.ParseEmojiMsgUtil;

/**
 * Emote 表情
 */
public class EmoticonsTextView extends FrameTextView {
    private static final String REGEX_STR = "\\[[\u4E00-\u9FFF]+\\]"; // 匹配聊天表情
    // [嘻嘻]
    private Matrix matrix;                              // 输入法表情尺寸
    private FrameApplication mApplication;
    private boolean colorChange = true;                         //是否需要颜色的改变

    public EmoticonsTextView(Context context) {
        this(context, null, 0);
    }

    public EmoticonsTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mApplication = (FrameApplication) getContext().getApplicationContext();
        matrix = new Matrix();
        matrix.postScale(0.45f, 0.45f);
    }

    public EmoticonsTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!TextUtils.isEmpty(text)) {
            String unicode = EmojiParser.getInstance(getContext()).parseEmoji(text.toString());
            SpannableString spannableString = ParseEmojiMsgUtil.getExpressionString(getContext(),
                    unicode);
            if (colorChange)
                super.setText(replace(text), type);
            else
                super.setText(replace(spannableString), type);
        } else {
            super.setText(text, type);
        }
    }

    public boolean isColorChange() {
        return colorChange;
    }

    //是否过滤字符(Emoji)
    public void setColorChange(boolean colorChange) {
        this.colorChange = colorChange;
    }

    /**
     * @Method 替换String 为 聊天表情 2014-12-6
     */
    private CharSequence replace(CharSequence text) {
//        try {
//            SpannableStringBuilder builder = new SpannableStringBuilder(text);
//            Pattern sinaPatten = Pattern.compile(REGEX_STR);
//            Matcher matcher2 = sinaPatten.matcher(text);
//            while (matcher2.find()) {
//                if (mApplication.getmEmoticonsId().containsKey(matcher2.group())) {
//                    int id = mApplication.getmEmoticonsId().get(matcher2.group());
//                    Bitmap bitmapCache = EmotionLruCache.getInstance().getEmoteFromCache(
//                            matcher2.group());
//                    if (bitmapCache == null) {
//                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
//                        bitmapCache = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
//                                bitmap.getHeight(), matrix, true);
//                        EmotionLruCache.getInstance()
//                                .putEmoteToCache(matcher2.group(), bitmapCache);
//                        bitmap.recycle();
//                    }
//                    ImageSpan span = new ImageSpan(getContext(), bitmapCache);
//                    builder.setSpan(span, matcher2.start(), matcher2.end(),
//                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                }
//            }
//            return builder;
//        } catch (Exception e) {
//            return text;
//        }
        return text;
    }
}
