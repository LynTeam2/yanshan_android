package com.ycl.framework.utils.helper;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.ycl.framework.R;
import com.ycl.framework.bean.BannerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * banner视图的helper类 by joeYu on 17/2/8.
 */
@Deprecated
public class BannerHelper {

    //初始化 banner
    public static void initBanner(final ConvenientBanner bannerPoster, List<BannerBean> banners,OnItemClickListener listener ) {

        if (banners == null) {
            banners = new ArrayList<>();
        }
//        bannerPoster.setCanLoop(true);//能否循环
        bannerPoster.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, banners)
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setOnItemClickListener(listener);
        // .setOnPageChangeListener(this)//监听翻页事件
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。

        if (banners.size() > 1) {
            //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
            bannerPoster.setPageIndicator(new int[]{R.drawable.icon_page_indicator_normal, R.drawable.icon_page_indicator_press});
            bannerPoster.startTurning(6000);
            bannerPoster.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
            bannerPoster.setManualPageable(true);//设置不能手动影响
        } else {
            bannerPoster.setManualPageable(false);//设置不能手动影响
        }
    }


}
