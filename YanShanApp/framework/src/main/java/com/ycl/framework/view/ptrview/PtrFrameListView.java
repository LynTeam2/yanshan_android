package com.ycl.framework.view.ptrview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.ycl.framework.R;
import com.ycl.framework.view.ptrview.LoadmoreListview.IXListViewListener;
import com.ycl.framework.view.recycleview.PtrClassisCustomHeader;

import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 * ptrfamelayout的下拉刷新
 */
public class PtrFrameListView extends FrameLayout {

    public PtrFrameLayout mPtrFrameLayout;
    public LoadmoreListview mLv;

    public PtrFrameListView(Context context) {
        this(context, null, 0);
    }

    public PtrFrameListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrFrameListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_ptr_list_view, this);
        mLv = (LoadmoreListview) findViewById(R.id.lv_load_more_ptr);
        initCustomSwipeToRefresh();
    }

    private void setListViewListener(IXListViewListener listener) {
        mLv.setXListViewListener(listener);
    }


    private void initCustomSwipeToRefresh() {
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_refresh_layout);
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
    }

}
