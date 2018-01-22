package cn.gov.bjys.onlinetrain.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.utils.util.ToastUtil;
import com.zhy.autolayout.utils.AutoUtils;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.AnJianDetailActivity;
import cn.gov.bjys.onlinetrain.act.PracticePrepareActivity;
import cn.gov.bjys.onlinetrain.act.view.DooItemTitleLayout;
import cn.gov.bjys.onlinetrain.act.view.DooSearchLayout;
import cn.gov.bjys.onlinetrain.adapter.DooHomeClassStudyAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooHomePullRefreshAdapter;
import cn.gov.bjys.onlinetrain.bean.AnjianBean;
import cn.gov.bjys.onlinetrain.bean.ClassStudyBean;
import cn.gov.bjys.onlinetrain.utils.BannerComHelper;


public class HomeFragment extends FrameFragment {


    @Bind(R.id.banner)
    ConvenientBanner mBanner;

    @Bind(R.id.anjian_loadmore)
    TextView anjian_loadmore;

    //search layout
    @Bind(R.id.search_layout)
    DooSearchLayout mDooSearchLayout;

    @Bind(R.id.item_title_layout)
    DooItemTitleLayout mDooItemTitleLayout;

    @Bind(R.id.class_study_rv)
    RecyclerView mClassStudyRv;

    @Bind(R.id.anjian_rv)
    RecyclerView mAnJianRv;


    @Bind(R.id.anjian_layout)
    LinearLayout anjian_layout;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        StatusBarUtil.addStatusForFragment(getActivity(),view.findViewById(R.id.status_bar_layout));
        return view;
    }

    public static int[] res = {
            R.drawable.home_page_normal_img,
            R.drawable.home_page_normal_img
    };

    @Override
    protected void initViews() {
        super.initViews();
        //banner
        BannerComHelper.initLocationBanner(mBanner, res);

        initClassStudyRv();

        initAnjianRv();
    }

    @OnClick({R.id.search_layout, R.id.anjian_loadmore})
     public void onTabClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.search_layout:
                ToastUtil.showToast(getString(R.string.string_function_dismiss));
            break;
                case R.id.anjian_loadmore:
                     List<AnjianBean> temList = mDooHomePullRefreshAdapter.getData();
                      int before = temList.size();
                      temList.addAll(getAnjianDatas());
                      int last = temList.size();
                    Log.d("dodo","h  linearlayout= "+anjian_layout.getHeight());
                      int i  = anjian_layout.getHeight()+(last - before)* AutoUtils.getPercentHeightSize(300);
                        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,i);
                        anjian_layout.setLayoutParams(llp);
                    Log.d("dodo","h = "+anjian_loadmore.getHeight());
                    break;
                default:break;
        }
    }
    DooHomeClassStudyAdapter mDooHomeClassStudyAdapter;
    private void initClassStudyRv(){
        mDooHomeClassStudyAdapter = new DooHomeClassStudyAdapter(R.layout.item_home_classstudy_layout,getClassStudyDatas());
        mClassStudyRv.setLayoutManager(new GridLayoutManager(getContext(),2));
        mClassStudyRv.setAdapter(mDooHomeClassStudyAdapter);
        mDooHomeClassStudyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startAct(PracticePrepareActivity.class);
            }
        });
    }

    private List<ClassStudyBean> getClassStudyDatas(){
        List<ClassStudyBean> datas = new ArrayList<>();
        for(int i=0; i < 4; i++){
            ClassStudyBean bean = new ClassStudyBean();
            bean.setClassName("第" + i + "节课:安全生产教育培训");
            datas.add(bean);
        }
        return datas;
    }


    DooHomePullRefreshAdapter mDooHomePullRefreshAdapter;
    private void initAnjianRv(){
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mDooHomePullRefreshAdapter = new DooHomePullRefreshAdapter(R.layout.item_home_anjian_layout, getAnjianDatas());
//        mDooHomePullRefreshAdapter.setOnLoadMoreListener(this,mAnJianRv);
        mAnJianRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAnJianRv.setAdapter(mDooHomePullRefreshAdapter);
        mDooHomePullRefreshAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startAct(AnJianDetailActivity.class);
            }
        });
    }


    private List<AnjianBean> getAnjianDatas(){
        List<AnjianBean> datas = new ArrayList<>();
        for(int i=0; i < 5; i++){
            AnjianBean bean = new AnjianBean();
            bean.setContent("中国通过国家主席习近平3日出席能赚国家工商中国通过国家主席习近平3日出席能赚国家工商");
            bean.setTitle(i+"习近平:金砖国家是国际安全秩序的建设者");
            datas.add(bean);
        }
        return datas;
    }

}
