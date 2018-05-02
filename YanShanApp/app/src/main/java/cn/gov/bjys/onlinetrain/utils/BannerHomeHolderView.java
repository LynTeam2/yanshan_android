package cn.gov.bjys.onlinetrain.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.ycl.framework.utils.helper.ViewBindHelper;
import com.ycl.framework.utils.util.GlideProxy;

import java.io.File;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.HomeBannerBean;

/**
 * Created by dodozhou on 2017/9/25.
 */
public class BannerHomeHolderView implements Holder<HomeBannerBean>{

    private View mRootView;

    @Override
    public View createView(Context context) {
        mRootView = View.inflate(context, R.layout.view_home_banner,null);
        return mRootView;
    }

    @Override
    public void UpdateUI(Context context, int position, HomeBannerBean data) {
//        File file = getNeedFile(data.getPath());
        GlideProxy.loadPicWithCommon((ImageView) mRootView.findViewById(R.id.banner_img),
                data.getPath(),
                R.drawable.icon_1080_580);
    }

    private File getNeedFile(String name){
        return new File(AssetsHelper.getYSPicPath(name));
    }
}
