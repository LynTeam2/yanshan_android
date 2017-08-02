package com.ycl.framework.utils.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.ycl.framework.utils.io.FileUtils;
import com.ycl.framework.utils.sp.SavePreference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PhotoUtils {

    private static String IMAGE_PATH = "";
    // 相册的RequestCode
    public static final int INTENT_REQUEST_CODE_ALBUM = 0;
    // 照相的RequestCode
    public static final int INTENT_REQUEST_CODE_CAMERA = 1;
    // 裁剪照片的RequestCode
    public static final int INTENT_REQUEST_CODE_CROP = 2;
    // 滤镜图片的RequestCode
    public static final int INTENT_REQUEST_CODE_FLITER = 3;
    // 照相的RequestCode
    public static final int INTENT_REQUEST_CODE_CAMERA_HEADER = 4;

    public static final String CAMERA_FOLDER = "DCIM/Camera/";

    public static String mPhotoPath = "";   // 拍照图片路径


    /**
     * 通过手机相册获取图片
     *
     * @param activity 类
     */
    public static void selectPhoto(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, INTENT_REQUEST_CODE_ALBUM);
    }

    /**
     * 通过手机照相获取图片
     *
     * @param activity 类
     * @return 照相后图片的路径
     */
    public static String takePicture(Activity activity, String folder) {
        return takePicture(activity, folder, INTENT_REQUEST_CODE_CAMERA);
    }

    public static String takePicture(Activity activity, String folder, int requestCode) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (!"".equals(folder)) {
                File photoFile = FileUtils.getOwnCacheDirectory(activity.getApplicationContext(), File.separator
                        + folder);
                //存储路径
                IMAGE_PATH = photoFile.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(IMAGE_PATH)));
                mPhotoPath = IMAGE_PATH;
                SavePreference.save(activity.getApplicationContext(), "CameraPhoto", mPhotoPath);
            }
            activity.startActivityForResult(intent, requestCode);

        } else {
            Toast.makeText(activity, "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
        return "";
    }


    public static Bitmap getImage(String srcPath, int requestWidth, int requestHeight)
            throws Exception {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inPreferredConfig = Config.RGB_565;
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
        int srcW = newOpts.outWidth;
        int srcH = newOpts.outHeight;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        newOpts.inSampleSize = 1;
        Matrix m = new Matrix();
        if (srcW > requestWidth || srcH > requestHeight) {
            final int heightRatio = Math.round((float) srcH / (float) requestHeight);
            final int widthRatio = Math.round((float) srcW / (float) requestWidth);
            newOpts.inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        newOpts.inJustDecodeBounds = false;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        float scale = 1f;
        int rotate = readPictureDegree(srcPath);
        m.setRotate(rotate, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
        if (srcW > requestWidth || srcH > requestHeight) {
            if (srcW >= srcH) {
                scale = (float) requestWidth / (float) bitmap.getWidth();
            } else {
                scale = (float) requestHeight / (float) bitmap.getHeight();
            }
        }
        m.postScale(scale, scale);
        Bitmap bm1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m,
                true);
        return bm1;// 压缩好比例大小后再进行质量压缩
    }

    //读取相片角度
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片纠正到正确方向
     *
     * @param degree ： 图片被系统旋转的角度
     * @param bitmap ： 需纠正方向的图片
     * @return 纠向后的图片
     */
    public static Bitmap rotateBitmap(int degree, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);

        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return bm;
    }

    /**
     * @param
     * @return 获取图片宽高
     */
    public static int[] getImageSize(InputStream is) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, newOpts);
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new int[]{w, h};
    }

    /**
     * 根据宽度和长度进行缩放图片
     *
     * @param path       图片的路径
     * @param w          宽度
     * @param h          长度
     * @param retryCount oom用于调用gc
     * @return Bitmap 图片对象
     */
    public static Bitmap createBitmap(String path, int w, int h, int retryCount) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            // 这里是整个方法的关键，inJustDecodeBounds设为true时将不为图片分配内存。
            BitmapFactory.decodeFile(path, opts);
            int srcWidth = opts.outWidth;// 获取图片的原始宽度
            int srcHeight = opts.outHeight;// 获取图片原始高度
            int destWidth;
            int destHeight;
            // 缩放的比例
            double ratio;
            if (srcWidth <= w || srcHeight <= h) {
                ratio = 0.0;
                destWidth = srcWidth;
                destHeight = srcHeight;
            } else {
                ratio = (double) srcHeight / h;
                destHeight = h;
                destWidth = (int) (srcWidth / ratio);
            }
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
            newOpts.inSampleSize = (int) ratio + 1;
            // inJustDecodeBounds设为false表示把图片读进内存中
            newOpts.inJustDecodeBounds = false;
            // 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
            newOpts.outHeight = destHeight;
            newOpts.outWidth = destWidth;
            newOpts.inPreferredConfig = Config.RGB_565;
            // 获取缩放后图片
            return BitmapFactory.decodeFile(path, newOpts);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (retryCount > 0) {
                System.gc();
                return createBitmap(path, w, h, retryCount - 1);
            }
        }
        return null;
    }

    //保存水印
    public static Bitmap createWaterMarkBitmap(Bitmap src, Bitmap watermark) {
        if (src == null) {
            return null;
        }
        Bitmap newb = null;//创建一个保存水印的位图
        int w = src.getWidth();
        int h = src.getHeight();//原图片的高
        int ww = watermark.getWidth();//水印图片的宽
        int wh = watermark.getHeight();//水印图片的高
        newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);//创建一个新的和src一样大小的位图
        Canvas cv = new Canvas(newb);//创建一个同等 大小的画布

        cv.drawBitmap(src, 0, 0, null);//从坐标0,0开始把src画入画布
        cv.drawBitmap(watermark, w - ww - 25, h - wh - 25, null);//在src中画入水印
        cv.save();//保存
        cv.restore();//存储
        watermark.recycle();
        return newb;//返回带水印的位图
    }


    public static void downPhotoToSDCard(Bitmap bitmapTag, Context mContext) {
        if (bitmapTag == null) {
            return;
        }
        File dir = FileUtils.getOwnCacheDirectory(mContext, "YuHua/DownLoad");
        String newFilePath = dir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".png";

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(newFilePath);
            bitmapTag.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (Exception ignore) {
        } catch (OutOfMemoryError e) {
            Toast.makeText(mContext, "图片保存失败，请清理内存后重试！", Toast.LENGTH_SHORT).show();
            System.gc();
        } finally {
            try {
                assert fileOutputStream != null;
                fileOutputStream.flush();
                fileOutputStream.close();
                if (!bitmapTag.isRecycled())
                    bitmapTag.recycle();
            } catch (IOException ignored) {
            }
        }
        Toast.makeText(mContext, "图片已保存至" + dir.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        MediaScannerConnection.scanFile(mContext, new String[]{dir.getAbsolutePath()
        }, null, null);
    }


}
