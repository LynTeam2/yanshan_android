package cn.gov.bjys.onlinetrain.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.view.TitleHeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.adapter.DooLawsSecondAdapter;
import cn.gov.bjys.onlinetrain.api.BaseResponse;
import cn.gov.bjys.onlinetrain.api.UserApi;
import cn.gov.bjys.onlinetrain.bean.LawContentBean;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dodo on 2018/5/4.
 */

public class LawsSecondActivity extends FrameActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    public static String ID = "ID";
    public static String NAME = "NAME";

    @Bind(R.id.header)
    TitleHeaderView header;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycler)
    RecyclerView recyclerView;

    DooLawsSecondAdapter mDooLawsSecondAdapter;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_law_second_layout);
    }

    private long id;
    private String name = "";

    @Override
    public void initViews() {
        super.initViews();
        Intent recIntent = getIntent();
        Bundle recBundle = recIntent.getExtras();
        id = recBundle.getLong(ID);
        name = recBundle.getString(NAME);
        if (!TextUtils.isEmpty(name)) {
            header.setTitleText(name);
        }
        initRecycler();
        remoteLaws();
    }

    private void initRecycler() {
        mDooLawsSecondAdapter = new DooLawsSecondAdapter(R.layout.item_law_second_item_layout, null);
        recyclerView.setAdapter(mDooLawsSecondAdapter);
        mDooLawsSecondAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mDooLawsSecondAdapter.setOnLoadMoreListener(this, recyclerView);
        mDooLawsSecondAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

    }

    private int mPage = 0;
    private int mSize = 8;
    private List<LawContentBean> mDatas = new ArrayList<>();

    private void remoteLaws() {
        Observable<BaseResponse<String>> obsLogin;
        obsLogin = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).
                getSpeUrlService(YSConst.BaseUrl.BASE_URL, UserApi.class).getLaws(mPage, mSize, id);
        obsLogin.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<String>>() {
                    @Override
                    public void onCompleted() {
                        isLoadMore = false;
                        isRefresh = false;
                        mSwipeRefreshLayout.setRefreshing(false);
                        mDooLawsSecondAdapter.setEnableLoadMore(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoadMore = false;
                        isRefresh = false;
                        mSwipeRefreshLayout.setRefreshing(false);
                        mDooLawsSecondAdapter.setEnableLoadMore(true);
                    }

                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                        Log.d("dodot", "stringbase = " + stringBaseResponse);
                        if ("1".equals(stringBaseResponse.getCode())) {
                            String ret = stringBaseResponse.getResults();
                            LawContentBean.First first = FastJSONParser.getBean(ret, LawContentBean.First.class);
                            if (first != null) {
                                LawContentBean.Second second = first.getLaws();
                                if (second != null) {
                                    List<LawContentBean> datas = second.getContent();
                                    if (isLoadMore) {
                                        mDatas.addAll(datas);
                                    } else {
                                        mDatas.clear();
                                        mDatas.addAll(datas);
                                    }
                                    mDooLawsSecondAdapter.setNewData(mDatas);
                                    mDooLawsSecondAdapter.loadMoreComplete();
                                }
                            }
                        }else{
                            ToastUtil.showToast(stringBaseResponse.getMsg());
                            mDooLawsSecondAdapter.loadMoreFail();
                        }
                    }
                });
    }


    private boolean isRefresh = false;

    @Override
    public void onRefresh() {
        if (isLoadMore) {
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }
        isRefresh = true;
        mDooLawsSecondAdapter.setEnableLoadMore(false);
        mPage = 0;
        remoteLaws();
    }


    private boolean isLoadMore = false;

    @Override
    public void onLoadMoreRequested() {
        isLoadMore = true;
        mPage++;
        remoteLaws();
    }
}
