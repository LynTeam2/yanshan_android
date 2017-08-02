package com.ycl.framework.view.recycleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycl.framework.utils.util.ViewHolderUtil;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * frame ViewHolder on 2015/11/12.
 */
public class FrameViewHolder extends RecyclerView.ViewHolder {

    public FrameViewHolder(View itemView, int type) {
        super(itemView);
        AutoUtils.autoSize(itemView); //自适应
    }

    public <T extends View> T getView(@IdRes int viewId) {
        return ViewHolderUtil.get(itemView, viewId);
    }

    public ImageView getImageView(@IdRes int viewId) {
        return (ImageView) this.getView(viewId);
    }

    public TextView getTextView(@IdRes int viewId) {
        return (TextView) this.getView(viewId);
    }

    public View getConvertView() {
        return this.itemView;
    }

    public Context getViewContext() {
        return this.itemView.getContext();
    }


    public FrameViewHolder setText(@IdRes int viewId, CharSequence text) {
        TextView view = this.getView(viewId);
        view.setText(text);
        return this;
    }

    public FrameViewHolder setText(@IdRes int viewId, @StringRes int stringResId) {
        TextView view = this.getView(viewId);
        view.setText(stringResId);
        return this;
    }

    public FrameViewHolder setHtml(@IdRes int viewId, String source) {
        TextView view = this.getView(viewId);
        view.setText(Html.fromHtml(source));
        return this;
    }

    public FrameViewHolder setChecked(@IdRes int viewId, boolean checked) {
        Checkable view = this.getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public FrameViewHolder setTag(@IdRes int viewId, Object tag) {
        View view = this.getView(viewId);
        view.setTag(tag);
        return this;
    }

    public FrameViewHolder setTag(@IdRes int viewId, int key, Object tag) {
        View view = this.getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public FrameViewHolder setVisibility(@IdRes int viewId, int visibility) {
        View view = this.getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public FrameViewHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = this.getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public FrameViewHolder setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView view = this.getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public FrameViewHolder setTextColorRes(@IdRes int viewId, @ColorRes int textColorResId) {
        TextView view = this.getView(viewId);
        view.setTextColor(ContextCompat.getColor(getMContxt(), textColorResId));
        return this;
    }

    public FrameViewHolder setTextColor(@IdRes int viewId, int textColor) {
        TextView view = this.getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public FrameViewHolder setBackgroundRes(@IdRes int viewId, int backgroundResId) {
        View view = this.getView(viewId);
        view.setBackgroundResource(backgroundResId);
        return this;
    }

    public FrameViewHolder setBackgroundColor(@IdRes int viewId, int color) {
        View view = this.getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public FrameViewHolder setBackgroundColorRes(@IdRes int viewId, @ColorRes int colorResId) {
        View view = this.getView(viewId);
        view.setBackgroundColor(ContextCompat.getColor(getMContxt(), colorResId));
        return this;
    }

    public FrameViewHolder setBackgroundDrawable(@IdRes int viewId, Drawable drawable) {
        View view = this.getView(viewId);
        view.setBackground(drawable);
        return this;
    }

    public FrameViewHolder setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ImageView view = this.getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    protected Context getMContxt() {
        return this.itemView.getContext().getApplicationContext();
    }

}
