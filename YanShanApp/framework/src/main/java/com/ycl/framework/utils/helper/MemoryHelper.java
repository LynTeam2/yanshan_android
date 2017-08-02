package com.ycl.framework.utils.helper;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.ycl.framework.view.roundedview.RoundedDrawable;
import com.ycl.framework.view.roundedview.RoundedImageView;

/**
 * 内存管理 by JoeYu on 17/6/23.
 */

public class MemoryHelper {


    public static void recycleImageViewBitMap(ImageView imageView) {
        if (imageView != null) {
            BitmapDrawable bd = (BitmapDrawable) imageView.getDrawable();
            rceycleBitmapDrawable(bd);
        }
    }

    public static void rceycleBitmapDrawable(BitmapDrawable bitmapDrawable) {
        if (bitmapDrawable != null) {
            Bitmap bitmap = bitmapDrawable.getBitmap();
            rceycleBitmap(bitmap);
        }
        bitmapDrawable = null;
    }

    public static void rceycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }
}
