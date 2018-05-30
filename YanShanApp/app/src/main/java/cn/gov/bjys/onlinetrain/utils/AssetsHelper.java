package cn.gov.bjys.onlinetrain.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.ycl.framework.utils.util.LogUtils;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.entity.DataInfo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import cn.gov.bjys.onlinetrain.BaseApplication;
import okhttp3.ResponseBody;


public class AssetsHelper {

    public static String getYSPicPath(String relativePath){

        String aimPath = BaseApplication.getAppContext().getFilesDir().getParent()+ File.separator +
                YSConst.UPDATE_ZIP+File.separator+
                AssetsHelper.getAssetUpdateZipName(BaseApplication.getAppContext(),YSConst.UPDATE_ZIP)+
                File.separator + "resource"+File.separator+
                relativePath;
        return aimPath;
    }


    /**
     * 解压assets的zip压缩文件到指定目录
     * @param context 上下文对象
     * @param assetName 压缩文件名
     * @param outputDirectory 输出目录
     * @param isReWrite 是否覆盖
     * @throws IOException
     */
    public static void unZip(Context context, String assetName,
                             String outputDirectory, boolean isReWrite) throws IOException {
        //创建解压目标目录
        File file = new File(outputDirectory);
        //如果目标目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        //打开压缩文件
        InputStream inputStream = context.getAssets().open(assetName);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        //读取一个进入点
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        //使用1Mbuffer
        byte[] buffer = new byte[1024 * 1024];
        //解压时字节计数
        int count = 0;
        //如果进入点为空说明已经遍历完所有压缩包中文件和目录
        while (zipEntry != null) {
            //如果是一个目录
            if (zipEntry.isDirectory()) {
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                //文件需要覆盖或者是文件不存在
                if(isReWrite || !file.exists()){
                    file.mkdir();
                }
            } else {
                //如果是文件
                file = new File(outputDirectory + File.separator
                        + zipEntry.getName());
                //文件需要覆盖或者文件不存在，则解压文件
                if(isReWrite || !file.exists()){
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    while ((count = zipInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                    fileOutputStream.close();
                }
            }
            //定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }


    public static String getAssetUpdateZipName(Context context,String fileName){
        AssetManager manager = context.getAssets();
        String aimFileNameAll = null;
        String aimFileName = null;
        try{
            String[] fileNames = manager
                    .list(fileName);

            if (fileNames != null && fileNames[0] != null) {
                aimFileNameAll = fileNames[0];
                aimFileName = getFileName(aimFileNameAll);
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.e(e.getMessage());
            return "";
        }
        return aimFileName;
    }


    /**
     * 解压assets目录下的文件名下的唯一Zip文件
     * @param context 上下文
     * @param fileName assets文件下的文件夹名字
     * @return   默认输出路径  data/data/传入的filename
     * @throws FileNotFoundException
     */
    public static boolean unZipAssetOneFileContains(Context context,String fileName) throws IOException {
        String outputDirectory = context.getFilesDir().getParent()+File.separator + fileName;
        AssetManager manager = context.getAssets();
        String aimFileNameAll = null;
        String aimFileName = null;
        try{
            String[] fileNames = manager
                    .list(fileName);

            if (fileNames != null && fileNames[0] != null) {
                aimFileNameAll = fileNames[0];
                aimFileName = getFileName(aimFileNameAll);
            }
       }catch (Exception e){
            e.printStackTrace();
            LogUtils.e(e.getMessage());
            return false;
        }

        if(!TextUtils.isEmpty(aimFileNameAll)) {
            String retName = getOnlyOneAssetsFile(context,fileName);
            InputStream  inputStream = manager.open(retName);
            unZipInputStream(context, inputStream, outputDirectory, true);
            return true;
        }else{
            return false;
        }
    }

    /**
     * @param context
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File un7zAssertFile(Context context,String fileName) throws IOException {
        AssetManager manager = context.getAssets();
        String aimFileNameAll = null;
        String aimFileName = null;
        try{
            String[] fileNames = manager
                    .list(fileName);

            if (fileNames != null && fileNames[0] != null) {
                aimFileNameAll = fileNames[0];
                aimFileName = getFileName(aimFileNameAll);
            }
       }catch (Exception e){
            e.printStackTrace();
            LogUtils.e(e.getMessage());
            return null;
        }

        if(!TextUtils.isEmpty(aimFileNameAll)) {
            String retName = getOnlyOneAssetsFile(context,fileName);
            InputStream  inputStream = manager.open(retName);
            File ret = null;
            try {
                ret = copeAssertFile( context,inputStream);
            }catch (Exception e){
            }
            return ret;
        }else{
            return null;
        }
    }

    public static void inputstream2File(InputStream ins,File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getOnlyOneAssetsFile(Context context, String filename){
        try {
            AssetManager am = context.getAssets();
            String[] fileNames = am.list(filename);
            if(fileNames.length > 0){
                for(String fn : fileNames){
                  //only one
                    return  getOnlyOneAssetsFile(context,filename + File.separator + fn);
                }
            }else{
                return filename;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return filename;
    }
    /**
     * get file name from the file path.
     *
     * @param filePath for example "file:///android_asset/www/index.html"
     * @return "index"
     */
    public static String getFileName(String filePath) {
        // create by RobinLin 20130729
        int startIndex = 0;
        int slashIndex = filePath.lastIndexOf("/");
        if (slashIndex > 0) {
            startIndex = slashIndex + 1;
        }
        int endIndex = filePath.length();
        int formatIndex = filePath.lastIndexOf(".");
        if (formatIndex > 0 && formatIndex > startIndex) {
            endIndex = formatIndex;
        }
        String subStr = filePath.substring(startIndex, endIndex);
        return subStr;
    }



    /**
     * 解压assets的zip压缩文件到指定目录
     * @param context 上下文对象
     * @param inputStream asset指定文件夹的输入源
     * @param outputDirectory 输出目录
     * @param isReWrite 是否覆盖
     * @throws IOException
     */
    public static void unZipInputStream(Context context, InputStream inputStream,
                             String outputDirectory, boolean isReWrite) throws IOException {
        //创建解压目标目录
        File file = new File(outputDirectory);
        //如果目标目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        //打开压缩文件
        //InputStream inputStream = context.getAssets().open(assetName);
        //直接使用输入流
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        //读取一个进入点
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        //使用1Mbuffer
        byte[] buffer = new byte[1024 * 1024];
        //解压时字节计数
        int count = 0;
        //如果进入点为空说明已经遍历完所有压缩包中文件和目录
        while (zipEntry != null) {
            //如果是一个目录
            if (zipEntry.isDirectory()) {
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                //文件需要覆盖或者是文件不存在
                if(isReWrite || !file.exists()){
                    file.mkdir();
                }
            } else {
                //如果是文件
                file = new File(outputDirectory + File.separator
                        + zipEntry.getName());
                //文件需要覆盖或者文件不存在，则解压文件
                if(isReWrite || !file.exists()){
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    while ((count = zipInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                    fileOutputStream.close();
                }
            }
            //定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }






    public static  void saveFile(ResponseBody body) {
        String destFileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        String destFileName ="upgrade.7z";
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);

            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            //onCompleted();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                Log.e("saveFile", e.getMessage());
            }
        }
    }


    public static File copeAssertFile(Context context,InputStream is) throws Exception {
        File file = null;
//        String destFileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        String destFileDir = getDiskCacheDir(context,YSConst.UPDATE_ZIP);
        String destFileName ="upgrade.7z";
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            File dir = new File(destFileDir);
            if (!dir.exists()) {
               boolean isCreate = dir.mkdirs();
                if(!isCreate){
                    throw new Exception("创建文件目录失败");
                }
            }
            file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            //onCompleted();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                Log.e("saveFile", e.getMessage());
            }
        }


        return file;
    }

    /**
     *获取7z文件 上一层 文件地址
     * @param context
     * @param uniqueName
     * @return
     */
    public static String getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath + File.separator + uniqueName;
    }


    /**
     * 解压缩
     * 将zipFile文件解压到folderPath目录下.
     * @param zipFile zip文件
     * @param folderPath 解压到的地址
     * @throws IOException
     */
    public static void upZipFile(File zipFile, String folderPath) throws IOException {
        ZipFile zfile = new ZipFile(zipFile);
        Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            ze = (ZipEntry) zList.nextElement();
            if (ze.isDirectory()) {
                Log.d("upZipFile", "ze.getName() = " + ze.getName());
                String dirstr = folderPath + ze.getName();
                dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                Log.d("upZipFile", "str = " + dirstr);
                File f = new File(dirstr);
                f.mkdir();
                continue;
            }
            Log.d("upZipFile", "ze.getName() = " + ze.getName());
            OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath, ze.getName())));
            InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
            int readLen = 0;
            while ((readLen = is.read(buf, 0, 1024)) != -1) {
                os.write(buf, 0, readLen);
            }
            is.close();
            os.close();
        }
        zfile.close();
    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     * @param baseDir     指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */
    public static File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        String substr = null;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                try {
                    substr = new String(substr.getBytes("8859_1"), "GB2312");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ret = new File(ret, substr);

            }
            Log.d("getRealFileName", "1ret = " + ret);
            if (!ret.exists())
                ret.mkdirs();
            substr = dirs[dirs.length - 1];
            try {
                substr = new String(substr.getBytes("8859_1"), "GB2312");
                Log.d("getRealFileName", "substr = " + substr);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ret = new File(ret, substr);
            Log.d("getRealFileName", "2ret = " + ret);
            return ret;
        }
        return ret;
    }

}
