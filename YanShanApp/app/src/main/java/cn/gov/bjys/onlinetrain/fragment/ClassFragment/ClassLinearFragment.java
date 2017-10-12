package cn.gov.bjys.onlinetrain.fragment.ClassFragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import cn.gov.bjys.onlinetrain.adapter.DooSimpleMultiAdapter;
import cn.gov.bjys.onlinetrain.bean.YSClassBean;

/**
 * Created by Administrator on 2017/10/10 0010.
 */
public class ClassLinearFragment extends FrameFragment {

    public static ClassLinearFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ClassLinearFragment fragment = new ClassLinearFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_linear_layout, container, false);
    }


    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Bind(R.id.rv)
    RecyclerView mRecyclerView;

    @Override
    protected void initViews() {
        super.initViews();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final List<YSClassBean> data = prepareTestData();
        final DooSimpleMultiAdapter multipleItemAdapter = new DooSimpleMultiAdapter(data);
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(manager);
        multipleItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return data.get(position).getSpan();
            }
        });
        mRecyclerView.setAdapter(multipleItemAdapter);
    }

    private List<YSClassBean> prepareTestData() {
        List<YSClassBean> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            YSClassBean bean = new YSClassBean();
            bean.setName("this is " + i);
            datas.add(bean);
        }
        return datas;
    }
    
}
