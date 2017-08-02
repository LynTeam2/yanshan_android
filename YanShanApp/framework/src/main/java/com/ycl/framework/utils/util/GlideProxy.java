package com.ycl.framework.utils.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ycl.framework.module.CustomImageSizeModel;

/**
 * Glide代理 by yuchaoliang on 16/4/19.
 */
public class GlideProxy {


    public static void loadImgForRes(ImageView mImageView, int ids) {
        Glide.with(mImageView.getContext().getApplicationContext()).load(ids).into(mImageView);
    }

    //自定义模式  加载
    public static void loadImgWithModel(ImageView imgView, CustomImageSizeModel customModel) {
        Glide.with(imgView.getContext().getApplicationContext()).load(customModel).into(imgView);
    }

    //  url加载
    public static void loadImgForUrl(ImageView mImageView, String url) {
        Glide.with(mImageView.getContext().getApplicationContext()).load(url).into(mImageView);
    }


    //  url加载  静态
    public static void loadImgUrl(ImageView mImageView, String url) {
        Glide.with(mImageView.getContext().getApplicationContext()).load(url).crossFade().into(mImageView);
    }

    //带预加载图  url加载
    public static void loadImgForUrlPlaceHolder(ImageView mImageView, String url, int ids) {
        Glide.with(mImageView.getContext().getApplicationContext()).load(url).placeholder(ids).into(mImageView);
    }
    //带预加载图  url加载 禁止动画
    public static void loadImgForUrlPlaceHolderDontAnimate(ImageView mImageView, String url, int ids) {
        Glide.with(mImageView.getContext().getApplicationContext()).load(url).placeholder(ids).dontAnimate().into(mImageView);
    }
    //带预加载图  url加载
    public static void loadImgForUrlPlaceFadeHolder(ImageView mImageView, String url, int ids) {
        Glide.with(mImageView.getContext()).load(url).asBitmap().placeholder(ids)
                .into(mImageView);
    }

    //自定义模式 (预加载等) 加载
    public static void loadImgWithModelMix(ImageView imgView, CustomImageSizeModel customModel,int idsRes) {
        Glide.with(imgView.getContext().getApplicationContext()).load(customModel).asBitmap().placeholder(idsRes).into(imgView);
    }


    //url 加载   animation动画
    public static void loadUrlAnimation(ImageView imgView, String url) {
        Glide.with(imgView.getContext().getApplicationContext()).load(url).crossFade().into(imgView);
    }

}
