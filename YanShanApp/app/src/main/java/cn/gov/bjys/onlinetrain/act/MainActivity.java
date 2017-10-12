package cn.gov.bjys.onlinetrain.act;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.util.LogUtils;
import com.ycl.framework.utils.util.SelectorUtil;
import com.zls.www.statusbarutil.StatusBarUtil;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.fragment.HomeFragment;
import cn.gov.bjys.onlinetrain.fragment.OwnFragment;
import cn.gov.bjys.onlinetrain.fragment.ShopFragment;
import cn.gov.bjys.onlinetrain.fragment.UserScoreFragment;
import cn.gov.bjys.onlinetrain.utils.AssetsHelper;
import cn.gov.bjys.onlinetrain.utils.Helper;

public class MainActivity extends FrameActivity {

    private Fragment[]  mFragments;
    private ImageView[] mImgs;
    private TextView[]  mTextViews;
    //当前Fragment 的 index
    private int  mCurrentTabIndex = 0;
    @Bind(R.id.framelayout)
    FrameLayout mFrameLayout;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this,null);
    }   


    @OnClick({R.id.ll_main_page1, R.id.ll_main_page2, R.id.ll_main_page3, R.id.ll_main_page4})
    void tabClick(View view) {
        switch (view.getId()) {
            case R.id.ll_main_page1:
                fragmentChange(0);
                break;
            case R.id.ll_main_page2:
                fragmentChange(1);
                break;
            case R.id.ll_main_page3:
                fragmentChange(2);
                break;
            case R.id.ll_main_page4:
                fragmentChange(3);
                break;
        }
    }

    public final static String UPDATE_ZIP = "update";
    @Override
    public void initViews() {
        try {
            AssetsHelper.unZipAssetOneFileContains(BaseApplication.getAppContext(), UPDATE_ZIP);
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.d(e.getMessage());
        }
            mImgs = new ImageView[5];
        mImgs[0] = (ImageView) findViewById(R.id.act_main_iv_page1);
        mImgs[1] = (ImageView) findViewById(R.id.act_main_iv_page2);
        mImgs[2] = (ImageView) findViewById(R.id.act_main_iv_page3);
        mImgs[3] = (ImageView) findViewById(R.id.act_main_iv_page4);

        mImgs[0].setImageDrawable(SelectorUtil.getDrawableWithDrawa(getApplicationContext(), R.drawable.home_page, R.drawable.home_page,getResources().getColor(R.color.normal_blue)));
        mImgs[1].setImageDrawable(SelectorUtil.getDrawableWithDrawa(getApplicationContext(), R.drawable.score_page, R.drawable.score_page ,getResources().getColor(R.color.normal_blue)));
        mImgs[2].setImageDrawable(SelectorUtil.getDrawableWithDrawa(getApplicationContext(), R.drawable.shop_page, R.drawable.shop_page,getResources().getColor(R.color.normal_blue)));
        mImgs[3].setImageDrawable(SelectorUtil.getDrawableWithDrawa(getApplicationContext(), R.drawable.account_page, R.drawable.account_page,getResources().getColor(R.color.normal_blue)));
        mTextViews = new TextView[5];
        mTextViews[0] = (TextView) findViewById(R.id.act_main_tv_page1);
        mTextViews[1] = (TextView) findViewById(R.id.act_main_tv_page2);
        mTextViews[2] = (TextView) findViewById(R.id.act_main_tv_page3);
        mTextViews[3] = (TextView) findViewById(R.id.act_main_tv_page4);

        mTextViews[0].setTextColor(SelectorUtil.getColorStateListSelected(Helper.getDefaultColor(), Helper.getSelectedColor()));
        mTextViews[1].setTextColor(SelectorUtil.getColorStateListSelected(Helper.getDefaultColor(), Helper.getSelectedColor()));
        mTextViews[2].setTextColor(SelectorUtil.getColorStateListSelected(Helper.getDefaultColor(), Helper.getSelectedColor()));
        mTextViews[3].setTextColor(SelectorUtil.getColorStateListSelected(Helper.getDefaultColor(), Helper.getSelectedColor()));

    }

    @Override
    public void initData() {

        mFragments = new Fragment[]{getFragment("homepage_fragment", HomeFragment.class, R.id.framelayout),
                getFragment("user_score_fragment", UserScoreFragment.class, R.id.framelayout),
                getFragment("shop_fragment", ShopFragment.class, R.id.framelayout),
                getFragment("own_fragment", OwnFragment.class, R.id.framelayout),

        };
        if (!mFragments[0].isAdded())
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.framelayout, mFragments[0], "fg_page_Home")
//                    .add(R.id.framelayout, mFragments[3], "fg_page_own")//.add(R.id.framelayout, fragments[3], "fg_page4").hide(fragments[4]).hide(fragments[3])//.hide(fragments[1]).hide(fragments[2])
//                    .hide(mFragments[3])
                    .show(mFragments[0]).commit();

        mImgs[0].setSelected(true);
        mTextViews[0].setSelected(true);

    }

    /**
     * pager切换
     */

    public void fragmentChange(int tag) {
        if (mCurrentTabIndex != tag) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(mFragments[mCurrentTabIndex]);
            if (!mFragments[tag].isAdded()) {
                trx.add(R.id.framelayout, mFragments[tag]);
            }
            trx.show(mFragments[tag]).commitAllowingStateLoss();
        }
        mImgs[mCurrentTabIndex].setSelected(false);
        mTextViews[mCurrentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mImgs[tag].setSelected(true);
        mTextViews[tag].setSelected(true);
        mCurrentTabIndex = tag;

        //改变标题栏颜色
    }

    //会导致 fragment序列化异常
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private long mExitTime = 0;
    @Override
    public void onBackPressed() {
//        if (System.currentTimeMillis() - mExitTime < 1600) {
//            super.onBackPressed();
//        } else {
//            mExitTime = System.currentTimeMillis();
//            showToast("连续按两次返回键关闭程序");
//        }
        moveTaskToBack(true);
    }
}
