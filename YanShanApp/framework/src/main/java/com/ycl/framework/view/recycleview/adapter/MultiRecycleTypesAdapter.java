package com.ycl.framework.view.recycleview.adapter;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycl.framework.R;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.helper.ContextHelper;
import com.ycl.framework.utils.util.GlideProxy;
import com.ycl.framework.utils.util.ViewHolderUtil;
import com.ycl.framework.view.recycleview.FrameViewHolder;

import java.util.List;

/**
 * recycle adapter（Multi） on 2015/11/5.
 */
public abstract class MultiRecycleTypesAdapter<T> extends AnimationAdapter<FrameViewHolder> {

    private View mHeaderView;
    private View customLoadMoreView;  //加载脚

    protected List<T> datas;

    public boolean mIsLoadMoreWidgetEnabled = false;//能否自动加载（true 不能加载）

    //view的Type类型
    public static class VIEW_TYPES {

        public static final int CUSTOM_FOOTER = -3;
        public static final int EMPTY_FOOTER = -2;
        public static final int TYPE_HEADER = -1;
        public static final int TYPE_FOOTER = 0;
        public static final int TYPE_NORMAL_1 = 1;
        public static final int TYPE_NORMAL_2 = 2;
        public static final int TYPE_NORMAL_3 = 3;
    }

    @Override
    public FrameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPES.TYPE_HEADER:
                return new FrameViewHolder(mHeaderView, viewType);
            case VIEW_TYPES.EMPTY_FOOTER:
            case VIEW_TYPES.TYPE_FOOTER:
            case VIEW_TYPES.CUSTOM_FOOTER: // customLoadMoreView 不会改变 recyclerView的布局复用 (缓存的view)
                return new FrameViewHolder(customLoadMoreView, viewType);
            default:
                return createCustomViewHolder(parent, viewType);
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && customLoadMoreView != null) {
            if (mIsLoadMoreWidgetEnabled) {
                if (customLoadMoreView.getTag() != null)
                    return VIEW_TYPES.CUSTOM_FOOTER;
                return VIEW_TYPES.EMPTY_FOOTER;
            }
            return VIEW_TYPES.TYPE_FOOTER;
        } else if (position == 0 && mHeaderView != null) {
            return VIEW_TYPES.TYPE_HEADER;
        } else {
            if (mHeaderView != null)
                position--;
            return getTypeForPosition(position);
        }
    }


    @Override
    public void onBindViewHolder(FrameViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TYPES.CUSTOM_FOOTER:
            case VIEW_TYPES.EMPTY_FOOTER:
            case VIEW_TYPES.TYPE_FOOTER:
            case VIEW_TYPES.TYPE_HEADER:
                fillSpecialView(holder, position, viewType);
                break;
            default:
                fillData(holder, position, getItemDats(position), viewType);
                break;
        }
    }


    @Override
    public int getItemCount() {

        int itemCount = 0;
        if (datas != null) itemCount = itemCount + datas.size();
        if (mHeaderView != null) itemCount++;
        if (customLoadMoreView != null) itemCount++;
        return itemCount;
    }

    //数据长度
    public int getDataSize() {
        return datas != null ? datas.size() : 0;
    }

    /**
     * 获取 ContentItem类型type  ()
     * <p>
     * Mul 多种类型
     *
     * @param position 考虑到有headerView   postion只使用于 判断ContentView 所需要的类型
     * @return viewType  (int)VIEW_TYPES
     */

    public abstract int getTypeForPosition(int position);

    /**
     * 获取 子类 内容的 ViewHolder
     *
     * @param parent 获取context
     * @return viewType  (int)VIEW_TYPES
     */
    public abstract FrameViewHolder createCustomViewHolder(ViewGroup parent, int viewType); //创建 内容 ViewHolder


    /**
     * 填充适配器 内容
     *
     * @param holder   Holder
     * @param position
     * @param data     T的数据
     * @param viewType
     */

    protected abstract void fillData(FrameViewHolder holder, int position, T data, int viewType);

    //填充适配器  特别的view
    protected void fillSpecialView(FrameViewHolder holder, int position, int viewType) {
    }


    public void setmHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
    }

    public View getmHeaderView() {
        return mHeaderView;
    }

    /**
     * 获取数据  item
     *
     * @param position index标志位  （去除 headerView）
     */
    public T getItemDats(int position) {
        if (datas == null || datas.isEmpty()) {
            return null;
        }
        if (mHeaderView != null)
            return datas.get(position - 1);
        return datas.get(position);
    }


    public View getCustomLoadMoreView() {
        return customLoadMoreView;
    }

    public void setCustomLoadMoreView(View customLoadMoreView) {
        this.customLoadMoreView = customLoadMoreView;
    }

    public boolean ismIsLoadMoreWidgetEnabled() {
        return mIsLoadMoreWidgetEnabled;
    }

    public void setmIsLoadMoreWidgetEnabled(boolean mIsLoadMoreWidgetEnabled) {
        this.mIsLoadMoreWidgetEnabled = mIsLoadMoreWidgetEnabled;
    }

    /**
     * adapter 适配器 数据长度 count
     */
    public int getAdapterItemCount() {
        return datas.size();
    }

    /**
     * Insert a item to the list of the adapter
     *
     * @param object   object T 类型
     * @param position position
     */
    public void insert(T object, int position) {
        datas.add(position, object);
        if (mHeaderView != null) position++;
        notifyItemInserted(position);
    }

    public void insertRange(List<T> extraDatas) {
        int a = 0;
        if (mHeaderView != null) a++;
        a += datas.size();
        datas.addAll(extraDatas);
        notifyItemRangeInserted(a, extraDatas.size());
    }

    //删除
    public void deteleItem(int position) {
        int index = position;
        if (mHeaderView != null) index--;  //去除 headerview
        notifyItemRemoved(position);
        datas.remove(index);
    }


    //set数据
    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public List<T> getDatas() {
        return datas;
    }

    //更新view
    public void updateViews(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }


    //获取Last T；最后一条数据
    public T getLastDatas() {
        if (datas != null && datas.size() > 0)
            return datas.get(datas.size() - 1);
        return null;
    }

    public boolean isEmpty() {
        if (datas == null || datas.isEmpty())
            return true;
        return false;
    }

    //提取  公用Glide加载 Mothed
    protected void commonLoadImag(String url, ImageView iv, int type) {
        if (type == 0) {
            ViewHolderUtil.loadSizeUrlCrop(url, iv);  //裁切
        } else if (type == 1)
            ViewHolderUtil.loadSizeUrlFit(url, iv);
        else
            ViewHolderUtil.loadSizeUrlFitTwo(url, iv);
    }


    public void loadUrlCommon(ImageView iv, String url) {
        GlideProxy.loadImgForUrl(iv, url);
    }

    public void loadUrlForoading(ImageView iv, String url) {
        GlideProxy.loadImgForUrlPlaceHolder(iv, url, R.drawable.bg_loading);
    }


    public boolean isHasHeader() {
        return mHeaderView != null;
    }

    public boolean isHasFooter() {
        return customLoadMoreView != null;
    }

    protected void initViewContent(TextView tv, String content) {
        tv.setText(content);
    }

    //初始化 clickListener
    protected void initViewClicker(View mView, Object tag, View.OnClickListener listener) {
        mView.setTag(tag);
        mView.setOnClickListener(listener);
    }

    //Support Library 改动  代码修复
    protected FrameActivity getRequiredActivity(View req_view) {
        return ContextHelper.getRequiredActivity(req_view.getContext());
    }

}
