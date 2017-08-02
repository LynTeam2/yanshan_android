package com.ycl.framework.view.recycleview.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.ycl.framework.view.recycleview.FrameViewHolder;
import com.ycl.framework.view.recycleview.adapter.MultiRecycleTypesAdapter;

/**
 * recycle adapter（Staggered  瀑布流） on 2015/11/5.
 * 加载脚 占满屏幕  效果实现
 */
public abstract class StaggeredMultiRecycleTypesAdapter<T> extends MultiRecycleTypesAdapter<T> {


    public boolean isHeader(int position) {
        return isHasHeader() && position == 0;
    }

    public boolean isFooter(int position) {
        return isHasFooter() && position == datas.size();
    }

    @Override
    public void onViewAttachedToWindow(FrameViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isStaggeredGridLayout(holder)) {
            handleLayoutIfStaggeredGridLayout(holder, holder.getLayoutPosition());
        }
    }

    private boolean isStaggeredGridLayout(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            return true;
        }
        return false;
    }

    protected void handleLayoutIfStaggeredGridLayout(RecyclerView.ViewHolder holder, int position) {
        if (isHeader(position) || isFooter(position)) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            p.setFullSpan(true);
        }
    }
}