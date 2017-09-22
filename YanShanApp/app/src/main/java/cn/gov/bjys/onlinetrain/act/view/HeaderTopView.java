package cn.gov.bjys.onlinetrain.act.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ycl.framework.utils.util.SelectorUtil;
import com.zhy.autolayout.AutoFrameLayout;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.utils.DensityUtil;


/**
 * 顶部栏 (通用) on 2015/10/16.
 */
public class HeaderTopView extends AutoFrameLayout {

    private TextView tvTitle;
    private RelativeLayout rlBack;
    private ImageView ivBack;
    private TextView tvBack;

    private RelativeLayout rlRight;
    private ImageView ivRight;
    private TextView tvRight;


    private RelativeLayout mRelativeRootView;

    public HeaderTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addView(LayoutInflater.from(context).inflate(R.layout.view_top_header, this, false));

        mRelativeRootView = (RelativeLayout) findViewById(R.id.ll_top_root);
        tvTitle = (TextView) findViewById(R.id.tv_top_header_titleText);
        rlBack = (RelativeLayout) findViewById(R.id.rl_top_header_back);
        ivBack = (ImageView) findViewById(R.id.iv_top_header_back);
        tvBack = (TextView) findViewById(R.id.tv_top_header_back);
        rlRight = (RelativeLayout) findViewById(R.id.rl_top_header_right);
        ivRight = (ImageView) findViewById(R.id.iv_top_header_right_icon);
        tvRight = (TextView) findViewById(R.id.tv_top_header_right_tv);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.styleable_titleHeaderView);
//        if (typedArray.getString(R.styleable.styleable_titleHeaderView_title_name) != null)
        tvTitle.setText(typedArray.getString(R.styleable.styleable_itemViews_item_left_string));
        typedArray.recycle();
        initViews();
    }

    private void initViews() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            findViewById(R.id.space_top_header).setVisibility(GONE);
//        }
        rlBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rlRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    //隐藏头部Space
//    public void hideTopSpace() {
//        findViewById(R.id.space_top_header).setVisibility(GONE);
//    }

    //设置 tvTitle的文本
    public void setTitleText(int txtId) {
        try {
            tvTitle.setText(getContext().getString(txtId));
        } catch (Exception e) {
            tvTitle.setText("");
        }
    }

    public void setTitleText(CharSequence content) {
        tvTitle.setText(content.toString());
    }
    public void setTitleTextColor(int id){
        tvTitle.setTextColor(getResources().getColor(id));
    }

    //自定义click事件
    public void setCustomClickListener(int rId, OnClickListener listener) {
        findViewById(rId).setOnClickListener(listener);
    }


    public void setLeftText(int ids) {
        tvBack.setText(getContext().getString(ids));
        ivBack.setVisibility(GONE);
    }

    public void setRightText(int ids){
        tvRight.setText(getContext().getString(ids));
        rlRight.setVisibility(VISIBLE);
        ivRight.setVisibility(GONE);
    }
    public void setRightText(String text){
        tvRight.setText(text);
        rlRight.setVisibility(VISIBLE);
        ivRight.setVisibility(GONE);
    }


    private TextView rightAlbum_tv;

    //相册选中多图
    public void setRightBtn_Album(int length, OnClickListener onClickListener, int maxLength) {
        if (rightAlbum_tv == null) {
            rightAlbum_tv = new TextView(getContext());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.setMargins(0, 0, DensityUtil.dip2px(getContext(),15), 0);
            rightAlbum_tv.setLayoutParams(layoutParams);
            int paddingTop = DensityUtil.dip2px(getContext(),2);
            int paddingLeft = DensityUtil.dip2px(getContext(),12);
            rightAlbum_tv.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
            rightAlbum_tv.setBackgroundResource(R.drawable.bg_btn_titleview_album);
            rightAlbum_tv.setTextColor(SelectorUtil.getColorListState(getContext(), R.color.me_data_icon, R.color.money_color));

            rightAlbum_tv.setOnClickListener(onClickListener);
            RelativeLayout title_root = (RelativeLayout) findViewById(R.id.ll_title_root);
            title_root.addView(rightAlbum_tv);
            rightAlbum_tv.setId(R.id.title_view_right_id);
        }

        if (length > 0) {
            rightAlbum_tv.setEnabled(true);
            rightAlbum_tv.setText("完成(" + length + "/" + maxLength + ")");
        } else {
            rightAlbum_tv.setEnabled(false);
            rightAlbum_tv.setText("完成");
        }
    }

    /**
     * right Text按钮
     *
     * @param str 文本 onClickListener 点击回调
     */
    public void setRightBtn_txt(String str, OnClickListener onClickListener) {
        if (rightAlbum_tv == null) {
            rightAlbum_tv = new TextView(getContext());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.setMargins(0, 0, DensityUtil.dip2px(getContext(),8), 0);
            rightAlbum_tv.setLayoutParams(layoutParams);
            int paddingTop = DensityUtil.dip2px(getContext(),2);
            int paddingLeft = DensityUtil.dip2px(getContext(),12);
            rightAlbum_tv.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
            rightAlbum_tv.setTextColor(Color.WHITE);
            rightAlbum_tv.setTextSize(15);

            rightAlbum_tv.setOnClickListener(onClickListener);
            RelativeLayout title_root = (RelativeLayout) findViewById(R.id.ll_title_root);
            title_root.addView(rightAlbum_tv);
            rightAlbum_tv.setId(R.id.title_view_right_id);
        }
        rightAlbum_tv.setText(str);
    }


    /**
     * right Img按钮
     *
     * @param ids 文本 onClickListener 点击回调
     */
    public void addRightImg(int ids, OnClickListener onClickListener) {
        ImageView ivRight = (ImageView) findViewById(R.id.title_view_right_id);
        if (ivRight == null) {
            ivRight = new ImageView(getContext());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.setMargins(0, 0, DensityUtil.dip2px(getContext(),8), 0);
            ivRight.setLayoutParams(layoutParams);
            int paddingTop = DensityUtil.dip2px(getContext(),2);
            int paddingLeft = DensityUtil.dip2px(getContext(),12);
            ivRight.setPadding(DensityUtil.dip2px(getContext(),6), paddingTop, paddingLeft, paddingTop);
            ivRight.setImageResource(ids);

            ivRight.setOnClickListener(onClickListener);
            RelativeLayout title_root = (RelativeLayout) findViewById(R.id.ll_title_root);
            title_root.addView(ivRight);
            ivRight.setId(R.id.title_view_right_id);
        }
    }


    //设置左边imageView的visible
    public void setLeftViewVisible(int visible) {
        rlBack.setVisibility(visible);
    }

    public void setRightViewVisible(int visible) {
        rlRight.setVisibility(visible);
    }

    //设置backGround 的透明度
    public void setBgAlpha(int alpha) {
        findViewById(R.id.ll_title_root_parent).getBackground().setAlpha(alpha);
    }

    public void setLeftViewRes(int ids) {
        ivBack.setImageResource(ids);
    }

    public void setRightViewRes(int ids) {
        ivRight.setImageResource(ids);
    }

    /**
     * right Img按钮
     *
     * @param onClickListener 点击回调
     */
    public void addRightSelectedImg(StateListDrawable drawables, OnClickListener onClickListener) {
        ImageView ivRight = (ImageView) findViewById(R.id.title_view_right_id);
        if (ivRight == null) {
            ivRight = new ImageView(getContext());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.setMargins(0, 0, DensityUtil.dip2px(getContext(),8), 0);
            ivRight.setLayoutParams(layoutParams);
            int paddingTop = DensityUtil.dip2px(getContext(),2);
            int paddingLeft = DensityUtil.dip2px(getContext(),6);
            ivRight.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
            ivRight.setImageDrawable(drawables);

            ivRight.setOnClickListener(onClickListener);
            RelativeLayout title_root = (RelativeLayout) findViewById(R.id.ll_title_root);
            title_root.addView(ivRight);
            ivRight.setId(R.id.title_view_right_id);
        }
    }


    /**
     * right Img按钮
     */
    public void addCustomView(Drawable drawable, OnClickListener onClickListener) {
        ImageView ivRight = (ImageView) findViewById(R.id.title_view_middle_id);
        if (ivRight == null) {
            ivRight = new ImageView(getContext());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.addRule(RelativeLayout.START_OF, R.id.title_view_right_id);
            ivRight.setLayoutParams(layoutParams);
            int paddingTop = DensityUtil.dip2px(getContext(),2);
            int paddingLeft = DensityUtil.dip2px(getContext(),3);
            ivRight.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
            ivRight.setImageDrawable(drawable);

            ivRight.setOnClickListener(onClickListener);
            RelativeLayout title_root = (RelativeLayout) findViewById(R.id.ll_title_root);
            title_root.addView(ivRight);
            ivRight.setId(R.id.title_view_middle_id);
        }
    }

    public void setCustomViewState(boolean state) {
        ImageView ivCustom = (ImageView) findViewById(R.id.title_view_middle_id);
        if (ivCustom != null) {
            ivCustom.setSelected(state);
        }
    }

    public void rootViewAddView(View view){
/*        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(AutoUtils.getPercentWidthSize(63), AutoUtils.getPercentHeightSize(72));
        lp.addRule(RelativeLayout.RIGHT_OF,R.id.rl_top_header_back);
        lp.leftMargin = AutoUtils.getPercentWidthSize(60);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        view.setLayoutParams(lp);
        mRelativeRootView.addView(view);*/
    }
}
