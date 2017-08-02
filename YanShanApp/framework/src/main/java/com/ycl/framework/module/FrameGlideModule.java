package com.ycl.framework.module;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.ViewTarget;
import com.ycl.framework.R;
import com.ycl.framework.utils.io.FileUtils;

import java.io.File;
import java.io.InputStream;

/**
 * 自定义GlideModule ( Glide框架 ) on 2015/10/20.
 */
public class FrameGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        File dir = FileUtils.getOwnCacheDirectory(context, GlideCatchConfig.GLIDE_CARCH_DIR);
        if (dir != null)
            builder.setDiskCache(new DiskLruCacheFactory(dir.getAbsolutePath(), GlideCatchConfig.GLIDE_CATCH_SIZE));  //设置 缓存目录 已经大小
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
        ViewTarget.setTagId(R.id.image_glide_tag);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(CustomImageSizeModel.class, InputStream.class,
                new CustomImageSizeModelFactory());

    }


}
