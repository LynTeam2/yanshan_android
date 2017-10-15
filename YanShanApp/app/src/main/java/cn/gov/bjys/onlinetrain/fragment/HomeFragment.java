package cn.gov.bjys.onlinetrain.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.ycl.framework.base.FrameFragment;
import com.zls.www.statusbarutil.StatusBarUtil;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.DooItemTitleLayout;
import cn.gov.bjys.onlinetrain.act.view.DooSearchLayout;
import cn.gov.bjys.onlinetrain.utils.BannerComHelper;


public class HomeFragment extends FrameFragment {


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

    private void initClassStudyRv(){

    }

    private void initAnjianRv(){

    }

}
