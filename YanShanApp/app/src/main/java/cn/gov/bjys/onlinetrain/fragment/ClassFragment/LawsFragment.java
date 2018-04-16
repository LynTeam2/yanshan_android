package cn.gov.bjys.onlinetrain.fragment.ClassFragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ycl.framework.base.FrameFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.adapter.DooLawsAdapter;
import cn.gov.bjys.onlinetrain.bean.LawsBean;

/**
 * Created by Administrator on 2018/4/15 0015.
 */
public class LawsFragment extends FrameFragment implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {

    public static LawsFragment newInstance() {
        Bundle args = new Bundle();
        LawsFragment fragment = new LawsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_laws_layout, container, false);
    }


    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Bind(R.id.rv)
    RecyclerView mRecyclerView;

    DooLawsAdapter mDooLawsAdapter;

    @Override
    protected void initViews() {
        super.initViews();
        prepareDatas();//测试
        mDooLawsAdapter = new DooLawsAdapter(R.layout.item_laws_item_layout,mDatas);
         LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mDooLawsAdapter);
        mDooLawsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mDooLawsAdapter.setOnLoadMoreListener(this,mRecyclerView);
    }

    private List<LawsBean> mDatas;
    private void prepareDatas(){
        mDatas = new ArrayList<>();
        for(int i=0; i<10; i++){
            LawsBean bean = new LawsBean();
            bean.setTitle("法律法规"+i);
            mDatas.add(bean);
        }
    }

    @Override
    public void onRefresh() {
        //刷新操作
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
