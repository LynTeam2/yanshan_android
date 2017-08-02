package com.ycl.framework.utils.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.TypedValue;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * inBitmap——在解析Bitmap时重用该Bitmap，不过必须等大的Bitmap而且inMutable须为true
 * inMutable——配置Bitmap是否可以更改，比如：在Bitmap上隔几个像素加一条线段
 * inJustDecodeBounds——为true仅返回Bitmap的宽高等属性
 * inSampleSize——须&gt;=1,表示Bitmap的压缩比例，如：inSampleSize=4，将返回一个是原始图的1/16大小的Bitmap
 * inPreferredConfig——Bitmap.Config.ARGB_8888等
 * inDither——是否抖动，默认为false
 * inPremultiplied——默认为true，一般不改变它的值
 * inDensity——Bitmap的像素密度
 * inTargetDensity——Bitmap最终的像素密度
 * inScreenDensity——当前屏幕的像素密度
 * inScaled——是否支持缩放，默认为true，当设置了这个，Bitmap将会以inTargetDensity的值进行缩放
 * inPurgeable——当存储Pixel的内存空间在系统内存不足时是否可以被回收
 * inInputShareable——inPurgeable为true情况下才生效，是否可以共享一个InputStream
 * inPreferQualityOverSpeed——为true则优先保证Bitmap质量其次是解码速度
 * outWidth——返回的Bitmap的宽
 * outHeight——返回的Bitmap的高
 * inTempStorage——解码时的临时空间，建议16*1024
 */


/**
 * 获取Bitmap by joeYu on 17/1/4.
 * 1、BitmapConfig的配置

 2、使用decodeFile、decodeResource、decodeStream进行解析Bitmap时，配置inDensity和inTargetDensity，两者应该相等,值可以等于屏幕像素密度*0.75f

 3、使用inJustDecodeBounds预判断Bitmap的大小及使用inSampleSize进行压缩

 4、对Density>240的设备进行Bitmap的适配（缩放Density）

 5、2.3版本inNativeAlloc的使用

 6、4.4以下版本inPurgeable、inInputShareable的使用

 7、Bitmap的回收
 */

public class BitmapDecodeUtil {
    private static final int DEFAULT_DENSITY = 240;
    private static final float SCALE_FACTOR = 0.75f;
    private static final Bitmap.Config DEFAULT_BITMAP_CONFIG = Bitmap.Config.RGB_565;

    private static BitmapFactory.Options getBitmapOptions(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        options.inPreferredConfig = DEFAULT_BITMAP_CONFIG;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inJustDecodeBounds = false;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            Field field = null;
            try {
                field = BitmapFactory.Options.class.getDeclaredField("inNativeAlloc");
                field.setAccessible(true);
                field.setBoolean(options, true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        int displayDensityDpi = context.getResources().getDisplayMetrics().densityDpi;
        float displayDensity = context.getResources().getDisplayMetrics().density;
        if (displayDensityDpi > DEFAULT_DENSITY && displayDensity > 1.5f) {
            int density = (int) (displayDensityDpi * SCALE_FACTOR);
            options.inDensity = density;
            options.inTargetDensity = density;
        }
        return options;
    }

    public static Bitmap decodeBitmap(Context context, int resId) {
        checkParam(context);
        return BitmapFactory.decodeResource(context.getResources(), resId, getBitmapOptions(context));
    }

    public static Bitmap decodeBitmap(Context context, String pathName) {
        checkParam(context);
        return BitmapFactory.decodeFile(pathName, getBitmapOptions(context));
    }

    public static Bitmap decodeBitmap(Context context, InputStream is) {
        checkParam(context);
        checkParam(is);
        return BitmapFactory.decodeStream(is, null, getBitmapOptions(context));
    }

    public static Bitmap compressBitmap(Context context, int resId, int maxWidth, int maxHeight) {
        checkParam(context);
        final TypedValue value = new TypedValue();
        InputStream is = null;
        try {
            is = context.getResources().openRawResource(resId, value);
            return compressBitmap(context, is, maxWidth, maxHeight);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Bitmap compressBitmap(Context context, String pathName, int maxWidth, int maxHeight) {
        checkParam(context);
        InputStream is = null;
        try {
            is = new FileInputStream(pathName);
            return compressBitmap(context, is, maxWidth, maxHeight);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Bitmap compressBitmap(Context context, InputStream is, int maxWidth, int maxHeight) {
        checkParam(context);
        checkParam(is);
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, opt);
        int height = opt.outHeight;
        int width = opt.outWidth;
        int sampleSize = computeSampleSize(width, height, maxWidth, maxHeight);
        BitmapFactory.Options options = getBitmapOptions(context);
        options.inSampleSize = sampleSize;
        return BitmapFactory.decodeStream(is, null, options);
    }

    private static int computeSampleSize(int width, int height, int maxWidth, int maxHeight) {
        int inSampleSize = 1;
        if (height > maxHeight || width > maxWidth) {
            final int heightRate = Math.round((float) height / (float) maxHeight);
            final int widthRate = Math.round((float) width / (float) maxWidth);
            inSampleSize = heightRate < widthRate ? heightRate : widthRate;
        }
        if (inSampleSize % 2 != 0) {
            inSampleSize -= 1;
        }
        return inSampleSize == 1 ? 1 : inSampleSize;
    }

    private static <T> void checkParam(T param) {
        if (param == null)
            throw new NullPointerException();
    }


    public static byte[] bmpToByteArray(Bitmap paramBitmap, boolean paramBoolean) {
        if (paramBitmap == null) return null;

        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        paramBitmap.compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream);
        try {
            byte[] bytes = localByteArrayOutputStream.toByteArray();
            return bytes;
        } catch (Exception localException) {
            localException.printStackTrace();
        } finally {
            if (paramBoolean) {
                paramBitmap.recycle();
            }
            try {
                localByteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Bitmap getBitmapByBytes(String file) {
        //对于图片的二次采样,主要得到图片的宽与高
        int width = 0;
        int height = 0;
        int sampleSize = 1; //默认缩放为1
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;  //仅仅解码边缘区域
        //如果指定了inJustDecodeBounds，decodeByteArray将返回为空
//        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        BitmapFactory.decodeFile(file, options);
        //得到宽与高
        height = options.outHeight;
        width = options.outWidth;

        //图片实际的宽与高，根据默认最大大小值，得到图片实际的缩放比例
        while ((height / sampleSize > 200)
                || (width / sampleSize > 200)) {
            sampleSize *= 2;
        }

        //不再只加载图片实际边缘
        options.inJustDecodeBounds = false;
        //并且制定缩放比例
        options.inSampleSize = sampleSize;
        return BitmapFactory.decodeFile(file, options);
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    public static Bitmap decodeBpFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }



}
