package com.ycl.framework.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 订单管理Fragment的 PageAdapter on 2015/6/17.
 */

public class FrameFragmentPagerAdapter extends FragmentPagerAdapter {

    /**
     * The m fragment[].
     */
    protected Fragment[] mFragmentList = null;

    public FrameFragmentPagerAdapter(FragmentManager mFragmentManager, Fragment[] fragmentList) {
        super(mFragmentManager);
        mFragmentList = fragmentList;
    }

    public void setmFragmentList(Fragment[] mFragmentList) {
        this.mFragmentList = mFragmentList;
    }

    /**
     * sliding_Strip 滑动剥离 title
     */
    protected String[] TITLES = null;

    public void setTITLES(String[] TITLES) {
        this.TITLES = TITLES;
    }

    /**
     * 重写sliding_Strip 滑动剥离 所需的 title
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if (TITLES != null)
            return TITLES[position];
        else
            return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.length;
    }

    /**
     * 描述：获取索引位置的Fragment.
     *   String name = makeFragmentName(container.getId(), itemId);   相同id  会导致viewpager中的Fg不显示
     */
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position < mFragmentList.length) {
            fragment = mFragmentList[position];
        } else {
            fragment = mFragmentList[0];
        }
        return fragment;
    }

}