package cn.gov.bjys.onlinetrain.task;

import android.os.AsyncTask;

import com.ycl.framework.db.business.QuestionInfoBusiness;
import com.ycl.framework.db.entity.QuestionBean;
import com.ycl.framework.utils.util.FastJSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.utils.AssetsHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by dodo on 2018/1/25.
 */


//保存Questions
public class ExamQuestionsTask extends AsyncTask<Integer,Integer,Boolean> {

    public static String QUESTION_NAME = "question";
    private int count = 0;
    @Override
    protected Boolean doInBackground(Integer... integers) {

       String rootDir =  BaseApplication.getAppContext().getFilesDir().getParent()+ File.separator + YSConst.UPDATE_ZIP;
       String rootName =  AssetsHelper.getAssetUpdateZipName(BaseApplication.getAppContext(),YSConst.UPDATE_ZIP);
       File queFile = new File(rootDir+File.separator+rootName+File.separator+QUESTION_NAME);
       File[] files = queFile.listFiles();
       Observable.from(files)
               .filter(new Func1<File, Boolean>() {
                   @Override
                   public Boolean call(File file) {
                       return file.getName().endsWith(".json");
                   }
               })
               .map(new Func1<File, String>() {
                   @Override
                   public String call(File file) {
                       String str = "";
                       try {
                           str = ReaderJsonFile(file);
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                       return str;
                   }
               })
               .subscribeOn(Schedulers.io())
               .observeOn(Schedulers.computation())
               .subscribe(new Action1<String>() {

                   @Override
                   public void call(String s) {
                     List<QuestionBean>  lists = FastJSONParser.getBeanList(s, QuestionBean.class);
                     for(QuestionBean temp: lists){
                         QuestionInfoBusiness.getInstance(BaseApplication.getAppContext()).createOrUpdate(temp);
                     }
                   }
               });
            return true;
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



    //将filePath转化成string
    private static String ReaderJson(String filePath)throws IOException {
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
    private static String ReaderJsonFile(File file)throws IOException {
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
}
