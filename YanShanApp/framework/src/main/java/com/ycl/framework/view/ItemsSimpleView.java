package com.ycl.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ycl.framework.R;
import com.ycl.framework.base.MsgCountEvent;
import com.ycl.framework.utils.helper.ViewBindHelper;
import com.ycl.framework.utils.string.DensityUtils;
import com.ycl.framework.utils.util.GlideProxy;
import com.ycl.framework.utils.util.SelectorUtil;
import com.ycl.framework.view.roundedview.RoundedImageView;
import com.zhy.autolayout.AutoFrameLayout;

import org.greenrobot.eventbus.EventBus;

/**
 * Simple选项通用item（纯文本 ） on 2015/10/19.
 */
public class ItemsSimpleView extends AutoFrameLayout {

    private TextView tvTitle;
    private ImageView ivLeft;
    private View line; //底部的线 (根据right view进行调整)
    private View rightView;

    public ItemsSimpleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addView(LayoutInflater.from(context).inflate(R.layout.layout_simple_itemview_2, this, false));
        setBackground(SelectorUtil.getDrawable(context, android.R.color.white, R.color.bg_default));

        tvTitle = (TextView) findViewById(R.id.tv_simple_item_view_title);
        ivLeft = (ImageView) findViewById(R.id.iv_item_simple_view_left);
        line = findViewById(R.id.line_simple_item_view_bottom);
        rightView = findViewById(R.id.iv_simple_item_view_guide);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.styleable_titleHeaderView);
        tvTitle.setText(typedArray.getString(R.styleable.styleable_titleHeaderView_title_name));

        rightView.setVisibility(typedArray.getBoolean(R.styleable.styleable_titleHeaderView_title_guide_more_visibility, true) ? VISIBLE : INVISIBLE);

        boolean visibility = typedArray.getBoolean(R.styleable.styleable_titleHeaderView_title_divider_visibility, true);
        if (!visibility) {
            line.setVisibility(GONE);
        }


        Drawable d = typedArray.getDrawable(R.styleable.styleable_titleHeaderView_title_left_img_res);
        if (d != null) {
            ivLeft.setImageDrawable(d);
            ivLeft.setVisibility(VISIBLE);
        }
        typedArray.recycle();
    }

    public void setSimpleItemsTitle(String text) {
        tvTitle.setText(text);
    }

    public void setRightViewVisibility(int visibility) {
        rightView.setVisibility(visibility);
    }


    private ImageView ivImg;

    /**
     * Method 设置Right 图片 (类似  头像)
     *
     * @param path 图片路径
     */
    public void setRightImg(String path) {
        if (ivImg == null) {
            ivImg = new ImageView(getContext());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(DensityUtils.dp2px(75, getContext()), DensityUtils.dp2px(75, getContext()));
            params.setMargins(DensityUtils.dp2px(15, getContext()), DensityUtils.dp2px(15, getContext()), DensityUtils.dp2px(15, getContext()), DensityUtils.dp2px(15, getContext()));
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            ivImg.setLayoutParams(params);
            ivImg.setScaleType(ScaleType.FIT_XY);
            ((RelativeLayout) findViewById(R.id.rl_simple_item_view_root)).addView(ivImg);
            ivImg.setId(R.id.title_view_right_id);
            //底部线
            View line = findViewById(R.id.line_simple_item_view_bottom);
            RelativeLayout.LayoutParams paramLine = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, DensityUtils.dp2px(1, getContext()));
            paramLine.addRule(RelativeLayout.BELOW, R.id.title_view_right_id);
            paramLine.setMargins(DensityUtils.dp2px(15, getContext()), DensityUtils.dp2px(10, getContext()), 0, 0);
            line.setLayoutParams(paramLine);

        }
        GlideProxy.loadImgForUrl(ivImg, path);
    }


    //添加右边 数字提示
    public void addDigitView(String str) {
        if (TextUtils.isEmpty(str)) return;

        TextView tvDigit = (TextView) findViewWithTag("digitView");
        if (tvDigit == null) {
            tvDigit = new TextView(getContext());
            tvDigit.setTextSize(11);
            tvDigit.setGravity(Gravity.CENTER);
            tvDigit.setTextColor(Color.WHITE);
            int padding = DensityUtils.dp2px(1f, getContext());
            tvDigit.setPadding(DensityUtils.dp2px(5f, getContext()), padding, DensityUtils.dp2px(5f, getContext()), padding);
            tvDigit.setBackground(SelectorUtil.getShape(getResources().getColor(R.color.title_view_header_bg_color), DensityUtils.dp2px(25, getContext()), 0, 0));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.RIGHT_OF, R.id.tv_simple_item_view_title);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.setMargins(DensityUtils.dp2px(10, getContext()), 0, 0, 0);
            tvDigit.setTag("digitView");
            tvDigit.setLayoutParams(params);
            ((RelativeLayout) findViewById(R.id.rl_simple_item_view_content_right)).addView(tvDigit);
        }
        tvDigit.setText(str.length() > 3 ? "···" : str);
        tvDigit.setVisibility(VISIBLE);
    }


    //dismiss View(数字提示)
    public void disDigitView() {
        View v = findViewWithTag("digitView");
        if (v != null) {
            if (v.isShown()) {
                v.setVisibility(GONE);
                MsgCountEvent event = new MsgCountEvent();
                event.setTypeEvent(2);
                event.setMsgTotal(Integer.parseInt(((TextView) v).getText().toString()));
                EventBus.getDefault().post(event);
            }
        }
    }


    public void addRightTvWithColor(CharSequence str, int colors) {
        TextView tvHint = (TextView) findViewWithTag("rightTv");
        if (tvHint == null) {
            tvHint = new TextView(getContext());
            tvHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, DensityUtils.getAutoSizePx(28));//使用 auto 布局  往小设置
            tvHint.setTextColor(colors);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.LEFT_OF, R.id.iv_simple_item_view_guide);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.setMargins(0, 0, DensityUtils.dp2px(10, getContext()), 0);
            tvHint.setTag("rightTv");
            tvHint.setLayoutParams(params);
            ((RelativeLayout) findViewById(R.id.rl_simple_item_view_content_right)).addView(tvHint);
        }
        tvHint.setText(str);
    }

    //学理财
    public void setEditContent(String hints, String content) {
        EditText tdText = ViewBindHelper.findViews(this, R.id.ed_simple_item_view_content);
        tdText.setHint(hints);
        tdText.setText(content);
        tdText.setVisibility(VISIBLE);
    }

    public String getEditContent() {
        EditText tdText = ViewBindHelper.findViews(this, R.id.ed_simple_item_view_content);
        return tdText.getText().toString();
    }

    public EditText getEditTextView() {
        return ViewBindHelper.findViews(this, R.id.ed_simple_item_view_content);
    }

    public void setGuideTvContent(String content, int position) {
        TextView tvText = ViewBindHelper.findViews(this, R.id.tv_simple_item_view_guide);

        tvText.setText(content);
        tvText.setTextColor(0xff666666);
        tvText.setTag(position + 1);
    }

    public void setGuideTvContent(String content, String tag) {
        TextView tvText = ViewBindHelper.findViews(this, R.id.tv_simple_item_view_guide);

        tvText.setText(content);
        tvText.setTextColor(0xff666666);
        tvText.setTag(tag);
    }

    public void setGuideTvContent(String content, OnClickListener listener) {
        TextView tvText = ViewBindHelper.findViews(this, R.id.tv_simple_item_view_guide);

        if (TextUtils.isEmpty(content)) {
            tvText.setText("请选择");
            tvText.setTextColor(0xff999999);
        } else {
            tvText.setText(content);
            tvText.setTextColor(0xff666666);
        }
        tvText.setVisibility(VISIBLE);
        setOnClickListener(listener);
    }

    public Object getGuideTvContent() {
        TextView tvText = ViewBindHelper.findViews(this, R.id.tv_simple_item_view_guide);
        return tvText.getTag();
    }

    public String getGuideTvStrContent() {
        TextView tvText = ViewBindHelper.findViews(this, R.id.tv_simple_item_view_guide);
        return tvText.getText().toString();
    }


    public void addRoundView(int ids, String url) {
        RoundedImageView imageView = (RoundedImageView) findViewWithTag("roundView");
        if (imageView == null) {
            imageView = new RoundedImageView(getContext());


            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setBorderWidth(0f);
            imageView.setCornerRadius(DensityUtils.getAutoSizePx(50));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(DensityUtils.getAutoSizePx(100), DensityUtils.getAutoSizePx(100));
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.setMargins(0, 0, DensityUtils.getAutoSizePx(44), 0);
            imageView.setTag("roundView");
            imageView.setLayoutParams(params);
            ((RelativeLayout) findViewById(R.id.rl_simple_item_view_content_right)).addView(imageView);
        }
        imageView.setImageResource(ids);

        final RoundedImageView finalImageView = imageView;
        Glide.with(getContext().getApplicationContext()).load(url).asBitmap().override(DensityUtils.getAutoSizePx(100), DensityUtils.getAutoSizePx(100)).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                finalImageView.setImageBitmap(resource);
            }
        });
    }

    public RoundedImageView getRoundView() {
        return (RoundedImageView) findViewWithTag("roundView");
    }

    public void addLeftImportantImg(CharSequence str) {
        TextView tvHint = (TextView) findViewWithTag("leftImportant");
        if (tvHint == null) {
            tvHint = new TextView(getContext());
            tvHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);//使用 auto 布局  往小设置
            tvHint.setTextColor(ContextCompat.getColor(getContext().getApplicationContext(), R.color.color_gray));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            tvHint.setTag("leftImportant");
            tvHint.setLayoutParams(params);
            ((RelativeLayout) findViewById(R.id.rl_simple_item_view_content_right)).addView(tvHint);
        }
        tvHint.setText(str);

        tvHint.post(new Runnable() {
            @Override
            public void run() {
                TextView tvImportantHint = (TextView) findViewWithTag("leftImportant");
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tvImportantHint.getLayoutParams();
                params.setMargins(((RelativeLayout.LayoutParams) findViewById(R.id.tv_simple_item_view_title).getLayoutParams()).leftMargin - tvImportantHint.getMeasuredWidth() - DensityUtils.dp2px(2, getContext().getApplicationContext()), 0, 0, 0);
                tvImportantHint.setLayoutParams(params);
            }
        });
    }

}
