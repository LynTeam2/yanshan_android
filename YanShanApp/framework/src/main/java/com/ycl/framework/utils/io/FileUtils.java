package com.ycl.framework.utils.io;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.ycl.framework.base.FrameApplication;
import com.ycl.framework.module.GlideCatchConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("deprecation")
public class FileUtils {
    private static BitmapFactory.Options sBitmapOptions;

    static {
        sBitmapOptions = new BitmapFactory.Options();
        sBitmapOptions.inPurgeable = true; // bitmap can be purged to disk  图片能回收
        sBitmapOptions.inInputShareable = true;
    }

    /**
     * sd卡的根目录
     */
    private static String mSdRootPath = Environment.getExternalStorageDirectory()
            .getPath();
    /**
     * 手机的缓存根目录
     */
    private static String mDataRootPath = null;
    /**
     * 保存Image的目录名
     */

    private static FileUtils instance = null;

    private FileUtils(Context context) {
        mDataRootPath = context.getCacheDir() != null ? context.getCacheDir().getPath() : "";
    }

    public static FileUtils getInstance(Context context) {
        if (instance == null) {
            instance = new FileUtils(context);
        }
        return instance;
    }

    /**
     * 获取储存Image的目录
     *
     * @return String
     */
    public String getStorageDirectory() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? mSdRootPath
                : "";
    }

    /**
     * 获取Cache的目录
     *
     * @return String
     */
    public String getCacheDirectory() {
        return mDataRootPath;
    }

    /**
     * 保存Image的方法，有sd卡存储到sd卡，没有就存储到手机目录
     *
     * @param fileName 文件名
     * @param bitmap   bitmap
     * @throws IOException
     */
    public boolean savaBitmap(String fileName, Bitmap bitmap) {
        boolean saved = false;
        if (bitmap == null) {
            return false;
        }
        try {
            String path = getStorageDirectory();
            File folderFile = new File(path);
            if (!folderFile.exists()) {
                folderFile.mkdirs();
            }
            File f = new File(path + File.separator + fileName);
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            if (bitmap.getByteCount() > 10)
                saved = bitmap.compress(CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            Log.e("other", "保存失败");
        }
        return saved;
    }

    /**
     * 从手机或者sd卡获取Bitmap
     *
     * @param fileName 文件名
     * @return Bitmap
     */
    public Bitmap getBitmap(String fileName) {
        return BitmapFactory.decodeFile(getStorageDirectory() + File.separator + fileName,
                sBitmapOptions);
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName 文件名
     * @return boolean
     */
    public boolean isFileExists(String fileName) {
        return new File(getStorageDirectory() + File.separator + fileName).exists();
    }

    /**
     * 获取文件的大小
     *
     * @param fileName 文件名
     * @return long  长度
     */
    public long getFileSize(String fileName) {
        return new File(getStorageDirectory() + File.separator + fileName).length();
    }

    /**
     * 删除SD卡或者手机的缓存图片和目录
     */
    public static void deleteFile(String path) {
        File dirFile = new File(mSdRootPath + File.separator + path);
        if (!dirFile.exists()) {
            return;
        }
        if (dirFile.isDirectory()) {
            String[] children = dirFile.list();
            for (String aChildren : children) {
                deleteFolder(dirFile.getAbsolutePath() + File.separator + aChildren);
            }
        }
//        dirFile.delete();
    }

    /**
     * @Method 删除文件夹(多层)    2014-6-23
     */
    public static synchronized void deleteFolder(String path) {
        File cache = new File(path);
        if (cache.exists()) {
            if (cache.isDirectory()) {
                File[] childFiles = cache.listFiles();
                for (File childFile : childFiles) {
                    if (childFile.isDirectory()) {
//                        String directoryPath = childFile.getAbsolutePath();
                        //过滤某个文件夹
//                        if ("chat_pic".equals(directoryPath.substring(
//                                directoryPath.lastIndexOf("/") + 1, directoryPath.length())))
//                            continue;
                        deleteFolder(childFile.getAbsolutePath());
                    }
                    childFile.delete();
                }
            } else {
                cache.delete();
            }
        }
    }

    public String getFileName(String path) {
        return path.substring("/".lastIndexOf(path) + 1);
    }

    /**
     * 创建 根目录
     *
     * @param path 目录路径
     */
    public void createDirFile(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 创建文件
     *
     * @param path 文件路径
     * @return 创建的文件
     */
    public File createNewFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return file;
    }

    /**
     * 判断SD是否可用
     *
     * @return boolean 是否存在
     */
    public static boolean isSdcardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static void saveDateToFile(Activity activity, String datas, String fileName)
            throws Exception {
        @SuppressWarnings("static-access")
        FileOutputStream outStream = activity.openFileOutput(fileName, activity.MODE_PRIVATE);
        if (datas == null) {
            outStream.write(new byte[]{});
        } else {
            outStream.write(datas.getBytes());
        }
        outStream.close();
    }

    public static String readDataFromFile(Activity activity, String fileName) throws Exception {
        FileInputStream inStream = activity.openFileInput(fileName);
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            byteOutput.write(buffer, 0, len);
        }
        byte[] data = byteOutput.toByteArray();
        return new String(data);
    }

    public static void copyFile(String oldPath, String newPath) {
        try {
            int byteread;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时 
                InputStream inStream = new FileInputStream(oldPath); //读入原文件 
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
        } catch (Exception ignored) {
        }
    }

    public static void copyFile(File oldfile, String newPath) {
        try {
            int byteread;
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldfile); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
        } catch (Exception ignored) {
        }
    }

    //创建文件
    public static File getOwnCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null;
        if ("mounted".equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }

        if (appCacheDir == null || !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = context.getCacheDir();
        }

        return appCacheDir;
    }

    //是否有 写权限
    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == 0;
    }

    /**
     * 获取文件夹总大小
     *
     * @param file 文件
     * @return double x.x
     */
    public static double getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children) {
                    size += getDirSize(f);
                }
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                return (double) file.length() / 1024 / 1024;
            }
        } else {
            return 0.0;
        }
    }

    //File  To File Uri
    public static Uri getImageStreamFromExternal(File picPath) {

        Uri uri = null;
        if (picPath.exists()) {
            uri = Uri.fromFile(picPath);
        }
        return uri;
    }


}


