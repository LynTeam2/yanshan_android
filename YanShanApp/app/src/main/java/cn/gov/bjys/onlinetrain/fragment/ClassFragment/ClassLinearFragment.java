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
public class ClassLinearFragment extends FrameFragment implements SwipeRefreshLayout.OnRefreshListener {

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
        final List<YSClassBean> data = prepareTestData();
        final DooSimpleMultiAdapter multipleItemAdapter = new DooSimpleMultiAdapter(data);
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(manager);
        multipleItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return data.get(position).getSpanSize();
            }
        });
        mRecyclerView.setAdapter(multipleItemAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private List<YSClassBean> prepareTestData() {
        List<String> titles = getTitles();
        List<YSClassBean> datas = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            YSClassBean bean = new YSClassBean(2,YSClassBean.LINEAR_COLUMN_1);
            bean.setName(titles.get(i));
            bean.setContent("主要学习介绍有关化学品相关的知识和内容");
            datas.add(bean);
        }
        return datas;
    }

    private List<String> getTitles(){
        List<String> titles = new ArrayList<>();
        titles.add("危险化学品");
        titles.add("建筑施工");
        titles.add("人员密集场所");
        titles.add("交通运输");
        titles.add("工业企业");
        titles.add("消防");
        titles.add("特种设备");
        return titles;
    }

    private List<String> getContents(){
        List<String> contents = new ArrayList<>();
        contents.add("主要学习介绍有关化学品相关的知识和内容");
        contents.add("主要学习介绍有关化学品相关的知识和内容");
        contents.add("主要学习介绍有关化学品相关的知识和内容");
        contents.add("主要学习介绍有关化学品相关的知识和内容");
        contents.add("主要学习介绍有关化学品相关的知识和内容");
        contents.add("主要学习介绍有关化学品相关的知识和内容");
        contents.add("主要学习介绍有关化学品相关的知识和内容");
        return contents;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
