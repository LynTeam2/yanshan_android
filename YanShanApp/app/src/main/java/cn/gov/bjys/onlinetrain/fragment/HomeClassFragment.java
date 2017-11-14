package cn.gov.bjys.onlinetrain.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.ycl.framework.base.FrameFragment;
import com.zls.www.statusbarutil.StatusBarUtil;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.TitleHeadNormalOne;


public class HomeClassFragment extends FrameFragment {

    @Bind(R.id.header)
    TitleHeadNormalOne mHeader;

    @Bind(R.id.more_class)
    ImageView more_class;

    @Bind(R.id.class_gridview)
    GridView class_gridview;

    @Bind(R.id.banner)
    ConvenientBanner banner;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_home_class_layout, container, false);
        StatusBarUtil.addStatusForFragment(getActivity(),view.findViewById(R.id.status_bar_layout));
        return view;
    }



    @Override
    protected void initViews() {
        super.initViews();

    }



}
