package cn.gov.bjys.onlinetrain.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.GlideProxy;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.CourseBean;
import cn.gov.bjys.onlinetrain.bean.LawContentBean;
import cn.gov.bjys.onlinetrain.bean.SearchBean;
import cn.gov.bjys.onlinetrain.task.KeShiTask;
import cn.gov.bjys.onlinetrain.utils.AssetsHelper;
import cn.gov.bjys.onlinetrain.utils.PracticeHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static cn.gov.bjys.onlinetrain.task.BaseAsyncTask.ReaderJsonFile;



public class DooSearchMultiAdapter extends BaseMultiItemQuickAdapter<SearchBean, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public DooSearchMultiAdapter(List<SearchBean> data) {
        super(data);
        addItemType(SearchBean.KESHI,R.layout.item_linear_class_layout);
        addItemType(SearchBean.LAW,R.layout.item_linear_class_layout);
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View v = super.getItemView(layoutResId, parent);
        AutoUtils.auto(v);
        return v;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchBean item) {
        switch (item.getItemType()){
            case SearchBean.KESHI:
                initKeshiInfo(helper,item);
                break;
            case SearchBean.LAW:
                String c = item.getItem();
                LawContentBean bean = FastJSONParser.getBean(c, LawContentBean.class);
                ImageView img = helper.getView(R.id.icon);
                GlideProxy.loadImgForUrlPlaceHolderDontAnimate(img,
                        bean.getIconPath(),
                        R.drawable.icon_189_174);
                helper.setText(R.id.title, bean.getLawName());
                helper.setText(R.id.content, bean.getCreateTime());
                break;
        }
    }
    private static final String COURSE = "course";//相对路径
    private void initKeshiInfo(final BaseViewHolder helper, SearchBean item){
        String course = item.getItem();
        String rootDir =  BaseApplication.getAppContext().getFilesDir().getParent()+ File.separator + YSConst.UPDATE_ZIP;
        String rootName =  AssetsHelper.getAssetUpdateZipName(BaseApplication.getAppContext(),YSConst.UPDATE_ZIP);
        File queFile = new File(rootDir + File.separator + rootName + File.separator + COURSE + File.separator + course+".json");
        if (queFile == null) {
            return ;
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
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String s) {
                        KeShiTask.KeShi keshi = FastJSONParser.getBean(s, KeShiTask.KeShi.class);
                        CourseBean bean = keshi.getCourse();
                        ImageView img = helper.getView(R.id.icon);
                        GlideProxy.loadImgForUrlPlaceHolderDontAnimate(img,
                                bean.getIcon(),
                                R.drawable.icon_189_174);
                        helper.setText(R.id.title, bean.getCourseName());
                        helper.setText(R.id.content, bean.getAjType());
                    }
                });
    }
}
