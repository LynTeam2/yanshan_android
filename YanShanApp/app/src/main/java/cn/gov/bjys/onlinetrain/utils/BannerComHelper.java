package cn.gov.bjys.onlinetrain.utils;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.j256.ormlite.stmt.query.In;
import com.ycl.framework.utils.helper.LocalImageHolderView;
import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.HRetrofitNetHelper;

import java.util.ArrayList;
import java.util.List;

import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.api.HomeApi;
import cn.gov.bjys.onlinetrain.bean.HomeBannerBean;
import cn.gov.bjys.onlinetrain.task.BannerTask;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dodozhou on 2017/9/25.
 */
public class BannerComHelper {

    private final static int turnTime = 6000;
    /**
     * 获取网络数据实现广告轮播图
     * @param banner
     * @param tag
     */
    public static void initNetworkBanner(final ConvenientBanner banner, String tag){
        //TODO 获取到网络数据并进行处理
        rx.Observable<String> obs;
        obs = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).
                getSpeUrlService(YSConst.UPDATE_ZIP, HomeApi.class).getHomeBannerDatas(HRetrofitNetHelper.createReqJsonBody(MapParamsHelper.getBaseParamsMap()));
        obs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String s) {
                          initBanner(banner,FastJSONParser.getBeanList(s,HomeBannerBean.class));
                    }
                });
    }

    /**
     * 加载本地banner
     * @param banner
     * @param zipResName 加载的json资源文件
     */
    public static void initZipBanner(final ConvenientBanner banner, String zipResName){
//        new BannerTask(banner, zipResName).execute();
    }

    public static void initBanner(final ConvenientBanner bannerPoster, List<HomeBannerBean> bannerList) {
//        bannerPoster.setVisibility(View.GONE);
        Log.d("dodoT", "is Main thread = " + (Thread.currentThread() == Looper.getMainLooper().getThread()));
      if(true){
          return;
      }

        if (bannerList == null) {
            bannerList = new ArrayList<>();
        }
        final List<HomeBannerBean> banners = bannerList;
//        bannerPoster.setCanLoop(true);//能否循环
        bannerPoster.setPages(
                //接口对象  加载图片
                new CBViewHolderCreator<BannerHomeHolderView>() {
                    @Override
                    public BannerHomeHolderView createHolder() {
                        return new BannerHomeHolderView();
                    }
                }, banners)
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        if (banners.size() > 1) {
            //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
            bannerPoster.setPageIndicator(new int[]{R.drawable.icon_page_indicator_normal, R.drawable.icon_page_indicator_press});
            bannerPoster.startTurning(turnTime);
            bannerPoster.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
            bannerPoster.setManualPageable(true);//设置不能手动影响
        } else {
            bannerPoster.setManualPageable(false);//设置不能手动影响
        }
    }


    /**
     *获取本地数据实现广告轮播图
     * @param banner
     * @param ress
     */
    public static void initLocationBanner(ConvenientBanner banner,int[] ress){

        ArrayList<Integer> res = new ArrayList<>();

        for(Integer i : ress){
            res.add(i);
        }

        banner.setPages(
                //接口对象  加载图片
                new CBViewHolderCreator<BannerComHelper.LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, res)
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        if (res.size() > 1) {
            //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
            banner.setPageIndicator(new int[]{R.drawable.icon_page_indicator_normal, R.drawable.icon_page_indicator_press});
            banner.startTurning(turnTime);
            banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
            banner.setManualPageable(true);//设置不能手动影响
        } else {
            banner.setManualPageable(false);//设置不能手动影响
        }
            }


    public static class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }
    }
}
