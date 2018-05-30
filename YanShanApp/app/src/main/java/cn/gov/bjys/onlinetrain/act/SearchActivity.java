package cn.gov.bjys.onlinetrain.act;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.GlideProxy;
import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.ycl.framework.utils.util.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.DooSearchLayout;
import cn.gov.bjys.onlinetrain.adapter.DooSearchMultiAdapter;
import cn.gov.bjys.onlinetrain.api.BaseResponse;
import cn.gov.bjys.onlinetrain.api.UserApi;
import cn.gov.bjys.onlinetrain.bean.CourseBean;
import cn.gov.bjys.onlinetrain.bean.LawContentBean;
import cn.gov.bjys.onlinetrain.bean.SearchBean;
import cn.gov.bjys.onlinetrain.task.KeShiTask;
import cn.gov.bjys.onlinetrain.utils.AssetsHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static cn.gov.bjys.onlinetrain.task.BaseAsyncTask.ReaderJsonFile;

/**
 * Created by Administrator on 2018/4/15 0015.
 */
public class SearchActivity extends FrameActivity {

    @Bind(R.id.search_layout)
    DooSearchLayout mDooSearchLayout;

    @Bind(R.id.search_rv)
    RecyclerView search_rv;
    DooSearchMultiAdapter mDooSearchMultiAdapter;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_search_layout);
    }


    EditText mEditText;
    DooSearchLayout.CallBack l;
    @Override
    public void initViews() {
        super.initViews();

        mEditText = (EditText) mDooSearchLayout.findViewById(R.id.edit_text);
        mEditText.setEnabled(true);
        mEditText.setFocusable(true);
        initEditTextListener();
        initAdapter();
    }

    private void initEditTextListener(){
        l = new DooSearchLayout.CallBack() {
            @Override
            public void textChange(String s) {
                resolveUserSearchContent(s);
            }

            @Override
            public void cancel() {
                finish();
            }

            @Override
            public void editClick() {
                showSoftInputFromWindow(SearchActivity.this, mEditText);
            }
        };
        mDooSearchLayout.register(l);
    }

    List<SearchBean> mDatas = new ArrayList<>();
    private void initAdapter(){
        mDatas.clear();
        mDooSearchMultiAdapter = new DooSearchMultiAdapter(mDatas);
        search_rv.setAdapter(mDooSearchMultiAdapter);
        search_rv.setLayoutManager(new LinearLayoutManager(this));
        mDooSearchMultiAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mDooSearchMultiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               SearchBean tempBean = (SearchBean) adapter.getData().get(position);
               switch (tempBean.getItemType()){
                   case SearchBean.KESHI:
                       startToKeShi(tempBean.getItem());
                       break;
                   case SearchBean.LAW:
                       LawContentBean bean = FastJSONParser.getBean(tempBean.getItem(),LawContentBean.class);
                       String name = bean.getFileName();
                       String filepath = bean.getFilePath();
                       Bundle bundle = new Bundle();
                       bundle.putString(PDFWebActivity.HEAD_TAG,name);
                       bundle.putString(PDFWebActivity.URL_TAG,filepath);
                       startAct(PDFWebActivity.class, bundle);
                       break;
               }
            }
        });
    }
    //跳转至课时
    private static final String COURSE = "course";//相对路径
    private void startToKeShi(String keshiId){
        String course = keshiId;
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
                        int type = Integer.valueOf(bean.getCourseType());
                        int id = bean.getId();
                        Bundle mBundle = new Bundle();
                        mBundle.putInt("type",type);
                        mBundle.putInt("id", id);
                        startAct(PracticePrepareActivity.class, mBundle);
                    }
                });
    }

    private void resolveUserSearchContent(final String s){
        String input = s;
        if(TextUtils.isEmpty(input)){
            return;//传入的s 为""的情况返回
        }
        rx.Observable<BaseResponse<String>> obs;
        obs = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).
                getSpeUrlService(YSConst.BaseUrl.BASE_URL, UserApi.class)
                .search(input);
        obs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse<String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                        if ("1".equals(stringBaseResponse.getCode())) {
                        //suc
                            String res = stringBaseResponse.getResults();
                            SearchBean.SearchParentBean parentBean = FastJSONParser.getBean(res, SearchBean.SearchParentBean.class);
                            List<SearchBean>  beans = parentBean.getItems();
                            mDatas.clear();
                            mDatas.addAll(beans);
                            mDooSearchMultiAdapter.notifyDataSetChanged();
                        } else {
                            //fail
                            ToastUtil.showToast(stringBaseResponse.getMsg());
                        }
                    }
                });

    }


    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(FrameActivity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

}
