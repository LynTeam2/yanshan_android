package cn.gov.bjys.onlinetrain.task;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.ycl.framework.utils.util.FastJSONParser;

import java.io.File;
import java.io.IOException;

import cn.gov.bjys.onlinetrain.bean.AllBannerBean;
import cn.gov.bjys.onlinetrain.utils.BannerComHelper;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by dodo on 2018/2/2.
 */

public class BannerTask extends BaseAsyncTask {

    public final static String RELATIVE_PATH = "banner" + File.separator+"banner.json";//相对路径
    ConvenientBanner mBanner;
    String zipResName;
    public BannerTask(final ConvenientBanner banner, String zipResName) {
        this.mBanner = banner;
        this.zipResName = zipResName;
    }

    @Override
    protected Void doInBackground(Void... params) {
        File queFile = new File(rootDir+File.separator+rootName+File.separator+RELATIVE_PATH);
        if(queFile == null){
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
                        AllBannerBean bean = FastJSONParser.getBean(s, AllBannerBean.class);
                        if(bean != null) {
                            BannerComHelper.initBanner(mBanner, bean.getBanners());
                        }
                    }
                });
        return null;
    }

}
