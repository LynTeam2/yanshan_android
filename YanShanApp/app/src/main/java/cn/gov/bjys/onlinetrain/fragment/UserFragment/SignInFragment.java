package cn.gov.bjys.onlinetrain.fragment.UserFragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.utils.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.adapter.DooExchangeRewardsAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooSigninGridAdapter;
import cn.gov.bjys.onlinetrain.bean.RewardBean;
import cn.gov.bjys.onlinetrain.bean.SignInBean;

/**
 * 签到fragment
 */
public class SignInFragment  extends FrameFragment{

    public static SignInFragment newInstance() {
        Bundle args = new Bundle();
        SignInFragment fragment = new SignInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Bind(R.id.icon)
    ImageView icon;

    @Bind(R.id.money)
    TextView money;

    @Bind(R.id.grid_view)
    GridView grid_view;

    @Bind(R.id.rv)
    RecyclerView rv;

    @Bind(R.id.sign_layout)
    RelativeLayout mSignLayout;


    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return View.inflate(getContext(), R.layout.fragment_sign_in_layout, null);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initViews() {
        super.initViews();
        initGridView();
        initRecycleView();
    }
    DooSigninGridAdapter mDooSigninGridAdapter;
    public void initGridView(){
        mDooSigninGridAdapter = new DooSigninGridAdapter(getContext(), prepareDatas());
        grid_view.setAdapter(mDooSigninGridAdapter);
    }

    public List<SignInBean> prepareDatas(){
        List<SignInBean> list = new ArrayList<>();
        for(int i=0;i<4;i++){
            SignInBean bean = new SignInBean();
            bean.setDay("0"+i);
            bean.setMouth(i+"月");
            bean.setType(i<1?SignInBean.FINISH:SignInBean.ING);
            list.add(bean);
        }
        return list;
    }


    DooExchangeRewardsAdapter mDooExchangeRewardsAdapter;
    public void initRecycleView(){
        mDooExchangeRewardsAdapter = new DooExchangeRewardsAdapter(R.layout.item_exchange_reward_item,prepareDatasRv());
        rv.setAdapter(mDooExchangeRewardsAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public List<RewardBean> prepareDatasRv(){
        List<RewardBean> list = new ArrayList<>();
        for(int i=0;i<4;i++){
            RewardBean bean = new RewardBean();
            bean.setId(i);
            bean.setName("this is =" + i);
            list.add(bean);
        }
        return list;
    }

    @OnClick({R.id.sign_layout})
    public void onTabClick(View v){
        switch (v.getId()){
            case R.id.sign_layout:
                ToastUtil.showToast("签到成功");
                break;
        }
    }

}
