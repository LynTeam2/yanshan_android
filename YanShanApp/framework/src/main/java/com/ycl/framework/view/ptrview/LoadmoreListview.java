package com.ycl.framework.view.ptrview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * 加载更多ListView on 2015/7/23.
 */
public class LoadmoreListview extends ListView implements OnScrollListener {
    private float mLastY = -1; // save event y
    private Context ctx;
    private OnScrollListener mScrollListener; // user's scroll listener

    private Scroller mScroller; // used for scroll back
    // -- footer view
    public XListViewFooter mFooterView;
    private boolean mEnablePullLoad;
    private boolean mPullLoading;


    private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
    private final static int SCROLL_DURATION = 400; // scroll back duration

    private int mScrollBack;
    private final static int SCROLLBACK_FOOTER = 1;

    // the interface to trigger refresh and load more.
    private IXListViewListener mListViewListener;


    // total list items, used to detect is at the bottom of listview.
    private int mTotalItemCount;
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
    // feature.

    public LoadmoreListview(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScroller = new Scroller(context, new DecelerateInterpolator());
        ctx = context;
        init();
    }

    private void init() {
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as a proxy).
        super.setOnScrollListener(this);

        mFooterView = new XListViewFooter(ctx);
        addFooterView(mFooterView);
    }

    /**
     * enable or disable pull up load more feature.
     *
     * @param enable
     */
    public void setPullLoadEnable(boolean enable) {
        mEnablePullLoad = enable;
        if (!mEnablePullLoad) {
            mFooterView.hide();
            mFooterView.setOnClickListener(null);
        } else {
            mPullLoading = false;
            mFooterView.show();
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }


    private void startLoadMore() {
        mPullLoading = true;
        mFooterView.setState(XListViewFooter.STATE_LOADING);
        if (mListViewListener != null) {
            mListViewListener.onLoadMore();
        }
    }

    /**
     * stop load more, reset footer view.
     */
    public void stopLoadMore() {
        if (mPullLoading == true) {
            mPullLoading = false;
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
        }
    }

    /*
     * 加载完毕
     */
    public void LoadingOver() {
        mFooterView.setState(XListViewFooter.STATE_OVER);
        setLoadFooterEnable(false);
    }

    //加载脚footer  enable
    private boolean loadFooterEnable = true;

    public void setLoadFooterEnable(boolean loadFooterEnable) {
        this.loadFooterEnable = loadFooterEnable;
    }

    private void updateFooterHeight(float delta) {
        int height = mFooterView.getBottomMargin() + (int) delta;
        if (mEnablePullLoad && !mPullLoading) {
            if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
                // more.
                mFooterView.setState(XListViewFooter.STATE_READY);
            } else {
                mFooterView.setState(XListViewFooter.STATE_NORMAL);
            }
        }
        mFooterView.setBottomMargin(height);

        // setSelection(mTotalItemCount - 1); // scroll to bottom
    }


    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
                    SCROLL_DURATION);
            invalidate();
        }
    }

    public void setXListViewListener(IXListViewListener l) {
        mListViewListener = l;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
//            if (mScrollBack == SCROLLBACK_FOOTER) {
            mFooterView.setBottomMargin(mScroller.getCurrY());
//            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    public void invokeOnScrolling() {
//        if (mScrollListener instanceof OnXScrollListener) {
//            OnXScrollListener l = (OnXScrollListener) mScrollListener;
//            l.onXScrolling(this);
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (loadFooterEnable) {
                    if (getLastVisiblePosition() == mTotalItemCount - 1
                            && (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
                        // last item, already pulled up or want to pull up.
                        updateFooterHeight(-deltaY / OFFSET_RADIO);
                    }
                }
                break;
            default:
                mLastY = -1; // reset
                if (getLastVisiblePosition() == mTotalItemCount - 1) {
                    // invoke load more.
                    if (mEnablePullLoad
                            && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        if (mScrollListener != null) {
//            mScrollListener.onScrollStateChanged(view, scrollState);
//        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // send to user's listener
        mTotalItemCount = totalItemCount;
//        if (mScrollListener != null) {
//            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
//                    totalItemCount);
//        }
    }

    public interface IXListViewListener {
        void onRefresh();

        void onLoadMore();
    }

}
