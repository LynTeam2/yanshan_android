package cn.gov.bjys.onlinetrain.fragment.UserFragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.util.DateUtil;
import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.utils.util.advanced.SpannableStringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.adapter.DooExchangeRewardsAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooSigninGridAdapter;
import cn.gov.bjys.onlinetrain.api.BaseResponse;
import cn.gov.bjys.onlinetrain.api.UserApi;
import cn.gov.bjys.onlinetrain.bean.RewardBean;
import cn.gov.bjys.onlinetrain.bean.SignInBean;
import cn.gov.bjys.onlinetrain.utils.MapParamsHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import cn.gov.bjys.onlinetrain.utils.YSUserInfoManager;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 签到fragment
 */
public class SignInFragment  extends FrameFragment{

    public final static String SIGN_SUCCESS = "sign_success";

    public static SignInFragment newInstance() {
        Bundle args = new Bundle();
        SignInFragment fragment = new SignInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Bind(R.id.icon)
    ImageView icon;

    @Bind(R.id.money)
    TextView money;

    @Bind(R.id.grid_view)
    GridView grid_view;

    @Bind(R.id.rv)
    RecyclerView rv;

    @Bind(R.id.sign_layout)
    RelativeLayout mSignLayout;

    @Bind(R.id.sign_hint)
    TextView sign_hint;


    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return View.inflate(getContext(), R.layout.fragment_sign_in_layout, null);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initViews() {
        super.initViews();
        initGridView();
        initRecycleView();
        initSignHint();
    }
    DooSigninGridAdapter mDooSigninGridAdapter;
    List<SignInBean> mSignList = new ArrayList<>();


    public void initSignHint(){
            updateSignHint();
    }

    private void updateSignHint(){
        SpannableStringBuilder ssb = null;
        List<SignInBean> datas =  mDooSigninGridAdapter.getDatas();
        SignInBean bean = datas.get(1);
        switch (bean.getType()){
            case SignInBean.ING:
                ssb = new SpannableStringUtils.Builder()
                        .append("今日签到可领取").setForegroundColor(getResources().getColor(R.color.normal_black))
                        .append("10").setForegroundColor(getResources().getColor(R.color.normal_red))
                        .append("安全豆").setForegroundColor(getResources().getColor(R.color.normal_black))
                        .create();
                break;
            case SignInBean.SUC:
                ssb = new SpannableStringUtils.Builder()
                        .append("您已经完成今日签到").setForegroundColor(getResources().getColor(R.color.normal_black))
                        .create();
                break;
        }
        sign_hint.setText(ssb);
    }


    public void initGridView(){
        mSignList.clear();
        mSignList.addAll(prepareDatas());
        mDooSigninGridAdapter = new DooSigninGridAdapter(getContext(), mSignList);
        grid_view.setAdapter(mDooSigninGridAdapter);
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SignInBean bean = (SignInBean) mDooSigninGridAdapter.getDatas().get(position);
                switch (bean.getType()){
                    case SignInBean.FUTURE:
                        ToastUtil.showToast("还未开始");
                        break;
                    case SignInBean.FINISH:
                        ToastUtil.showToast("已经结束");
                        break;
                    case SignInBean.ING:
                        ToastUtil.showToast("签到成功");
                        bean.setType(SignInBean.SUC);
                        SavePreference.save(getContext(),SIGN_SUCCESS, mNowDate);
                        updateUserWealth();
                        updateSignHint();
                        break;
                    case SignInBean.SUC:
                        ToastUtil.showToast("已经完成签到");
                        break;
                }
                mDooSigninGridAdapter.notifyDataSetChanged();
            }
        });
    }


    private String mNowDate = "";

    public List<SignInBean> prepareDatas(){
        List<SignInBean> list = new ArrayList<>();
        String lastTimeStr = SavePreference.getStr(getContext(),SIGN_SUCCESS);
        long nowtime = System.currentTimeMillis();


        long yesterday = nowtime - 24*60*60*1000;
        long tomorrow = nowtime + 24*60*60*1000;
        long aftertomorrow = nowtime + 2*24*60*60*1000;

        String yesterDate = DateUtil.formatYourSelf(yesterday, new SimpleDateFormat("yyyyMMdd"));
        String nowDate = DateUtil.formatYourSelf(nowtime, new SimpleDateFormat("yyyyMMdd"));
        mNowDate = nowDate;
        String tomorrowDate = DateUtil.formatYourSelf(tomorrow, new SimpleDateFormat("yyyyMMdd"));
        String aftertomorrowDate = DateUtil.formatYourSelf(aftertomorrow, new SimpleDateFormat("yyyyMMdd"));

        SignInBean yesBean = new SignInBean();
        yesBean.setDay(yesterDate.substring(6,yesterDate.length()));
        yesBean.setMouth(yesterDate.substring(4,6));
        yesBean.setType(SignInBean.FINISH);//昨天
        list.add(yesBean);

        SignInBean todayBean = new SignInBean();
        todayBean.setDay(nowDate.substring(6,nowDate.length()));
        todayBean.setMouth(nowDate.substring(4,6));

        if(nowDate.equals(lastTimeStr)) {
            todayBean.setType(SignInBean.SUC);//今天 已经签到
        }else{
            todayBean.setType(SignInBean.ING);//今天 未签到
        }
        list.add(todayBean);

        SignInBean tomorrowBean = new SignInBean();
        tomorrowBean.setDay(tomorrowDate.substring(6,tomorrowDate.length()));
        tomorrowBean.setMouth(tomorrowDate.substring(4,6));
        tomorrowBean.setType(SignInBean.FUTURE);//明天
        list.add(tomorrowBean);


        SignInBean afterTomorrowBean = new SignInBean();
        afterTomorrowBean.setDay(aftertomorrowDate.substring(6,aftertomorrowDate.length()));
        afterTomorrowBean.setMouth(aftertomorrowDate.substring(4,6));
        afterTomorrowBean.setType(SignInBean.FUTURE);//昨天
        list.add(afterTomorrowBean);


        return list;
    }


    DooExchangeRewardsAdapter mDooExchangeRewardsAdapter;
    public void initRecycleView(){
        mDooExchangeRewardsAdapter = new DooExchangeRewardsAdapter(R.layout.item_exchange_reward_item,prepareDatasRv());
        rv.setAdapter(mDooExchangeRewardsAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public List<RewardBean> prepareDatasRv(){
        List<RewardBean> list = new ArrayList<>();
        for(int i=0;i<4;i++){
            RewardBean bean = new RewardBean();
            bean.setId(i);
            bean.setName("this is =" + i);
            list.add(bean);
        }
        return list;
    }

    @OnClick({R.id.sign_layout})
    public void onTabClick(View v){
        switch (v.getId()){
            case R.id.sign_layout:
               List<SignInBean> datas =  mDooSigninGridAdapter.getDatas();
               SignInBean bean = datas.get(1);
               switch (bean.getType()){
                   case SignInBean.ING:
                       ToastUtil.showToast("签到成功");
                       bean.setType(SignInBean.SUC);
                       mDooSigninGridAdapter.notifyDataSetChanged();
                       SavePreference.save(getContext(), SIGN_SUCCESS, mNowDate);
                       updateUserWealth();
                       updateSignHint();
                       break;
                    case SignInBean.SUC:
                        ToastUtil.showToast("已经完成签到");
                        break;
               }

                break;
        }
    }

    public void updateUserWealth(){
        long userValue = SavePreference.getLong(getContext(), YSConst.UserInfo.USER_WEALTH);
        SavePreference.save(getContext(), YSConst.UserInfo.USER_WEALTH,userValue +10L );
        uploadWealthValue(userValue + 10L);
    }


    private void uploadWealthValue(final long count) {
        Observable<BaseResponse<String>> obsLogin;
        obsLogin = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).
                getSpeUrlService(YSConst.BaseUrl.BASE_URL, UserApi.class).upLoadWealthValue(HRetrofitNetHelper.createReqJsonBody(MapParamsHelper.uploadWealthValue(count)));
        obsLogin.subscribeOn(Schedulers.io())
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
                        if("1".equals(stringBaseResponse.getCode())){
                            YSUserInfoManager.getsInstance().getUserBean().setBeanCount(count);
                        }
                    }
                });
    }

}
