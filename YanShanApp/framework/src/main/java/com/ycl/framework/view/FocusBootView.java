package com.ycl.framework.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ycl.framework.R;
import com.ycl.framework.base.FrameViewPager;

/**
 * 引导页  滑动view .
 */
public class FocusBootView extends RelativeLayout {
    private FrameViewPager FocusViewPager;
    private int[] goodsImage;
    private PageIndicatorView DotView;

    private LinearLayout llBtn; // 操作btn；

    public FocusBootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.focus_boot, this);
        FocusViewPager = (FrameViewPager) findViewById(R.id.FocusViewPager);
        DotView = (PageIndicatorView) findViewById(R.id.pageView);
        DotView.setVisibility(GONE);
        FocusViewPager.setOffscreenPageLimit(2);
        initData();
        initWidget();
    }

    private void initWidget() {
        FocusViewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                ImageView img = new ImageView(getContext());
                img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                img.setScaleType(ImageView.ScaleType.FIT_XY);
                //获取资源图片
//                Glide.with(getContext()).load(goodsImage[position]).into(img);
                img.setImageResource(goodsImage[position]);

                if (position == goodsImage.length - 1 && listener != null) {
                    img.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.clickView(v, position);
                        }
                    });
                }

                container.addView(img);
                return img;
            }

            @Override
            public int getCount() {
                return goodsImage.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((ImageView) object);
            }

        });


    }

    public void setOnPageChange() {
        FocusViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (llBtn != null)
                    if (position == goodsImage.length - 1) {
                        ObjectAnimator shareAni1 = ObjectAnimator.ofFloat(llBtn, "alpha", 0, 1);    //执行单个 属性动画
                        shareAni1.setDuration(1100);
                        shareAni1.start();
                        llBtn.setVisibility(VISIBLE);
                    } else {
                        if (llBtn.isShown()) {
                            ViewCompat.setAlpha(llBtn, 0);
                            llBtn.setVisibility(GONE);
                        }
                    }

//                DotView.setCurrentPage(position % 3);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        goodsImage = new int[3];
        goodsImage[0] = R.drawable.flash_map_1;
        goodsImage[1] = R.drawable.flash_map_2;
        goodsImage[2] = R.drawable.flash_map_3;
        DotView.setTotalPage(goodsImage.length);
        DotView.setCurrentPage(0);

    }

    public void setLlBtn(LinearLayout llBtn) {
        this.llBtn = llBtn;
    }


    private OnItemViewClickerListener listener;

    public interface OnItemViewClickerListener {
        void clickView(View v, int index);
    }

    public void setListener(OnItemViewClickerListener listener) {
        this.listener = listener;
    }
}
