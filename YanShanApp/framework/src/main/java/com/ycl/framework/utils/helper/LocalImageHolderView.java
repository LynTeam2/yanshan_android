package com.ycl.framework.utils.helper;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.ycl.framework.R;
import com.ycl.framework.bean.BannerBean;
import com.ycl.framework.utils.util.GlideProxy;

/**
 * 自定义Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。例子
 * Created by joeYu .
 */

public class LocalImageHolderView implements Holder<BannerBean> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, BannerBean data) {
        GlideProxy.loadImgForUrlPlaceHolder(imageView,data.getImg_path2(), R.drawable.icon_loading_teacher);

    }
}
