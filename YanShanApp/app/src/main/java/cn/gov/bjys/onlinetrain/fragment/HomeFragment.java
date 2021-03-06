package cn.gov.bjys.onlinetrain.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.ycl.framework.utils.util.ToastUtil;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.AnJianDetailActivity;
import cn.gov.bjys.onlinetrain.act.PDFWebActivity;
import cn.gov.bjys.onlinetrain.act.PracticePrepareActivity;
import cn.gov.bjys.onlinetrain.act.SearchActivity;
import cn.gov.bjys.onlinetrain.act.view.DooItemTitleLayout;
import cn.gov.bjys.onlinetrain.act.view.DooSearchLayout;
import cn.gov.bjys.onlinetrain.adapter.DooHomeClassStudyAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooHomePullRefreshAdapter;
import cn.gov.bjys.onlinetrain.api.BaseResponse;
import cn.gov.bjys.onlinetrain.api.HomeApi;
import cn.gov.bjys.onlinetrain.bean.AnjianBean;
import cn.gov.bjys.onlinetrain.bean.CourseBean;
import cn.gov.bjys.onlinetrain.bean.HomeAnJianBean;
import cn.gov.bjys.onlinetrain.bean.HomeAnJianList;
import cn.gov.bjys.onlinetrain.task.HomeNewCourseTask;
import cn.gov.bjys.onlinetrain.task.InitAllExamTask;
import cn.gov.bjys.onlinetrain.utils.BannerComHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


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
        StatusBarUtil.addStatusForFragment(getActivity(), view.findViewById(R.id.status_bar_layout));
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
//        BannerComHelper.initLocationBanner(mBanner, res);


        initClassStudyRv();
        initAnjianRv();
        initAllExam();//为专项练习准备数据
    }

    InitAllExamTask mInitAllExamTask;

    private void initAllExam() {
        mInitAllExamTask = new InitAllExamTask(getContext());
        mInitAllExamTask.execute();
    }

    @OnClick({R.id.search_layout, R.id.anjian_loadmore})
    public void onTabClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.search_layout:
//                ToastUtil.showToast(getString(R.string.string_function_dismiss));
                startAct(SearchActivity.class);
/*              Bundle tempBundle = new Bundle();
                tempBundle.putString(PDFWebActivity.HEAD_TAG, "法律法规");
                tempBundle.putString(PDFWebActivity.URL_TAG, "http://39.104.118.75/resource/1.pdf");
                startAct(PDFWebActivity.class,tempBundle);*/
                break;
            case R.id.anjian_loadmore:
                isLoadMore = true;
                mPages++;
                remoteAnjian(mPages,SIZE);
                break;
            default:
                break;
        }
    }

    private boolean isLoadMore = false;

    DooHomeClassStudyAdapter mDooHomeClassStudyAdapter;

    private void initClassStudyRv() {
        mDooHomeClassStudyAdapter = new DooHomeClassStudyAdapter(R.layout.item_home_classstudy_layout, null);
        mClassStudyRv.setLayoutManager(new GridLayoutManager(getContext(), 2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mClassStudyRv.setAdapter(mDooHomeClassStudyAdapter);
        mDooHomeClassStudyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CourseBean bean = mDooHomeClassStudyAdapter.getData().get(position);
                try {
                    int type = Integer.valueOf(bean.getCourseType());
                    int id = bean.getId();
                    Bundle mBundle = new Bundle();
                    mBundle.putInt("type",type);
                    mBundle.putInt("id", id);
                    startAct(PracticePrepareActivity.class, mBundle);
                }catch (Exception e){

                }
            }
        });
    }




    DooHomePullRefreshAdapter mDooHomePullRefreshAdapter;

    private void initAnjianRv() {
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mDooHomePullRefreshAdapter = new DooHomePullRefreshAdapter(R.layout.item_home_anjian_layout, mHomeAnjianList);
//        mDooHomePullRefreshAdapter.setOnLoadMoreListener(this,mAnJianRv);
        mAnJianRv.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAnJianRv.setAdapter(mDooHomePullRefreshAdapter);
        mAnJianRv.setNestedScrollingEnabled(false);
        mDooHomePullRefreshAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
             HomeAnJianBean bean =   mDooHomePullRefreshAdapter.getData().get(position);
                Bundle mBundle = new Bundle();
                mBundle.putLong("id", bean.getId());
                mBundle.putParcelable("news",bean);
                startAct(AnJianDetailActivity.class,mBundle);
            }
        });
//        loadingAnjianDongtai();
    }


    private List<AnjianBean> getAnjianDatas() {
        List<AnjianBean> datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AnjianBean bean = new AnjianBean();
            bean.setContent("中国通过国家主席习近平3日出席能赚国家工商中国通过国家主席习近平3日出席能赚国家工商");
            bean.setTitle(i + "习近平:金砖国家是国际安全秩序的建设者");
            datas.add(bean);
        }
        return datas;
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        if(visible){
            loadingBanner();
            loadingNewsClass();
            loadingAnjianDongtai();
        }
    }

    //加载轮播图
    private void loadingBanner(){
        BannerComHelper.initZipBanner(mBanner, "banner.json");
    }

    //加载最新课程
    private void loadingNewsClass(){
       new HomeNewCourseTask(mDooHomeClassStudyAdapter).execute();
    }

    //加载安监动态
    private void loadingAnjianDongtai(){
        mPages = 0;
        remoteAnjian(mPages,SIZE);
    }

    List<HomeAnJianBean> mHomeAnjianList = new ArrayList<>();
    private int mPages = 0;
    private final int  SIZE  = 5;
    private void remoteAnjian(int page, int size){
        rx.Observable<BaseResponse<String>> obs;
        obs = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).getSpeUrlService(YSConst.BaseUrl.BASE_URL
                , HomeApi.class).getAnjianDatas(page,size);
        obs .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<String>>() {
                    @Override
                    public void onCompleted() {
                        isLoadMore = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoadMore = false;
                    }

                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                          if("1".equals(stringBaseResponse.getCode())){
                              String res = stringBaseResponse.getResults();
                              HomeAnJianList.Parent parent =   FastJSONParser.getBean(res, HomeAnJianList.Parent.class);
                              List<HomeAnJianBean>   datas =  parent.getNewsList().getContent();
                              if(datas.size() > 0) {
                                  if(isLoadMore){
                                      mHomeAnjianList.addAll(datas);
                                      mDooHomePullRefreshAdapter.setNewData(mHomeAnjianList);
                                  }else{
                                      mHomeAnjianList.clear();
                                      mHomeAnjianList.addAll(datas);
                                      mDooHomePullRefreshAdapter.setNewData(mHomeAnjianList);
                                  }
                              }else{
                                  if(isLoadMore){
                                      ToastUtil.showToast("没有更多了！");
                                  }
                              }
                          }
                    }
                });
    }
}
