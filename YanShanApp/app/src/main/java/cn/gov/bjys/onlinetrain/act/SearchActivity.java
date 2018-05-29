package cn.gov.bjys.onlinetrain.act;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.ycl.framework.utils.util.ToastUtil;

import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.DooSearchLayout;
import cn.gov.bjys.onlinetrain.api.BaseResponse;
import cn.gov.bjys.onlinetrain.api.UserApi;
import cn.gov.bjys.onlinetrain.bean.SearchBean;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/15 0015.
 */
public class SearchActivity extends FrameActivity {

    @Bind(R.id.search_layout)
    DooSearchLayout mDooSearchLayout;

    @Bind(R.id.search_rv)
    RecyclerView search_rv;

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
                        Log.d("dodo", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("dodo", "e.message = " + e.getMessage());
                    }

                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                        Log.d("dodo", "resp = " + stringBaseResponse);
                        if ("1".equals(stringBaseResponse.getCode())) {
                        //suc
                            String res = stringBaseResponse.getResults();
                            SearchBean.SearchParentBean parentBean = FastJSONParser.getBean(res, SearchBean.SearchParentBean.class);
                            List<SearchBean>  beans = parentBean.getItems();

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
