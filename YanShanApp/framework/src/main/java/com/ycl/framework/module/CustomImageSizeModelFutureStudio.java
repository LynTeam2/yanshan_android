package com.ycl.framework.module;

/**
 * 自定义Size Url 尺寸Model on 2016/3/8.
 * 使用方式
 *
 * CustomImageSizeModelFutureStudio customModel = ViewHolderUtil.getCustomModel(Const.PHOTO_HOST + fileBean.getFilePath(), 0);
 *  if (width > 900)
 *  customModel.setInterlaceAble(true);
 *  GlideProxy.loadImgWithModelMix(imgs, customModel, R.drawable.icon_dynamic_img_loading);
 *
 */
public class CustomImageSizeModelFutureStudio implements CustomImageSizeModel {
    private String baseImageUrl;
    private int typeUrl;
    private boolean interlaceAble = false;  //是否压缩

    public CustomImageSizeModelFutureStudio() {
    }

    public CustomImageSizeModelFutureStudio(String baseImageUrl, int type) {
        this.baseImageUrl = baseImageUrl;
        this.typeUrl = type;
    }

    public void setBaseImageUrl(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }

    public void setTypeUrl(int typeUrl) {
        this.typeUrl = typeUrl;
    }

    public void setInterlaceAble(boolean interlaceAble) {
        this.interlaceAble = interlaceAble;
    }

    @Override
    public String requestCustomSizeUrl(int width, int height) {
        // previous way: we directly accessed the images
        // https://futurestud.io/images/logo.png

        // new way, server could handle additional parameter and provide the image in a specific size
        // in this case, the server would serve the image in 400x300 pixel size
        // https://futurestud.io/images/logo.png?w=400&h=300
        String urlPattern = "";
        switch (typeUrl) {
            case 0:  //裁切
                urlPattern = "?imageView2/5/w/" + width + "/h/" + height;
                break;
            case 1://缩放
                urlPattern = "?imageView2/0/w/" + width + "/h/" + height;
                break;
            case 2:// 加载二倍图
                urlPattern = "?imageView2/0/w/" + (width * 2) + "/h/" + (height * 2);
                break;
            case 3:// 加载三倍图
                urlPattern = "?imageView2/0/w/" + (width * 3) + "/h/" + (height * 3);
                break;
        }
        if (interlaceAble) {
            int width2 = (int) (0.65 * width); //width  = height
            urlPattern = "?imageView2/1/w/" + width2 + "/h/" + width2;
        }

        return baseImageUrl + urlPattern;
    }
}
