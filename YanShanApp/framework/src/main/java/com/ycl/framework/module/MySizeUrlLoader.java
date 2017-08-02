package com.ycl.framework.module;

import android.content.Context;

import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

public class MySizeUrlLoader extends BaseGlideUrlLoader<CustomImageSizeModel> {
    public MySizeUrlLoader(Context context) {
        super(context);
    }

    @Override
    protected String getUrl(CustomImageSizeModel model, int width, int height) {
        // Construct the url for the correct size here.
        return model.requestCustomSizeUrl(width, height);
    }


}

