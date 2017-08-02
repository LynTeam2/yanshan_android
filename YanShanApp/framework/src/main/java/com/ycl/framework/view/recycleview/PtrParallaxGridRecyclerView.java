package com.ycl.framework.view.recycleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.ycl.framework.R;
import com.ycl.framework.view.recycleview.adapter.MultiRecycleTypesAdapter;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * PtrRefresh的 RecyclerView on 2015/11/6.
 *
 * 监听头部滚动状态(滑动监听)
 *
 */
@Deprecated
public class PtrParallaxGridRecyclerView extends FrameLayout {

    private PtrRefreshRecyclerView.OnLoadMoreListener onLoadMoreListener;
    public ScrollRecyclerView mRecyclerView;
    private MultiRecycleTypesAdapter mAdapter;

    public PtrFrameLayout mPtrFrameLayout;

    protected int mPadding;
    protected int mPaddingTop;
    protected int mPaddingBottom;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected boolean mClipToPadding;
    protected int itemColumns = 2;  //多少列

    private int mVisibleItemCount = 0;
    private int mTotalItemCount = 0;
    private int mFirstVisibleItem;
    private int lastVisibleItemPosition;


    private boolean isLoadingMore = false; //加载是否进行中  。。屏蔽网络慢 多次加载

    private GridLayoutManager manager;

    protected RecyclerView.OnScrollListener mOnScrollListener;

    private FrameRefreshListener refreshListener;

    public PtrParallaxGridRecyclerView(Context context) {
        this(context, null);
    }

    public PtrParallaxGridRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrParallaxGridRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initViews();
        initCustomSwipeToRefresh();
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.GridRecyclerView);
        try {
            mPadding = (int) typedArray.getDimension(R.styleable.GridRecyclerView_gridRecyclerPadding, -1.1f);
            mPaddingTop = (int) typedArray.getDimension(R.styleable.GridRecyclerView_gridRecyclerPaddingTop, 0.0f);
            mPaddingBottom = (int) typedArray.getDimension(R.styleable.GridRecyclerView_gridRecyclerPaddingBottom, 0.0f);
            mPaddingLeft = (int) typedArray.getDimension(R.styleable.GridRecyclerView_gridRecyclerPaddingLeft, 0.0f);
            mPaddingRight = (int) typedArray.getDimension(R.styleable.GridRecyclerView_gridRecyclerPaddingRight, 0.0f);
            mClipToPadding = typedArray.getBoolean(R.styleable.GridRecyclerView_gridRecyclerClipToPadding, false);
            itemColumns = typedArray.getInt(R.styleable.GridRecyclerView_gridRecyclerColumn, 2);
        } finally {
            typedArray.recycle();
        }
    }

    protected void initViews() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ptr_refresh_recycler_view_layout, this);
        mRecyclerView = (ScrollRecyclerView) view.findViewById(R.id.ptr_frame_refresh_recycler);

        if (mRecyclerView != null) {
            mRecyclerView.setClipToPadding(mClipToPadding);
            if (mPadding != -1.1f) {
                mRecyclerView.setPadding(mPadding, mPadding, mPadding, mPadding);
            } else {
                mRecyclerView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
            }
        }

        manager = new GridLayoutManager(getContext(), itemColumns);
        mRecyclerView.setLayoutManager(manager);
    }

    private void initCustomSwipeToRefresh() {
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_frame_refresh_layout);
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
                }, 0);
            }
        });
    }

    public void setRefreshCallBackListener(FrameRefreshListener listener) {
        this.refreshListener = listener;
    }

    /**
     * Set the layout manager to the recycler
     */
    public GridLayoutManager getLayoutManager() {
        return manager;
    }

    private RecyclerView.AdapterDataObserver dataObserver;


    public void setAdapter(MultiRecycleTypesAdapter adapter) {
        mAdapter = adapter;
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
        //决定每个 position 上的项应该跨越多少列
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 && mAdapter.getmHeaderView() != null)
                    return itemColumns;
                if (position == mAdapter.getItemCount() - 1 && mAdapter.getCustomLoadMoreView() != null)
                    return itemColumns;
                else
                    return 1;
            }
        });
    }


    //设置滚动监听
    public void setRecyclerScrollListener(RecyclerView.OnScrollListener listener) {
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
        mOnScrollListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mHeader != null) {
                    mTotalYScrolled += dy;
                    if (isParallaxHeader)
                        translateHeader(mTotalYScrolled);
                }
            }
        };
        mRecyclerView.addOnScrollListener(mOnScrollListener);

    }

    private int loadMoreHeight = 0;

    /**
     * Enable loading more of the recyclerview
     */
    public void enableLoadmore() {
        if (mOnScrollListener != null)
            mRecyclerView.removeOnScrollListener(mOnScrollListener);
        mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mHeader != null) {
                    mTotalYScrolled += dy;
                    if (isParallaxHeader)
                        translateHeader(mTotalYScrolled);
                }
                if (!mAdapter.mIsLoadMoreWidgetEnabled) {
                    mVisibleItemCount = manager.getChildCount();
                    mTotalItemCount = manager.getItemCount();
                    lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                    mFirstVisibleItem = manager.findFirstVisibleItemPosition();
                    //      YisLoger.debug("lastVisibleItemPosition          " + lastVisibleItemPosition + "         mTotalItemCount      " + mTotalItemCount);
                    if (lastVisibleItemPosition == mTotalItemCount - 1) {
                        View lastVisibleItem = manager.findViewByPosition(lastVisibleItemPosition);
                        Rect rc = new Rect();
                        lastVisibleItem.getLocalVisibleRect(rc);
//                        YisLoger.debug("rc.bottom - rc.top          " + (rc.bottom - rc.top));
                        if (rc.bottom - rc.top >= lastVisibleItem.getMeasuredHeight() / 2)
                            if (!isLoadingMore) {
                                isLoadingMore = true;
                                loadMoreHeight = lastVisibleItem.getMeasuredHeight();
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
    public void disableLoadmore(int count) {
        if (count == 0)
            mTotalYScrolled -= loadMoreHeight;
        setDefaultScrollListener();
        if (mAdapter != null) {
            mAdapter.setCustomLoadMoreView(LayoutInflater.from(getContext())
                    .inflate(R.layout.empty_progressbar, null));
            mAdapter.setmIsLoadMoreWidgetEnabled(true);
            mAdapter.notifyItemChanged(mAdapter.getItemCount() - 1);
        }
    }

    //视图刷新
    private void updateHelperDisplays() {
        if (mAdapter == null)
            return;
        isLoadingMore = false;
//        if (mAdapter.getCustomLoadMoreView() == null) return;
//        if (mAdapter.getAdapterItemCount() >= showLoadMoreItemNum) {
//            mAdapter.getCustomLoadMoreView().setVisibility(View.VISIBLE);
//        } else {
//            mAdapter.getCustomLoadMoreView().setVisibility(View.GONE);
//        }
    }

    private View mHeader;
    private OnParallaxScroll mParallaxScroll;
    private static boolean isParallaxHeader = false;
    private final float SCROLL_MULTIPLIER = 0.5f;
    private int mTotalYScrolled;


    /**
     * Set the parallax header of the recyclerview
     *
     * @param header the view
     */
    public void setParallaxHeader(View header) {
        mHeader = header;
        if (mAdapter != null)
            mAdapter.setmHeaderView(mHeader);
        isParallaxHeader = true;
    }


    /**
     * Set the on scroll method of parallax header
     *
     * @param parallaxScroll
     */
    public void setOnParallaxScroll(OnParallaxScroll parallaxScroll) {
        mParallaxScroll = parallaxScroll;
        mParallaxScroll.onParallaxScroll(0, 0, mHeader);
    }

    public interface OnParallaxScroll {
        void onParallaxScroll(float percentage, float offset, View parallax);
    }

    //滚动监听
    private void translateHeader(float of) {
        float ofCalculated = of * SCROLL_MULTIPLIER;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            //Logs.d("ofCalculated    " + ofCalculated+"   "+mHeader.getHeight());
//            mHeader.setTranslationY(ofCalculated);
//        } else {
//            TranslateAnimation anim = new TranslateAnimation(0, 0, ofCalculated, ofCalculated);
//            anim.setFillAfter(true);
//            anim.setDuration(0);
//            mHeader.startAnimation(anim);
//        }
//        mHeader.setClipY(Math.round(ofCalculated));
        if (mParallaxScroll != null) {
            float left = Math.min(1, ((ofCalculated) / (mHeader.getHeight() * SCROLL_MULTIPLIER)));
            mParallaxScroll.onParallaxScroll(left, of, mHeader);
        }
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
