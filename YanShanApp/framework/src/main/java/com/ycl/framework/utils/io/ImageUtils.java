package com.ycl.framework.utils.io;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import com.ycl.framework.base.FrameApplication;
import com.ycl.framework.module.GlideCatchConfig;
import com.ycl.framework.utils.util.PhotoUtils;
import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.utils.util.YisLoger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    /**
     * 渐变显示图片
     *
     * @param imageView 宿主imageView
     * @param bitmap    视图对象
     */
    @SuppressWarnings("deprecation")
    public static void setImageBitmap(Context context, ImageView imageView,
                                      Bitmap bitmap) {
        // Use TransitionDrawable to fade in.
        final TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                new ColorDrawable(Color.TRANSPARENT),
                new BitmapDrawable(context.getResources(), bitmap)});
        // noinspection deprecation
        // imageView.setBackgroundDrawable(imageView.getDrawable());
        imageView.setImageDrawable(td);
        td.startTransition(200);
    }

    /**
     * 保存图片
     *
     * @param filePath 文件路径+文件名
     * @throws IOException
     * @ param conent
     * 文件内容
     */
    public static void saveAsJPEG(Bitmap bitmap, String filePath)
            throws IOException {
        FileOutputStream fos = null;

        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bitmap.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 保存图片
     *
     * @param filePath 文件路径+文件名
     * @throws IOException
     * @ param content
     * 文件内容
     */
    public static void saveAsPNG(Bitmap bitmap, String filePath)
            throws IOException {
        FileOutputStream fos = null;
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 100, fos);
            fos.flush();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 保存图片到SD卡
     * LruCache<String ,Bitmap >
     *
     * @return String 路径
     */
    public static String savePhotoToSDCard(String picPath, Context mContext) {
        String IMAGE_PATH = Environment.getExternalStorageDirectory()
                .getPath() + File.separator
                + "Yuhua/temp";
        FileUtils.getInstance(mContext).createDirFile(IMAGE_PATH);
        String newFilePath = IMAGE_PATH + File.separator + System.currentTimeMillis() + ".png";

        //初始化  压缩 参数
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        opts.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(picPath, opts);// 此时返回bm为空
        int srcW = opts.outWidth;
        int srcH = opts.outHeight;
        int comressSzie;
        //图片过小不进行压缩
        opts.inSampleSize = 1;
        if (srcW < 1000f && srcH < 1000f) {
            return picPath;
        } else if (srcW < 1200f && srcH < 1200f)
            comressSzie = 90;
        else if (srcW < 1400f && srcH < 1400f)
            comressSzie = 80;
        else if (srcW < 1600f && srcH < 1600f) {
            comressSzie = 70;
        } else if (srcW < 1900f && srcH < 1900f) {
            opts.inSampleSize = 2;
            comressSzie = 80;
        } else if (srcW < 2200f && srcH < 2200f) {
            opts.inSampleSize = 2;
            comressSzie = 60;
        } else {
            opts.inSampleSize = (int) Math.pow(2, (int) Math.round(Math.log(1000f / (double) Math.max(srcH, srcW)) / Math.log(0.5)));
            comressSzie = 50;
        }

        Bitmap bitmap;
        bitmap = LruCacheUtils.getInstance().getBitmapFromMemCache(picPath);
        if (bitmap == null) {
            opts.inPreferredConfig = Config.RGB_565;
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            opts.inJustDecodeBounds = false;
            opts.inDither = false;
            opts.inPurgeable = true;
            opts.inInputShareable = true;
            opts.inTempStorage = new byte[12 * 1024];
            File file = new File(picPath);
            FileInputStream fs = null;
            try {
                fs = new FileInputStream(file);
                bitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, opts);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fs != null)
                        fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//        Bitmap bitmap = BitmapFactory.decode(picPath, opts);
            if (bitmap == null) {
                return null;
            }
            int degreePic = PhotoUtils.readPictureDegree(picPath);
            if (degreePic > 0) {
                bitmap = PhotoUtils.rotateBitmap(degreePic, bitmap);
            }
            LruCacheUtils.getInstance().putBitmapToMemoryCache(picPath, bitmap);
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(newFilePath);
            bitmap.compress(CompressFormat.JPEG, comressSzie, fileOutputStream);
        } catch (Exception e) {
            return null;
        } catch (OutOfMemoryError e) {
            Toast.makeText(mContext.getApplicationContext(), "图片压缩异常，请清理内存后重试！", Toast.LENGTH_SHORT).show();
            return null;
        } finally {
            try {
                assert fileOutputStream != null;
                fileOutputStream.flush();
                fileOutputStream.close();
//                if (!bitmap.isRecycled())
//                    bitmap.recycle();
            } catch (IOException e) {
                return null;
            }
        }
        return newFilePath;
    }

    //质量压缩
    private Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    //使用Bitmap加Matrix来缩放()
    public static Bitmap resizeImage(Bitmap BitmapOrg, int w, int h) {
        Bitmap resizedBitmap = null;
        try {
            int width = BitmapOrg.getWidth();
            int height = BitmapOrg.getHeight();
            float scaleWidth = ((float) w) / width;
            float scaleHeight = ((float) h) / height;

            float min = scaleWidth > scaleHeight ? scaleHeight : scaleWidth;//等比
            Matrix matrix = new Matrix();
            matrix.postScale(min, min);
            // matrix.postRotate(45); 调整旋转角
            resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                    height, matrix, true);
        } catch (Exception e) {
            YisLoger.debug(e.toString());
        }
        return resizedBitmap;
    }


    /**
     * 保存图片到SD卡
     * LruCache<String ,Bitmap >
     *
     * @return String 路径
     */
    public static String saveHeadPhotoToSDCard(String picPath) {
        File fileTemp = FileUtils.getOwnCacheDirectory(FrameApplication.getFrameContext(), GlideCatchConfig.PHOTO_TEMP_DIR);
        String newFilePath = fileTemp.getAbsolutePath() + File.separator  + "CropCache";

        //初始化  压缩 参数
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        opts.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(picPath, opts);// 此时返回bm为空
        int srcW = opts.outWidth;
        int srcH = opts.outHeight;

        int be = 1;
        float hh = 1100f;
        float ww = 1100f;
        //长宽比例 严重失调的 图 会有bug
        be = (int) Math.max(srcH / hh, srcW / ww);
        if (be <= 1)
            be = 1;
        opts.inSampleSize = be;

        if (opts.inSampleSize == 1) {
            return picPath;
        }

        Bitmap bitmap;
        bitmap = LruCacheUtils.getInstance().getBitmapFromMemCache(picPath);
        if (bitmap == null) {
            opts.inPreferredConfig = Config.RGB_565;
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            opts.inJustDecodeBounds = false;
            opts.inDither = false;
            opts.inPurgeable = true;
            opts.inInputShareable = true;
            opts.inTempStorage = new byte[12 * 1024];
            File file = new File(picPath);
            FileInputStream fs = null;
            try {
                fs = new FileInputStream(file);
                bitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, opts);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fs != null)
                        fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//        Bitmap bitmap = BitmapFactory.decode(picPath, opts);
            if (bitmap == null) {
                return null;
            }
            int degreePic = PhotoUtils.readPictureDegree(picPath);
            if (degreePic > 0) {
                bitmap = PhotoUtils.rotateBitmap(degreePic, bitmap);
            }
            LruCacheUtils.getInstance().putBitmapToMemoryCache(picPath, bitmap);
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(newFilePath);
            bitmap.compress(CompressFormat.JPEG, 60, fileOutputStream);
        } catch (Exception e) {
            return null;
        } catch (OutOfMemoryError e) {
            ToastUtil.showToast("图片压缩异常，请清理内存后重试！");
            return null;
        } finally {
            try {
                assert fileOutputStream != null;
                fileOutputStream.flush();
                fileOutputStream.close();
//                if (!bitmap.isRecycled())
//                    bitmap.recycle();
            } catch (IOException e) {
                return null;
            }
        }
        return newFilePath;
    }

}
