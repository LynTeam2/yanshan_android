package cn.gov.bjys.onlinetrain.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ZipUtils;
import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.view.ProgressWebView;
import com.ycl.framework.view.TitleHeaderView;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.business.DownLoadInfoBusiness;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.entity.DataInfo;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.entity.DownLoadInfoBean;
import com.zls.www.mulit_file_download_lib.multi_file_download.view.MultiDownView;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.utils.UpdateFileUtils;


public class ShopFragment extends FrameFragment {

    public final static String TAG = ShopFragment.class.getSimpleName();


    @Bind(R.id.header)
    TitleHeaderView mHeader;

    @Bind(R.id.web_view)
    ProgressWebView webView;



    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_shop_layout, container, false);
        StatusBarUtil.addStatusForFragment(getActivity(),view.findViewById(R.id.status_bar_layout));
        return view;
    }


    @Override
    protected void initViews() {
        super.initViews();

    }



    @Override
    protected void initData() {
        super.initData();
    }

}
