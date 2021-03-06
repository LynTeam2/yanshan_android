package cn.gov.bjys.onlinetrain.task;

import com.ycl.framework.utils.util.FastJSONParser;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import cn.gov.bjys.onlinetrain.api.ZipCallBackListener;
import cn.gov.bjys.onlinetrain.bean.CategoryBean;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/27 0027.
 */
public class HomeClassStudySencondTask extends BaseAsyncTask {

    public final static String RELATIVE_PATH = "course" + File.separator + "category.json";//相对路径

    public HomeClassStudySencondTask(ZipCallBackListener listener) {
        mListenerWeakReference = new WeakReference<ZipCallBackListener>(listener);
    }

    @Override
    protected Void doInBackground(Void... params) {
        File queFile = new File(rootDir + File.separator + rootName + File.separator + RELATIVE_PATH);
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
                        CategoryBean bean = FastJSONParser.getBean(s, CategoryBean.class);
                        if (bean != null) {
                            callback(bean.getCategories());
                        }
                    }
                });
        return super.doInBackground(params);
    }

}
