package cn.gov.bjys.onlinetrain.task;

import android.os.AsyncTask;
import android.util.Log;

import com.hzy.lib7z.ExtractCallback;
import com.hzy.lib7z.Z7Extractor;

import java.io.File;

import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.utils.AssetsHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;

/**
 * Created by Administrator on 2018/5/26 0026.
 */
public class Un7zTask extends AsyncTask<Integer, Integer, Boolean> {

    private boolean isAssetUnZip;

    private String zipPath;

    private String aimPath;

    public Un7zTask(boolean isAssetUnZip, String zipPath, String aimPath){
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
            if (isUn7zOk()) {
                return true;//如果已经解压好了，直接返回true
            }
            boolean un7zOk = false;
            try {
//              un7zOk = AssetsHelper.unZipAssetOneFileContains(BaseApplication.getAppContext(), YSConst.UPDATE_ZIP);
                un7zOk = Z7Extractor.extractFile(AssetsHelper.un7zAssertFile(BaseApplication.getAppContext(), YSConst.UPDATE_ZIP).getAbsolutePath(),
                        BaseApplication.getAppContext().getFilesDir().getParent() + File.separator + YSConst.UPDATE_ZIP,
                        null
                );
            } catch (Exception e) {
            }
            return un7zOk;
        }else{
            //根据zipPath和aimPath解压
         /*   File file = new File(zipPath);
            InputStream is = null;
            try {
                Log.d("dodoT","zipPath = " +zipPath+ "\n   aimPath =" + aimPath);
                AssetsHelper.upZipFile(file,aimPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            Z7Extractor.extractFile(zipPath, aimPath, new ExtractCallback() {
                @Override
                public void onProgress(String name, long size) {
                    Log.d("dodo", "onProgress name =" +name+"   size =" + size);
                }

                @Override
                public void onError(int errorCode, String message) {
                    Log.d("dodo", "onError errorCode =" +errorCode+"   message =" + message);
                }
            });
            return true;
        }
    }

    /**
     * 是否已经解压了asset/update/目标文件
     * @return
     */
    public boolean isUn7zOk(){
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