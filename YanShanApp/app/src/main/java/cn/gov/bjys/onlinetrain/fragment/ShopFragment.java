package cn.gov.bjys.onlinetrain.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.blankj.utilcode.util.ZipUtils;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.util.DateUtil;
import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.ycl.framework.utils.util.LunarCalendar;
import com.ycl.framework.utils.util.ToastUtil;
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
import cn.gov.bjys.onlinetrain.adapter.DooWeatherHourAdapter;
import cn.gov.bjys.onlinetrain.api.WeatherApi;
import cn.gov.bjys.onlinetrain.bean.WeatherInfoBean;
import cn.gov.bjys.onlinetrain.bean.weather.Forecast;
import cn.gov.bjys.onlinetrain.bean.weather.HeWeather6;
import cn.gov.bjys.onlinetrain.bean.weather.Hourly;
import cn.gov.bjys.onlinetrain.service.SearchCityHelper;
import cn.gov.bjys.onlinetrain.utils.DifferSystemUtil;
import cn.gov.bjys.onlinetrain.utils.UpdateFileUtils;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ShopFragment extends FrameFragment {

    public final static String TAG = ShopFragment.class.getSimpleName();
//    public static final String WEATHER_BASE_URL = "http://www.sojson.com/";
    public static final String WEATHER_BASE_URL = "https://free-api.heweather.com/";
    public static final String KEY = "6e8a4b90f9504d9caed280c41a837c1c";
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

    @Bind(R.id.kongqizhiliang)
    TextView kongqizhiliang;

    @Bind(R.id.xingqiji)
    TextView xiqingji;

    @Bind(R.id.grid_view)
    GridView grid_view;

    DooWeatherHourAdapter mDooWeatherHourAdapter;

    //监听GPS位置改变后得到新的经纬度
    private LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.e("location", location.toString() + "....");
            // TODO Auto-generated method stub
            if (location != null) {
                //获取国家，省份，城市的名称
                new SearchCityHelper(ShopFragment.this,location).execute();
            } else {
                ToastUtil.showToast("获取不到数据");
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    };
    private LocationManager myLocationManager;

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
        initGridViewAdapter();
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if(visible){
            requestCityName();
        }
    }

    private void setupView(HeWeather6.WeatherJson bean) {

        kongqizhiliang.setText(bean.getHeWeather6().get(0).getLifestyle().get(0).getTxt());

        xiqingji.setText(DateUtil.getWeek(bean.getHeWeather6().get(0).getDaily_forecast().get(0).getDate()));

        wendu.setText(bean .getHeWeather6().get(0).getNow().getTmp()+ "°");
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


    private void initGridViewAdapter(){
        mDooWeatherHourAdapter = new DooWeatherHourAdapter(getContext(),null);
        grid_view.setAdapter(mDooWeatherHourAdapter);
    }

    /**
     * 查询城市信息
     */
    private void requestCityName() {
        showProDialog("请求数据中...");
        new SearchCityHelper(this,getLocation()).execute();
    }

    List<String> mAllProviders;
    private void initLocationManager(){
        //获取位置管理服务
        //查找服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //定位精度: 最高
        criteria.setAltitudeRequired(false); //海拔信息：不需要
        criteria.setBearingRequired(false); //方位信息: 不需要
        criteria.setCostAllowed(true);  //是否允许付费
        criteria.setPowerRequirement(Criteria.POWER_LOW); //耗电量: 低功耗
        //用于获取Location对象，以及其他
        String serviceName = Context.LOCATION_SERVICE;
        //实例化一个LocationManager对象
        myLocationManager = (LocationManager) getContext().getSystemService(serviceName);

        String provider = myLocationManager.getBestProvider(criteria, true); //获取GPS信息
        mAllProviders = myLocationManager.getAllProviders();
    }

    private boolean requestPermission() {
        if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                && PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            return true;
        } else {
            ActivityCompat.requestPermissions((FrameActivity) getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLocationManager();
        requestPermission();
    }

    @Override
    public void onStart() {
        super.onStart();
        registerLocationListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        removeLocationListener();
    }

    @SuppressLint("MissingPermission")
    private void registerLocationListener(){
/*        myLocationManager.setTestProviderEnabled("gps",true);
        myLocationManager.setTestProviderEnabled("network",true);*/
        for(String s : mAllProviders){
            if(requestPermission()) {
                if (s.contains("network")) {
                    myLocationManager.requestLocationUpdates("network", 3000, 500, mLocationListener);
                }
                if (s.contains("gps")) {
                    myLocationManager.requestLocationUpdates("gps", 3000, 500, mLocationListener);
                }
            }
        }
    }

    private void removeLocationListener(){
        myLocationManager.removeUpdates (mLocationListener);
/*        myLocationManager.setTestProviderEnabled("gps",false);
        myLocationManager.setTestProviderEnabled("network",false);*/
    }

    private Location getLocation() {

        Location gpsLocation = null;
        Location netLocation = null;
//        myLocationManager.addGpsStatusListener();
        //权限判断

        int fineOps = AppOpsManager.MODE_ALLOWED;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            fineOps = checkOpsPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        }
        int coarseOps = AppOpsManager.MODE_ALLOWED;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            coarseOps = checkOpsPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        //权限获取成功

            if (DifferSystemUtil.getSystem().equals(DifferSystemUtil.SYS_MIUI)) {
                //如果是小米在判断是否获取成功
                if (fineOps == AppOpsManager.MODE_ALLOWED && coarseOps == AppOpsManager.MODE_ALLOWED) {
                    //权限成功
                    return calLocation(gpsLocation, netLocation);
                } else if (fineOps == AppOpsManager.MODE_IGNORED && coarseOps == AppOpsManager.MODE_IGNORED) {
                    //权限拒绝
                    ActivityCompat.requestPermissions((FrameActivity) getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    ToastUtil.showToast("权限不足");
                    return null;
                } else {
                    //询问状态
                    return calLocation(gpsLocation, netLocation);
                }
            } else {
                return calLocation(gpsLocation, netLocation);
            }
        }


    private Location calLocation(Location gpsLocation, Location netLocation) {
        if (netWorkIsOpen()) {
            //3000代表每3000毫秒更新一次，500米范围外更新
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            netLocation = myLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if (gpsIsOpen()) {
            gpsLocation = myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if (gpsLocation == null && netLocation == null) {
            return null;
        }
        if (gpsLocation != null && netLocation != null) {
            if (gpsLocation.getTime() < netLocation.getTime()) {
                gpsLocation = null;
                return netLocation;
            } else {
                netLocation = null;
                return gpsLocation;
            }
        }
        if (gpsLocation == null) {
            return netLocation;
        } else {
            return gpsLocation;
        }
    }


    private boolean gpsIsOpen() {
        boolean isOpen = true;
        if (!myLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {//没有开启GPS
            isOpen = false;
        }
        return isOpen;
    }

    private boolean netWorkIsOpen() {
        boolean netIsOpen = true;
        if (!myLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {//没有开启网络定位
            netIsOpen = false;
        }
        return netIsOpen;
    }


    /**
     * true为通过
     *
     * @param context
     * @param permission
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    private static int checkOpsPermission(Context context, String permission) {
        try {
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            String opsName = AppOpsManager.permissionToOp(permission);
            if (opsName == null) {
                return AppOpsManager.MODE_ALLOWED;
            }
            int opsMode = appOpsManager.checkOpNoThrow(opsName, android.os.Process.myUid(), context.getPackageName());
            return opsMode;
        } catch (Exception ex) {
            return AppOpsManager.MODE_ALLOWED;
        }
    }

    //回调并查询天气
    private String mCityName = "";

    public void setCityName(List<Address> addresses) {
        mCityName = "";
        if (addresses != null && addresses.size() > 0) {
            for (Address address : addresses) {
                mCityName += address.getLocality();
            }
        }
        mHeader.setTitleText(mCityName);
        if (checkNeedUpdate()) {
//        if (true) {
            reqWeatherInfos(mCityName);
        }
    }

    //是否刷新天气信息
    private boolean checkNeedUpdate() {

        String lastWeather = SavePreference.getStr(getContext(), NEW_WEATHER_FLAG);
        HeWeather6.WeatherJson bean = FastJSONParser.getBean(lastWeather, HeWeather6.WeatherJson.class);
        String lastCity = "";
        String lastDate = "";
        try {
            lastCity = bean.getHeWeather6().get(0).getBasic().getLocation();
            lastDate = bean.getHeWeather6().get(0).getDaily_forecast().get(0).getDate();
        }catch (Exception e){
            e.printStackTrace();

        }
        String today = DateUtil.formatYourSelf(System.currentTimeMillis(), new SimpleDateFormat("yyyy-MM-dd"));
        if (TextUtils.isEmpty(lastCity) || !mCityName.contains(lastCity) || !today.equals(lastDate)) {
            //未保存上一次的城市地名 或者 此次地名未包含上次地名 或者 日期不同 需要刷新

            return true;
        } else {
            //否则的话 刷新

           try {
               List<Forecast> datas = bean.getHeWeather6().get(0).getDaily_forecast();//
               mWeatherAdapter.setNewData(datas);
               List<Hourly> list = bean.getHeWeather6().get(0).getHourly();
               List<Hourly> showList = list.subList(0, list.size() - 3);
               mDooWeatherHourAdapter.replaceAll(showList);
               setupView(bean);
           }catch (Exception e){
               e.printStackTrace();

           }
            dismissProgressDialog();
            return false;
        }
    }


    public final static String NEW_WEATHER_FLAG = "new_weather_flag";

    private void reqWeatherInfos(String cityName) {

        rx.Observable<String> obs;
        obs = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).
                getSpeUrlService(WEATHER_BASE_URL, WeatherApi.class).getNewWeatherInfos(KEY,cityName);
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
                        HeWeather6.WeatherJson bean = FastJSONParser.getBean(s, HeWeather6.WeatherJson.class);
                        //只存下最新数据
                        SavePreference.save(getContext(), NEW_WEATHER_FLAG, s);
                        List<Forecast> datas = bean.getHeWeather6().get(0).getDaily_forecast();//
                        mWeatherAdapter.setNewData(datas);
                        List<Hourly> list =  bean.getHeWeather6().get(0).getHourly();
                        List<Hourly> showList = list.subList(0,list.size()-3);
                        mDooWeatherHourAdapter.replaceAll(showList);
                        setupView(bean);
                    }
                });
    }


}
