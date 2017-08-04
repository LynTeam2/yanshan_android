package cn.gov.bjys.onlinetrain.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.string.DensityUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.text.DecimalFormat;

import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;

/**
 * helper类 by joeYu .
 */

public class Helper {

    //获取默认 颜色
    public static int getSelectedColor() {
        return ContextCompat.getColor(BaseApplication.getAppContext(), R.color.color_select_main);
    }

    //获取默认 颜色
    public static int getDefaultColor() {
        return ContextCompat.getColor(BaseApplication.getAppContext(), R.color.color_gray_main);
    }

    public static int getResColor(int ids) {
        return ContextCompat.getColor(BaseApplication.getAppContext(), ids);
    }

    //获取 适配 auto的px值
    public static int getAutoPx(int oldPx) {
        return (int) (DensityUtils.getScreenW(BaseApplication.getAppContext()) / 1.0f / 1080 * oldPx);
    }


    //
    public static void initIndicatorView(final ViewPager mViewPager, MagicIndicator tabLayout) {
        CommonNavigator commonNavigator = new CommonNavigator(BaseApplication.getAppContext());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(0xff666666);
                colorTransitionPagerTitleView.setSelectedColor(0xffcc0f2f);
                colorTransitionPagerTitleView.setText(mViewPager.getAdapter().getPageTitle(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.getPaint().setColor(0xffcc0f2f);
                return indicator;
            }
        });
        tabLayout.setNavigator(commonNavigator);
        ViewPagerHelper.bind(tabLayout, mViewPager);
    }

    public static void initIndicatorViewDoo(final ViewPager mViewPager, MagicIndicator tabLayout) {
        CommonNavigator commonNavigator = new CommonNavigator(BaseApplication.getAppContext());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(0xff666666);
                colorTransitionPagerTitleView.setSelectedColor(0xffcc0f2f);
                colorTransitionPagerTitleView.setText(mViewPager.getAdapter().getPageTitle(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.getPaint().setColor(0xffcc0f2f);
                return indicator;
            }
        });
        tabLayout.setNavigator(commonNavigator);
        ViewPagerHelper.bind(tabLayout, mViewPager);
    }


    public static void initIndicatorViewAccountBill(final ViewPager mViewPager, MagicIndicator tabLayout) {
        CommonNavigator commonNavigator = new CommonNavigator(BaseApplication.getAppContext());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(0xff666666);
                colorTransitionPagerTitleView.setSelectedColor(0xffcc0f2f);
                colorTransitionPagerTitleView.setText(mViewPager.getAdapter().getPageTitle(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.getPaint().setColor(0xffcc0f2f);
                return indicator;
            }
        });
        tabLayout.setNavigator(commonNavigator);
        ViewPagerHelper.bind(tabLayout, mViewPager);
    }

















    public static String formatNums(String str) {
        if (str == null)
            return "";
        float a = Float.valueOf(str);
        if (a > 10000) {
            float num = a / 10000;
            return new DecimalFormat("0.00").format(num) + "万";
        }
        return String.format("%.0f", a);
    }


}
