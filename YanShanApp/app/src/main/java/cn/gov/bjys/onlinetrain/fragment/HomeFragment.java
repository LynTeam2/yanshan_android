package cn.gov.bjys.onlinetrain.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.view.recycleview.SwipeRefreshRecyclerView;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.DooItemTitleLayout;
import cn.gov.bjys.onlinetrain.act.view.DooSearchLayout;
import cn.gov.bjys.onlinetrain.adapter.DooHomeClassStudyAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooHomePullRefreshAdapter;
import cn.gov.bjys.onlinetrain.bean.AnjianBean;
import cn.gov.bjys.onlinetrain.bean.ClassStudyBean;
import cn.gov.bjys.onlinetrain.utils.BannerComHelper;


public class HomeFragment extends FrameFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    @Bind(R.id.banner)
    ConvenientBanner mBanner;


    //search layout
    @Bind(R.id.search_layout)
    DooSearchLayout mDooSearchLayout;

    @Bind(R.id.item_title_layout)
    DooItemTitleLayout mDooItemTitleLayout;

    @Bind(R.id.class_study_rv)
    RecyclerView mClassStudyRv;

    @Bind(R.id.anjian_rv)
    RecyclerView mAnJianRv;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        StatusBarUtil.addStatusForFragment(getActivity(),view.findViewById(R.id.status_bar_layout));
        return view;
    }

    public static int[] res = {
            R.mipmap.ic_launcher,
            R.mipmap.wx_login_icon
    };

    @Override
    protected void initViews() {
        super.initViews();
        //banner
        BannerComHelper.initLocationBanner(mBanner, res);

        initClassStudyRv();

        initAnjianRv();
    }

    @OnClick({R.id.search_layout})
     public void onTabClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.search_layout:
                break;
        }
    }
    DooHomeClassStudyAdapter mDooHomeClassStudyAdapter;
    private void initClassStudyRv(){
        mDooHomeClassStudyAdapter = new DooHomeClassStudyAdapter(R.layout.item_home_classstudy_layout,getClassStudyDatas());
        mClassStudyRv.setLayoutManager(new GridLayoutManager(getContext(),2));
        mClassStudyRv.setAdapter(mDooHomeClassStudyAdapter);

    }

    private List<ClassStudyBean> getClassStudyDatas(){
        List<ClassStudyBean> datas = new ArrayList<>();
        for(int i=0; i < 10; i++){
            ClassStudyBean bean = new ClassStudyBean();
            bean.setClassName("测试标题+" + i);
            datas.add(bean);
        }
        return datas;
    }


    DooHomePullRefreshAdapter mDooHomePullRefreshAdapter;
    private void initAnjianRv(){
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mDooHomePullRefreshAdapter = new DooHomePullRefreshAdapter(R.layout.item_home_anjian_layout, getAnjianDatas());
        mDooHomePullRefreshAdapter.setOnLoadMoreListener(this,mAnJianRv);
        mAnJianRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAnJianRv.setAdapter(mDooHomePullRefreshAdapter);

    }


    private List<AnjianBean> getAnjianDatas(){
        List<AnjianBean> datas = new ArrayList<>();
        for(int i=0; i < 10; i++){
            AnjianBean bean = new AnjianBean();
            bean.setContent("测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容");
            bean.setTitle("测试标题+" + i);
            datas.add(bean);
        }
        return datas;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }
}
