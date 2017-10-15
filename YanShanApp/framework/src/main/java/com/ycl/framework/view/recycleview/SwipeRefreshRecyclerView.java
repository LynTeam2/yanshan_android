package com.ycl.framework.view.recycleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.ycl.framework.R;
import com.ycl.framework.view.recycleview.adapter.MultiRecycleTypesAdapter;

/**
 * SwipeRefresh的 RecyclerView on 2015/11/6.
 */
public class SwipeRefreshRecyclerView extends FrameLayout {

    private PtrRefreshRecyclerView.OnLoadMoreListener onLoadMoreListener;
    public ScrollRecyclerView mRecyclerView;
    private MultiRecycleTypesAdapter mAdapter;

    protected int mPadding;
    protected int mPaddingTop;
    protected int mPaddingBottom;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected boolean mClipToPadding;
    protected int[] defaultSwipeToDismissColors = null; //加载更多颜色

    public VerticalSwipeRefreshLayout mSwipeRefreshLayout;

    private int mVisibleItemCount = 0;
    private int mTotalItemCount = 0;
    private int mFirstVisibleItem;
    private int lastVisibleItemPosition;

    private boolean isLoadingMore = false; //加载是否进行中  。。屏蔽网络慢 多次加载

    private LinearLayoutManager linearLayoutManager;

    protected RecyclerView.OnScrollListener mOnScrollListener;

    public SwipeRefreshRecyclerView(Context context) {
        super(context);
        initViews();
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initViews();
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initViews();
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.UltimateRecyclerview);
        try {
            mPadding = (int) typedArray.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPadding, -1.1f);
            mPaddingTop = (int) typedArray.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPaddingTop, 0.0f);
            mPaddingBottom = (int) typedArray.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPaddingBottom, 0.0f);
            mPaddingLeft = (int) typedArray.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPaddingLeft, 0.0f);
            mPaddingRight = (int) typedArray.getDimension(R.styleable.UltimateRecyclerview_recyclerviewPaddingRight, 0.0f);
            mClipToPadding = typedArray.getBoolean(R.styleable.UltimateRecyclerview_recyclerviewClipToPadding, false);
            int colorList = typedArray.getResourceId(R.styleable.UltimateRecyclerview_recyclerviewDefaultSwipeColor, 0);
            if (colorList != 0) {
                defaultSwipeToDismissColors = getResources().getIntArray(colorList);
            }
        } finally {
            typedArray.recycle();
        }
    }

    protected void initViews() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.swipe_refresh_recycler_view_layout, this);
        mRecyclerView = (ScrollRecyclerView) view.findViewById(R.id.swipe_refresh_recycler);
        mSwipeRefreshLayout = (VerticalSwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setEnabled(false);

        if (mRecyclerView != null) {
            mRecyclerView.setClipToPadding(mClipToPadding);
            if (mPadding != -1.1f) {
                mRecyclerView.setPadding(mPadding, mPadding, mPadding, mPadding);
            } else {
                mRecyclerView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
            }
        }

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * Set the layout manager to the recycler
     */
    public LinearLayoutManager getLayoutManager() {
        return linearLayoutManager;
    }


    public void setAdapter(MultiRecycleTypesAdapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
        setRefreshing(false);
        if (mAdapter != null)
            mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    super.onItemRangeChanged(positionStart, itemCount);
                    updateHelperDisplays();
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    updateHelperDisplays();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    updateHelperDisplays();
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                    updateHelperDisplays();
                }

                @Override
                public void onChanged() {
                    super.onChanged();
                    updateHelperDisplays();
                }
            });
    }


    //设置滚动监听
    public void setRecyclerScrollListener(cn.gov.bjys.onlinetrain.fragment.HomeFragment listener) {
        this.mOnScrollListener = listener;
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    public void setOnLoadMoreListener(PtrRefreshRecyclerView.OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    //mOnScrollListener 还原成ScrollListener(头部栏滑动的)  这边 默认没有其他需求  ,只是清除外部的设置
    protected void setDefaultScrollListener() {
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
    }

    /**
     * Enable loading more of the recyclerview
     */
    public void enableLoadmore() {
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        mOnScrollListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mAdapter.mIsLoadMoreWidgetEnabled) {
                    mVisibleItemCount = linearLayoutManager.getChildCount();
                    mTotalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    mFirstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                    //      YisLoger.debug("lastVisibleItemPosition          " + lastVisibleItemPosition + "         mTotalItemCount      " + mTotalItemCount);
                    if (lastVisibleItemPosition == mTotalItemCount - 1) {
                        if (!isLoadingMore) {
                            isLoadingMore = true;
                            onLoadMoreListener.loadMore(mRecyclerView.getAdapter().getItemCount(), lastVisibleItemPosition);
                        }
                    }
                }
            }

        };
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        //加载脚
        if (mAdapter != null) {
            mAdapter.setCustomLoadMoreView(LayoutInflater.from(getContext())
                    .inflate(R.layout.item_recycler_view_progressbar, null));
            mAdapter.setmIsLoadMoreWidgetEnabled(false);
        }
    }

    /**
     * Remove loading more scroll listener
     */
    public void disableLoadmore() {
        setDefaultScrollListener();
        if (mAdapter != null) {
            mAdapter.setCustomLoadMoreView(LayoutInflater.from(getContext())
                    .inflate(R.layout.empty_progressbar, null));
            mAdapter.setmIsLoadMoreWidgetEnabled(true);
        }
    }

    public void setDefaultOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        mSwipeRefreshLayout.setEnabled(true);
        if (defaultSwipeToDismissColors != null && defaultSwipeToDismissColors.length > 0) {
            mSwipeRefreshLayout.setColorSchemeColors(defaultSwipeToDismissColors);
        } else {
            mSwipeRefreshLayout.setColorSchemeResources(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);

        }
        mSwipeRefreshLayout.setOnRefreshListener(listener);
    }

    public void setRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
    }

    //视图刷新
    private void updateHelperDisplays() {
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(false);
        if (mAdapter == null)
            return;
        isLoadingMore = false;
    }

}
