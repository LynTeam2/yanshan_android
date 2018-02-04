package cn.gov.bjys.onlinetrain.task;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.api.ZipCallBackListener;
import cn.gov.bjys.onlinetrain.utils.AssetsHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;

/**
 * Created by Administrator on 2018/1/27 0027.
 */
public class BaseAsyncTask extends AsyncTask<Void,Void,Void> {

    WeakReference<ZipCallBackListener> mListenerWeakReference = new WeakReference<ZipCallBackListener>(null);
    String rootDir =  BaseApplication.getAppContext().getFilesDir().getParent()+ File.separator + YSConst.UPDATE_ZIP;
    String rootName =  AssetsHelper.getAssetUpdateZipName(BaseApplication.getAppContext(),YSConst.UPDATE_ZIP);
    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }


    //将filePath转化成string
    public static String ReaderJson(String filePath)throws IOException {
        //对一串字符进行操作
        StringBuffer fileData = new StringBuffer();
        //
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        //缓冲区使用完必须关掉
        reader.close();
        return fileData.toString();
    }

    //将file转化成string
    public static String ReaderJsonFile(File file)throws IOException {
        //对一串字符进行操作
        StringBuffer fileData = new StringBuffer();
        //
        BufferedReader reader = new BufferedReader(new FileReader(file));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        //缓冲区使用完必须关掉
        reader.close();
        return fileData.toString();
    }

    public <T> void callback(List<T> data){
        ZipCallBackListener listener =  mListenerWeakReference.get();
        if(listener != null){
            listener.zipCallBackListener(data);
        }
    }
    public void success(){
        ZipCallBackListener listener =  mListenerWeakReference.get();
        if(listener != null){
            listener.zipCallBackSuccess();
        }
    }

    public void fail(){
        ZipCallBackListener listener =  mListenerWeakReference.get();
        if(listener != null){
            listener.zipCallBackFail();
        }
    }
}
