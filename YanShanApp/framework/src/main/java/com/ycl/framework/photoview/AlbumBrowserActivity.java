package com.ycl.framework.photoview;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ycl.framework.R;
import com.ycl.framework.albums.AlbumActivity;
import com.ycl.framework.albums.MyAdapter;
import com.ycl.framework.base.AlbumEntityEBus;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.base.FrameActivityStack;
import com.ycl.framework.photoview.transformer.DepthPageTransformer;
import com.ycl.framework.utils.util.GlideProxy;
import com.ycl.framework.utils.util.SelectorUtil;
import com.ycl.framework.view.TitleHeaderView;

import java.util.List;

import org.greenrobot.eventbus.EventBus;

/**
 * 相册图片选择预览 by yuchaoliang on 16/5/30.
 */
public class AlbumBrowserActivity extends FrameActivity {

    private TextView tvSelectedNum; //已选择数量
    private TitleHeaderView titleView;
    private ScrollViewPager mSvpPager;

    private AlbumBrowserAdapter mAdapter;
    private int mPosition;
    List<String> imagesList;

    private boolean isRefresh = false;
    private int topicId;
    private String tagName;

    public static final String PHOTO_BROWSER = "photos_browser";
    public static final String PHOTO_INDEX = "photos_browser_index";

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_album_browser);
    }


    @Override
    public void initViews() {
        mSvpPager = (ScrollViewPager) findViewById(R.id.imagebrowser_svp_pager);
        titleView = (TitleHeaderView) findViewById(R.id.frame_title_view);
        tvSelectedNum = (TextView) findViewById(R.id.tv_album_browser_num);
        titleView.findViewById(com.ycl.framework.R.id.ll_title_root_parent).setBackground(null);

        if (MyAdapter.mSelectedImage.size() == 0) {
            tvSelectedNum.setText("确定");
        } else {
            tvSelectedNum.setText("确定 （" + MyAdapter.mSelectedImage.size() + "张)");
        }

        titleView.addRightSelectedImg(SelectorUtil.getDrawableWithSelected(getApplicationContext(), R.drawable.icon_browser_unselect, R.drawable.icon_browser_select), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRefresh = true;
                String currentPath = imagesList.get(mPosition);
                if (MyAdapter.mSelectedImage.contains(currentPath)) {
                    titleView.findViewById(R.id.title_view_right_id).setSelected(false);
                    MyAdapter.mSelectedImage.remove(currentPath);
                } else {
                    titleView.findViewById(R.id.title_view_right_id).setSelected(true);
                    MyAdapter.mSelectedImage.add(currentPath);
                }
                if (MyAdapter.mSelectedImage.size() == 0) {
                    tvSelectedNum.setText("确定");
                } else {
                    tvSelectedNum.setText("确定 ( " + MyAdapter.mSelectedImage.size() + "张 )");
                }

            }
        });

        mSvpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                titleView.setTitleText((mPosition + 1) + " / " + imagesList.size());
                if (MyAdapter.mSelectedImage.contains(imagesList.get(mPosition))) {
                    titleView.findViewById(R.id.title_view_right_id).setSelected(true);
                } else
                    titleView.findViewById(R.id.title_view_right_id).setSelected(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvSelectedNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyAdapter.mSelectedImage != null && MyAdapter.mSelectedImage.size() > 0) {
                    isRefresh = false;
                    AlbumEntityEBus event = new AlbumEntityEBus<>(MyAdapter.mSelectedImage);
                    event.setTagName(tagName);
                    event.setTopicId(topicId);
                    EventBus.getDefault().post(event);
                    FrameActivityStack.create().finishActivity(AlbumActivity.class);
                    finish();
                }
            }
        });
    }

    @Override
    public void initData() {
        imagesList = getIntent().getStringArrayListExtra(PHOTO_BROWSER);
        mPosition = getIntent().getIntExtra(PHOTO_INDEX, 0);

        topicId = getIntent().getIntExtra("topicId", 0);
        tagName = getIntent().getStringExtra("tagName");

        titleView.setTitleText((mPosition + 1) + " / " + imagesList.size());

        titleView.setBackground(null);
        mAdapter = new AlbumBrowserAdapter();
        mSvpPager.setAdapter(mAdapter);
        mSvpPager.setPageTransformer(true, new DepthPageTransformer());
        mSvpPager.setCurrentItem(mPosition, false);
        if(mPosition == 0)
        {

        }
    }


    private class AlbumBrowserAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return imagesList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            String path = imagesList.get(position);
            GlideProxy.loadImgForUrl(photoView, "file://" + path);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void finish() {
        if (isRefresh)
            setResult(1001);
        super.finish();
    }
}
