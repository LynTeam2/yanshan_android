package cn.gov.bjys.onlinetrain.task;

import com.ycl.framework.utils.util.FastJSONParser;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import cn.gov.bjys.onlinetrain.api.ZipCallBackListener;
import cn.gov.bjys.onlinetrain.bean.NativeLawsBean;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class LawsTask  extends BaseAsyncTask {
    private static String RELATIVE_PATH = "lawType";//相对路径
    private String mRelativePath = "";


    public LawsTask(ZipCallBackListener listener) {
        mListenerWeakReference = new WeakReference<ZipCallBackListener>(listener);
        mRelativePath = "law" + File.separator + RELATIVE_PATH+".json";//
    }

    @Override
    protected Void doInBackground(final Void... params) {
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
                        NativeLawsBean.NativeLawsBeanParent parent = FastJSONParser.getBean(s, NativeLawsBean.NativeLawsBeanParent.class);
                        mListenerWeakReference.get().zipCallBackListener(parent.getLawType());
                    }
                });
        return super.doInBackground(params);

    }

}