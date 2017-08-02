package com.ycl.framework.view.recycleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ycl.framework.R;
import com.ycl.framework.utils.string.DensityUtils;
import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.utils.util.YisLoger;
import com.ycl.framework.view.recycleview.adapter.MultiRecycleTypesAdapter;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * PtrRefresh的 RecyclerView on 2015/11/6.
 */
public class PtrRefreshRecyclerView extends FrameLayout {

    private OnLoadMoreListener onLoadMoreListener;
    private RelativeLayout mContainer;
    public ScrollRecyclerView mRecyclerView;
    private MultiRecycleTypesAdapter mAdapter;

    public PtrFrameLayout mPtrFrameLayout;
    private View emptyView;

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

    public PtrRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public PtrRefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        LayoutInflater inflater = LayoutInflater.from(getContext());// (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //此时默认是true  已经加到
        inflater.inflate(R.layout.ptr_refresh_recycler_view_layout, this);
//        mContainer = (RelativeLayout) view.findViewById(R.id.ptr_frame_refresh_recycler_container);
        mRecyclerView = (ScrollRecyclerView) findViewById(R.id.ptr_frame_refresh_recycler);

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

    public void setEmptyImage(int resId) {
        //Todo 扩展增加空白底图
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setVisibility(GONE);
        imageView.setImageResource(resId);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = DensityUtils.dp2px(140, getContext());
        mContainer.addView(imageView, params);

        emptyView = imageView;
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
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    public void run() {
                        if (refreshListener != null)
                            refreshListener.refresh();
                    }
                }, 80);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return !mRecyclerView.canScrollVertically(-1);
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
                    YisLoger.logTagI("onItemRangeChanged", "positionStart  =  " + positionStart + " itemCount  =  " + itemCount);
                    updateHelperDisplays();
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    YisLoger.logTagI("onItemRangeInserted", "positionStart  =  " + positionStart + " itemCount  =  " + itemCount);
                    updateHelperDisplays();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    YisLoger.logTagI("onItemRangeRemoved", "positionStart  =  " + positionStart + " itemCount  =  " + itemCount);
                    updateHelperDisplays();
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                    YisLoger.logTagI("onItemRangeMoved", "fromPosition  =  " + fromPosition + "   toPosition  =  " + toPosition + " itemCount  =  " + itemCount);

                    updateHelperDisplays();
                }

                @Override
                public void onChanged() {
                    super.onChanged();
                    YisLoger.logTagI("onChanged", "    =  ");

                    updateHelperDisplays();
                    updateEmptyStyle();
                }
            };
            mAdapter.registerAdapterDataObserver(dataObserver);
        }
    }

    //普通列表 不进行register
    public void setAdapterUnRegister(MultiRecycleTypesAdapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);

    }

    public void updateEmptyStyle() {
        if (mAdapter != null && emptyView != null) {
            if (mAdapter.getDataSize() == 0) {
                if (emptyView.getVisibility() != View.VISIBLE)
                    emptyView.setVisibility(View.VISIBLE);
                if (mRecyclerView.getVisibility() != View.GONE)
                    mRecyclerView.setVisibility(View.GONE);
            } else {
                if (emptyView.getVisibility() != View.GONE)
                    emptyView.setVisibility(View.GONE);
                if (mRecyclerView.getVisibility() != View.VISIBLE)
                    mRecyclerView.setVisibility(View.VISIBLE);
            }
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
//            mFirstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
            //      YisLoger.debug("lastVisibleItemPosition          " + lastVisibleItemPosition + "         mTotalItemCount      " + mTotalItemCount);
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
    }

    //自定义提示footer
    public void disableHintLoadmore(RecyclerView.OnScrollListener listener) {
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        if (listener != null) {
            mOnScrollListener = listener;
            mRecyclerView.addOnScrollListener(mOnScrollListener);
        }
        if (mAdapter != null) {
            TextView loadView = new TextView(getContext());
            loadView.setTag("custom");
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int padTop = DensityUtils.getAutoSizePx(28);
            loadView.setPadding(0, padTop, 0, padTop);
            loadView.setTextSize(14);
            loadView.setTextColor(Color.BLACK);
            loadView.setGravity(Gravity.CENTER);
            loadView.setText("查看全部");
            loadView.setLayoutParams(params);
            mAdapter.setCustomLoadMoreView(loadView);
            mAdapter.setmIsLoadMoreWidgetEnabled(true);
            mAdapter.notifyItemChanged(mAdapter.getItemCount() - 1);
        }
    }

    //自定义提示footer
    public void addCustomFootView(View customVIew) {
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        if (mAdapter != null) {
            mAdapter.setCustomLoadMoreView(customVIew);
            customVIew.setTag("custom");
            mAdapter.setmIsLoadMoreWidgetEnabled(true);
//            mAdapter.notifyItemChanged(mAdapter.getItemCount() - 1);
        }
    }


    public void refreshDisableMore() {
        if (mAdapter != null) {
            mAdapter.setCustomLoadMoreView(LayoutInflater.from(getContext())
                    .inflate(R.layout.empty_progressbar, null));
            mAdapter.notifyItemChanged(mAdapter.getItemCount() - 1);
        }
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

    public void disableWhenHorizontalMove() {
        mPtrFrameLayout.disableWhenHorizontalMove(true);
    }

    public interface OnLoadMoreListener {
        void loadMore(int itemsCount, final int maxLastVisiblePosition);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mAdapter != null && dataObserver != null) {
            try {
                mAdapter.unregisterAdapterDataObserver(dataObserver);
            } catch (Exception ignore) {
                //popup中 会出现 未注册情况
            }
        }
        refreshListener = null;
        mOnScrollListener = null;
        onLoadMoreListener = null;
        super.onDetachedFromWindow();
    }

}
