package cn.gov.bjys.onlinetrain.task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.utils.AssetsHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;

public class UnZipTask extends AsyncTask<Integer, Integer, Boolean> {

    private boolean isAssetUnZip;

    private String zipPath;

    private String aimPath;

    public UnZipTask(boolean isAssetUnZip, String zipPath, String aimPath){
        this.isAssetUnZip = isAssetUnZip;
        this.zipPath = zipPath;
        this.aimPath = aimPath;
    }

    /**
     * 这里的Integer参数对应AsyncTask中的第一个参数
     * 这里的String返回值对应AsyncTask的第三个参数
     * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
     * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
     */
    @Override
    protected Boolean doInBackground(Integer... params) {
       if(isAssetUnZip) {
           //asset/update/目标文件  解压过程
           if (isUnZipOk()) {
               return true;//如果已经解压好了，直接返回true
           }
           boolean unZipOk = false;
           try {
               unZipOk = AssetsHelper.unZipAssetOneFileContains(BaseApplication.getAppContext(), YSConst.UPDATE_ZIP);
           } catch (Exception e) {
           }
           return unZipOk;
       }else{
            //根据zipPath和aimPath解压
           File file = new File(zipPath);
           InputStream is = null;
           try {
               AssetsHelper.upZipFile(file,aimPath);
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
           return true;
       }
    }

    /**
     * 是否已经解压了asset/update/目标文件
     * @return
     */
    public boolean isUnZipOk(){
        String aimDir = BaseApplication.getAppContext().getFilesDir().getParent()+ File.separator + YSConst.UPDATE_ZIP;
        String zipName = AssetsHelper.getAssetUpdateZipName(BaseApplication.getAppContext(),YSConst.UPDATE_ZIP);
        File aimFiles = new File(aimDir);
        if(aimFiles.exists()){
            String[] aimNames = aimFiles.list();
            for(String aimName:aimNames){
                aimName.contains(zipName);
                return true;
            }
        }
        return false;
    }


    /**
     * 这里的Intege参数对应AsyncTask中的第二个参数
     * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
     * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
     */
    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
    @Override
    protected void onPreExecute() {
    //开始执行异步线程

    }

    /**
     * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
     * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
     */
    @Override
    protected void onPostExecute(Boolean result) {
        //异步操作执行结束

    }

}
