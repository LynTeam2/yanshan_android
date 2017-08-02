package com.ycl.framework.view.recycleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import com.ycl.framework.R;
import com.ycl.framework.view.ptrview.FixRequestDisallowTouchEventPtrFrameLayout;
import com.ycl.framework.view.recycleview.adapter.MultiRecycleTypesAdapter;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * PtrRefresh的 RecyclerView on 2015/11/6.
 */
public class PtrFixRefreshRecyclerView extends FrameLayout {

    private OnLoadMoreListener onLoadMoreListener;
    public ScrollRecyclerView mRecyclerView;
    private MultiRecycleTypesAdapter mAdapter;

    public FixRequestDisallowTouchEventPtrFrameLayout mPtrFrameLayout;

    protected int mPadding;
    protected int mPaddingTop;
    protected int mPaddingBottom;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected boolean mClipToPadding;

    private int mVisibleItemCount = 0;
    private int mTotalItemCount = 0;
    private int mFirstVisibleItem;
    private int lastVisibleItemPosition;

    private boolean isLoadingMore = false; //加载是否进行中  。。屏蔽网络慢 多次加载

    private LinearLayoutManager linearLayoutManager;

    protected RecyclerView.OnScrollListener mOnScrollListener;

    private FrameRefreshListener refreshListener;

    public PtrFixRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public PtrFixRefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrFixRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initViews();
        initCustomSwipeToRefresh();
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
        } finally {
            typedArray.recycle();
        }
    }

    protected void initViews() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ptr_fix_refresh_recycler_view_layout, this);
        mRecyclerView = (ScrollRecyclerView) view.findViewById(R.id.ptr_frame_refresh_recycler);

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

    private void initCustomSwipeToRefresh() {
        mPtrFrameLayout = (FixRequestDisallowTouchEventPtrFrameLayout) findViewById(R.id.ptr_frame_refresh_layout);
        mPtrFrameLayout.setResistance(1.5f);
        mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        PtrClassisCustomHeader header = new PtrClassisCustomHeader(getContext());
        header.setLastUpdateTimeKey("lastTime");
//        mPtrFrameLayout.setDurationToClose(200);
        mPtrFrameLayout.setDurationToCloseHeader(1000);
        mPtrFrameLayout.setPullToRefresh(false);//释放刷新
        mPtrFrameLayout.setKeepHeaderWhenRefresh(true);

        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.setHeaderView(header);
        //刷新Method()
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    public void run() {
                        if (refreshListener != null)
                            refreshListener.refresh();
                    }
                }, 100);
            }
        });
    }

    public void setRefreshCallBackListener(FrameRefreshListener listener) {
        this.refreshListener = listener;
    }

    /**
     * Set the layout manager to the recycler
     */
    public LinearLayoutManager getLayoutManager() {
        return linearLayoutManager;
    }

    private RecyclerView.AdapterDataObserver dataObserver;

    public void setAdapter(MultiRecycleTypesAdapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
        if (mAdapter != null) {

            dataObserver = new RecyclerView.AdapterDataObserver() {
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
            };
            mAdapter.registerAdapterDataObserver(dataObserver);
        }
    }


    //设置滚动监听
    public void setRecyclerScrollListener(RecyclerView.OnScrollListener listener) {
        this.mOnScrollListener = listener;
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    /**
     * Enable loading more of the recyclerview
     */
    public void enableLoadmore() {
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                commonScrollMore();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)//停止滚动)
                {
                    mPtrFrameLayout.setDisallowInterceptTouchEvent(false);
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
     * Enable loading more of the recyclerview
     */
    public void setEnableLoader(RecyclerView.OnScrollListener listener) {
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        mOnScrollListener = listener;
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        //加载脚
        if (mAdapter != null) {
            mAdapter.setCustomLoadMoreView(LayoutInflater.from(getContext())
                    .inflate(R.layout.item_recycler_view_progressbar, null));
            mAdapter.setmIsLoadMoreWidgetEnabled(false);
        }
    }

    /**
     * Enable loading more of the recyclerviewdis(dismiss)   支持自定义onScrollListener；；
     */
    public void setdisEnableLoader(RecyclerView.OnScrollListener listener) {
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        mOnScrollListener = listener;
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        //加载脚
        if (mAdapter != null) {
            mAdapter.setCustomLoadMoreView(LayoutInflater.from(getContext())
                    .inflate(R.layout.empty_progressbar, null));
            mAdapter.setmIsLoadMoreWidgetEnabled(true);
            //及时刷新view
            mAdapter.notifyItemChanged(mAdapter.getItemCount() - 1);
        }
    }

    //
    public void commonScrollMore() {
        if (!mAdapter.mIsLoadMoreWidgetEnabled) {
            mVisibleItemCount = linearLayoutManager.getChildCount();
            mTotalItemCount = linearLayoutManager.getItemCount();
            lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            mFirstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
            //      YisLoger.debug("lastVisibleItemPosition          " + lastVisibleItemPosition + "         mTotalItemCount      " + mTotalItemCount);
//            if (mFirstVisibleItem == 0) {
//                View firstVisibleItem = linearLayoutManager.findViewByPosition(mFirstVisibleItem);
//                Rect rc = new Rect();
//                firstVisibleItem.getLocalVisibleRect(rc);
//                if (rc.bottom - rc.top == firstVisibleItem.getMeasuredHeight()) ;
//                {
//                    mPtrFrameLayout.setDisallowInterceptTouchEvent(false);
//                }
//            }
            if (lastVisibleItemPosition == mTotalItemCount - 1) {
                View lastVisibleItem = linearLayoutManager.findViewByPosition(lastVisibleItemPosition);
                Rect rc = new Rect();
                lastVisibleItem.getLocalVisibleRect(rc);
                if (rc.bottom - rc.top >= lastVisibleItem.getMeasuredHeight() / 2)
                    if (!isLoadingMore) {
                        isLoadingMore = true;
                        onLoadMoreListener.loadMore(mRecyclerView.getAdapter().getItemCount(), lastVisibleItemPosition);
                    }
            }
        }
    }

    /**
     * Remove loading more scroll listener
     */
    public void disableLoadmore() {
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        if (mAdapter != null) {
            mAdapter.setCustomLoadMoreView(LayoutInflater.from(getContext())
                    .inflate(R.layout.empty_progressbar, null));
            mAdapter.setmIsLoadMoreWidgetEnabled(true);
            mAdapter.notifyItemChanged(mAdapter.getItemCount() - 1);
        }
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)//停止滚动)
                {
                    mPtrFrameLayout.setDisallowInterceptTouchEvent(false);
                }

            }
        });
    }

    //视图刷新
    private void updateHelperDisplays() {
//        if (mAdapter == null)
//            return;
        isLoadingMore = false;
//        if (mAdapter.getCustomLoadMoreView() == null) return;
//        if (mAdapter.getAdapterItemCount() >= showLoadMoreItemNum) {
//            mAdapter.getCustomLoadMoreView().setVisibility(View.VISIBLE);
//        } else {
//            mAdapter.getCustomLoadMoreView().setVisibility(View.GONE);
//        }
    }

    //自动刷新
    public void autoRefreshViews() {
        mPtrFrameLayout.post(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh();
            }
        });
    }

    public interface OnLoadMoreListener {
        void loadMore(int itemsCount, final int maxLastVisiblePosition);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mAdapter != null && dataObserver != null)
            mAdapter.unregisterAdapterDataObserver(dataObserver);
        refreshListener = null;
        onLoadMoreListener = null;
        mOnScrollListener = null;
        super.onDetachedFromWindow();
    }
}
