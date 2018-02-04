package cn.gov.bjys.onlinetrain.task;

import com.ycl.framework.utils.util.FastJSONParser;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import cn.gov.bjys.onlinetrain.api.ZipCallBackListener;
import cn.gov.bjys.onlinetrain.bean.CourseBean;
import cn.gov.bjys.onlinetrain.bean.CoursesBean;
import cn.gov.bjys.onlinetrain.utils.PracticeHelper;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/28 0028.
 */
//根据id获取课时数据
public class KeShiTask extends BaseAsyncTask {
    private static String RELATIVE_PATH = "course";//相对路径
    private String mRelativePath = "";


//    public KeShiTask(int id){
//        mRelativePath = RELATIVE_PATH + File.separator + id+".json";//
//    }
    public KeShiTask(ZipCallBackListener listener, int id) {
        mListenerWeakReference = new WeakReference<ZipCallBackListener>(listener);
        mRelativePath = RELATIVE_PATH + File.separator + id+".json";//
    }

    @Override
    protected Void doInBackground(Void... params) {
        File queFile = new File(rootDir + File.separator + rootName + File.separator + mRelativePath);
        if (queFile == null) {
            return null;
        }
        Observable.just(queFile)
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        success();
                    }

                    @Override
                    public void onError(Throwable e) {
                        fail();
                    }

                    @Override
                    public void onNext(String s) {
                        KeShi keshi = FastJSONParser.getBean(s, KeShi.class);
                        PracticeHelper.getInstance().setmCourseBean(keshi.getCourse());
                    }
                });
        return super.doInBackground(params);

    }
    public  static class KeShi{
        CourseBean course;

        public CourseBean getCourse() {
            return course;
        }

        public void setCourse(CourseBean course) {
            this.course = course;
        }
    }

}
