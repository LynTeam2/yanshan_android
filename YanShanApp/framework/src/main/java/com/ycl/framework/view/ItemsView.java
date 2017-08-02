package com.ycl.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ycl.framework.R;

/**
 * 选项通用item on 2015/10/19.
 */
public class ItemsView extends FrameLayout {

    private ImageView MLeftImageView;
    private TextView MLeftText;

    public ItemsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addView(LayoutInflater.from(context).inflate(R.layout.layout_itemview, this, false));
        MLeftImageView = (ImageView) findViewById(R.id.MLeftImageView);
        MLeftText = (TextView) findViewById(R.id.MLeftText);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.styleable_itemViews);
        if (typedArray.getString(R.styleable.styleable_itemViews_item_left_string) != null)
            MLeftText.setText(typedArray.getString(R.styleable.styleable_itemViews_item_left_string));
        MLeftImageView.setImageResource(typedArray.getResourceId(R.styleable.styleable_itemViews_item_left_img, R.drawable.photo_camera));
        typedArray.recycle();
    }

    public void setItemsView(int img_R, int text_R) {
        MLeftImageView.setImageResource(img_R);
        MLeftText.setText(getContext().getString(text_R));
    }

    public void setItemsView(int text_R) {
        MLeftImageView.setVisibility(GONE);
        MLeftText.setText(getContext().getString(text_R));
    }

    public void setItemsView(String text) {
        MLeftText.setText(text);
    }

    public void setItemsTextWithMagin(int text_R, int margins) {
        MLeftImageView.setVisibility(GONE);
        MLeftText.setText(getContext().getString(text_R));
        ((LinearLayout.LayoutParams) MLeftText.getLayoutParams()).setMargins(margins, (int) getResources().getDimension(R.dimen.default_margin_top), 0, (int) getResources().getDimension(R.dimen.default_margin_top));
    }
}
