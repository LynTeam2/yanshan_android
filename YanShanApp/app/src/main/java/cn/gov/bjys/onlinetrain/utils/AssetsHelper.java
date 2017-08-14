package cn.gov.bjys.onlinetrain.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.ycl.framework.utils.util.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by dodozhou on 2017/8/8.
 */
public class AssetsHelper {

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
//            String appDirPath = context.getFilesDir().getParent();
//            String dirPath = appDirPath + File.separator
//                    + fileName;
//            String aimPath = dirPath + File.separator + aimFileName;
//            File file = new File(aimPath);
//           if(!file.exists()){
//               file.mkdirs();
//           }
            String retName = getOnlyOneAssetsFile(context,fileName);
            InputStream  inputStream = manager.open(retName);
            unZipInputStream(context, inputStream, outputDirectory, true);
            return true;
        }else{
            return false;
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
}
