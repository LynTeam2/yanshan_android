package cn.gov.bjys.onlinetrain.fragment.ClassFragment;

import android.os.Bundle;
import android.support.annotation.StringDef;
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
import cn.gov.bjys.onlinetrain.act.LawsSecondActivity;
import cn.gov.bjys.onlinetrain.adapter.DooLawsAdapter;
import cn.gov.bjys.onlinetrain.api.ZipCallBackListener;
import cn.gov.bjys.onlinetrain.bean.LawsBean;
import cn.gov.bjys.onlinetrain.bean.NativeLawsBean;
import cn.gov.bjys.onlinetrain.task.LawsTask;

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
//        prepareDatas();//测试
        mDooLawsAdapter = new DooLawsAdapter(R.layout.item_laws_item_layout,null);
         LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mDooLawsAdapter);
        mDooLawsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mDooLawsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NativeLawsBean temp = mDatas.get(position);
                Bundle bundle = new Bundle();
                bundle.putLong(LawsSecondActivity.ID,temp.getId());
                bundle.putString(LawsSecondActivity.NAME,temp.getName());
                startAct(LawsSecondActivity.class,bundle);
            }
        });

//        mDooLawsAdapter.setOnLoadMoreListener(this,mRecyclerView);
    }

    private List<NativeLawsBean> mDatas = new ArrayList<>();


    @Override
    public void onRefresh() {
        //刷新操作
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if(visible){
            refresh();
        }
    }

    private void refresh(){
        new LawsTask(new ZipCallBackListener() {
            @Override
            public void zipCallBackListener(List datas) {
                if(null != datas&& datas.size() > 0){
                    mDatas.clear();
                    mDatas.addAll(datas);
                }else{
                }
                mDooLawsAdapter.setNewData(mDatas);
            }

            @Override
            public void zipCallBackSuccess() {
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void zipCallBackFail() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }).execute();
    }
}
