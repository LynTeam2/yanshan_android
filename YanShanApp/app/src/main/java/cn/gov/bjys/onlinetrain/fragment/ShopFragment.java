package cn.gov.bjys.onlinetrain.fragment;

import android.location.Address;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ZipUtils;
import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.util.DateUtil;
import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.ycl.framework.utils.util.LunarCalendar;
import com.ycl.framework.view.ProgressWebView;
import com.ycl.framework.view.TitleHeaderView;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.business.DownLoadInfoBusiness;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.entity.DataInfo;
import com.zls.www.mulit_file_download_lib.multi_file_download.db.entity.DownLoadInfoBean;
import com.zls.www.mulit_file_download_lib.multi_file_download.view.MultiDownView;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.LifeHelpActivity;
import cn.gov.bjys.onlinetrain.adapter.DooWeatherAdapter;
import cn.gov.bjys.onlinetrain.api.WeatherApi;
import cn.gov.bjys.onlinetrain.bean.WeatherInfoBean;
import cn.gov.bjys.onlinetrain.service.SearchCityHelper;
import cn.gov.bjys.onlinetrain.utils.UpdateFileUtils;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ShopFragment extends FrameFragment {

    public final static String TAG = ShopFragment.class.getSimpleName();
    public static final String WEATHER_BASE_URL = "http://www.sojson.com/";

    @Bind(R.id.header)
    TitleHeaderView mHeader;

    @Bind(R.id.date)
    TextView date;

    @Bind(R.id.xingqi)
    TextView xingqi;

    @Bind(R.id.nongli)
    TextView nongli;

    @Bind(R.id.wendu)
    TextView wendu;

    @Bind(R.id.rv)
    RecyclerView mRv;

    DooWeatherAdapter mWeatherAdapter;



    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_shop_layout, container, false);
        StatusBarUtil.addStatusForFragment(getActivity(),view.findViewById(R.id.status_bar_layout));
        return view;
    }
    @Override
    public void initViews() {
        super.initViews();
        initRvAdapter();
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if(visible){
            requestCityName();
        }
    }

    private void setupView(WeatherInfoBean bean) {
        //实时温度
        wendu.setText(bean.getData().getWendu() + "°");

        String dateStr = bean.getDate();

        date.setText(decoratedDate(dateStr));

        xingqi.setText(XingqiToZhou(bean.getData().getForecast().get(0).getDate()));

        nongli.setText(getNongLi(dateStr));

    }

    private String decoratedDate(String date){
        String year = date.substring(0,4);
        String month = date.substring(4,6);
        String day = date.substring(6,date.length());

        int monthValue = 0;
        try{
            monthValue = Integer.valueOf(month);
        }catch (Exception e){
            e.printStackTrace();
        }
        return (monthValue > 0 ? monthValue : month)+"月"+day+"日";
    }

    //星期几 转 周几
    private String XingqiToZhou(String xingqi){
        if(xingqi.length() < 1){
            return "";
        }
        String val = xingqi.substring(xingqi.length()-1, xingqi.length());
        return "周"+val;
    }

    //获取农历
    private String getNongLi(String date){
        String year = date.substring(0,4);
        String mouth = date.substring(4,6);
        String day = date.substring(6,date.length());
        int[] ret = new int[4];
        try {
            ret = LunarCalendar.solarToLunar(Integer.valueOf(year),Integer.valueOf(mouth),Integer.valueOf(day));
        }catch (Exception e){
            e.printStackTrace();
        }

        int mouthVal = ret[1];
        String mouthStr = String.valueOf(mouthVal);
        String mouthOutPut = "";
        for(int i=0; i<mouthStr.length(); i++){
            if(mouthStr.length() < 2){
                mouthOutPut += LunarCalendar.formatDigit(mouthStr.charAt(i));
            }else{
                if(i==0){
                    mouthOutPut += "十";
                }else {
                    mouthOutPut += LunarCalendar.formatDigit(mouthStr.charAt(i));
                }
            }
        }

        int dayVal = ret[2];
        String dayStr = String.valueOf(dayVal);
        String dayOutPut = "";
        for(int i=0; i<dayStr.length(); i++){
            if(dayStr.length() < 2){
                dayOutPut += LunarCalendar.formatDigit(dayStr.charAt(i));
            }else{
                if(i==0){
                    dayOutPut += (LunarCalendar.formatDigit(dayStr.charAt(i)) == '一' ? "":LunarCalendar.formatDigit(dayStr.charAt(i))) + "十";
                }else {
                    dayOutPut += LunarCalendar.formatDigit(dayStr.charAt(i));
                }
            }
        }

        return  "农历" + mouthOutPut+"月" + dayOutPut;
    }


    @Override
    public void initData() {
        super.initData();
    }

    private void initRvAdapter() {
        mWeatherAdapter = new DooWeatherAdapter(R.layout.item_weahter_item_layout, null);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(mWeatherAdapter);
    }


    /**
     * 查询城市信息
     */
    private void requestCityName() {
        showProDialog("请求数据中...");
        new SearchCityHelper(this).execute();
    }

    //回调并查询天气
    private String mCityName = "";

    public void setCityName(List<Address> addresses) {
        if (addresses != null && addresses.size() > 0) {
            for (Address address : addresses) {
                mCityName += address.getLocality();
            }
        }
        mHeader.setTitleText(mCityName);
        if (checkNeedUpdate()) {
            reqWeatherInfos(mCityName);
        }
    }

    //是否刷新天气信息
    private boolean checkNeedUpdate() {
        Log.d("dodoT", "checkNeedUpdate");
        String lastWeather = SavePreference.getStr(getContext(), NEW_WEATHER_FLAG);
        WeatherInfoBean bean = FastJSONParser.getBean(lastWeather, WeatherInfoBean.class);
        String lastCity = bean.getCity();
        String today = DateUtil.formatYourSelf(System.currentTimeMillis(), new SimpleDateFormat("yyyyMMdd"));
        if (TextUtils.isEmpty(lastCity) || !mCityName.contains(bean.getCity()) || !today.equals(bean.getDate())) {
            //未保存上一次的城市地名 或者 此次地名未包含上次地名 或者 日期不同 需要刷新
            Log.d("dodoT", "checkNeedUpdate  true");
            return true;
        } else {
            //否则的话 刷新
            Log.d("dodoT", "checkNeedUpdate  false");
            List<WeatherInfoBean.detailWeatherInfo> datas = bean.getData().getForecast();
            mWeatherAdapter.setNewData(datas);
            setupView(bean);
            dismissProgressDialog();
            return false;
        }
    }


    public final static String NEW_WEATHER_FLAG = "new_weather_flag";

    private void reqWeatherInfos(String cityName) {
        Log.d("dodoT", "reqWeatherInfos");
        rx.Observable<String> obs;
        obs = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).
                getSpeUrlService(WEATHER_BASE_URL, WeatherApi.class).getWeatherInfos(cityName);
        obs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                        dismissProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                        dismissProgressDialog();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("dodoT", "onNext");
                        WeatherInfoBean bean = FastJSONParser.getBean(s, WeatherInfoBean.class);
                        //只存下最新数据
                        SavePreference.save(getContext(), NEW_WEATHER_FLAG, s);
                        List<WeatherInfoBean.detailWeatherInfo> datas = bean.getData().getForecast();
                        mWeatherAdapter.setNewData(datas);
                        setupView(bean);
                    }
                });
    }


}
