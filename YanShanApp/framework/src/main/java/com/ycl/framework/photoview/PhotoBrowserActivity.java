package com.ycl.framework.photoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.ycl.framework.R;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.photoview.transformer.DepthPageTransformer;
import com.ycl.framework.photoview.transformer.ImageBean;
import com.ycl.framework.utils.util.GlideProxy;
import com.ycl.framework.utils.util.PhotoUtils;

import java.util.List;

/**
 * 图片预览 activity  2015/7/1.
 */
public class PhotoBrowserActivity extends FrameActivity {

    private ScrollViewPager mSvpPager;
    private TextView mPtvPage;

    private ImageBrowserAdapter mAdapter;
    private int mPosition;
    List<ImageBean> imagesList;

    public static final String PHOTO_BROWSER = "photos_browser";
    public static final String PHOTO_INDEX = "photos_browser_index";

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_photo_browser);
    }


    @Override
    public void initViews() {
        mSvpPager = (ScrollViewPager) findViewById(R.id.imagebrowser_svp_pager);
        mPtvPage = (TextView) findViewById(R.id.imagebrowser_ptv_page);

        mSvpPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                mPtvPage.setText((mPosition % imagesList.size()) + 1 + "/" + imagesList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.iv_image_browser_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter.getPrimaryItem().getDrawable() == null)
                    return;
                v.setEnabled(false);
                Bitmap bpMark = PhotoUtils.createWaterMarkBitmap(((GlideBitmapDrawable) mAdapter.getPrimaryItem().getDrawable()).getBitmap(), BitmapFactory.decodeResource(getResources(), R.drawable.icon_photo_water_mark));
                PhotoUtils.downPhotoToSDCard(bpMark, getApplicationContext());
                v.setEnabled(true);
            }
        });
    }

    @Override
    protected void initStatusBar() {
    }

    @Override
    public void initData() {
        imagesList = getIntent().getParcelableArrayListExtra(PHOTO_BROWSER);
        mPosition = getIntent().getIntExtra(PHOTO_INDEX, 0);

        mPtvPage.setText((mPosition + 1) + "/" + imagesList.size());

        mAdapter = new ImageBrowserAdapter(this, imagesList);
        mSvpPager.setAdapter(mAdapter);
        mSvpPager.setPageTransformer(true, new DepthPageTransformer());
        mSvpPager.setCurrentItem(mPosition, false);
    }


    private class ImageBrowserAdapter extends PagerAdapter {

        Context context;

        //操作当前view
        private PhotoView currentView;

        public ImageBrowserAdapter(Context context, List<ImageBean> photos) {
            this.context = context;
        }

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
            String path = imagesList.get(position).getPath();
            if (!TextUtils.isEmpty(path) && path.startsWith("http")) {
                GlideProxy.loadImgForUrl(photoView, path);
            } else {
                GlideProxy.loadImgForUrl(photoView, "http://source.icangke.com/" + path + "?interlace/1");
            }
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            currentView = (PhotoView) object;
        }

        public PhotoView getPrimaryItem() {
            return currentView;
        }
    }


}
