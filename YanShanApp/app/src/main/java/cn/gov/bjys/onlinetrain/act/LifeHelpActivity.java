package cn.gov.bjys.onlinetrain.act;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.util.BaseResp;
import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.HRetrofitNetHelper;

import java.util.List;
import java.util.Observable;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.api.Weather;
import cn.gov.bjys.onlinetrain.bean.WeatherInfoBean;
import cn.gov.bjys.onlinetrain.service.SearchCityHelper;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 生活助手Activity
 */
public class LifeHelpActivity extends FrameActivity {
    public final static String TAG = "LifeHelpActivity";

    public static final String WEATHER_BASE_URL = "http://www.sojson.com/";

    @Bind(R.id.city_name)
    TextView mCityNameTv;


    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_lifehelp_layout);
    }

    @Override
    public void initViews() {
        super.initViews();
        requestCityName();
    }

    @Override
    public void initData() {
        super.initData();
    }

    /**
     * 查询城市信息
     */
    private void requestCityName(){
        new SearchCityHelper(LifeHelpActivity.this).execute();
    }

    private String mCityName = "";
    public void setCityName(List<Address> addresses){
        if(addresses != null && addresses.size() > 0){
            for(Address address : addresses){
               mCityName += address.getLocality();
            }
        }
        mCityNameTv.setText(mCityName);
        reqWeatherInfos(mCityName);
    }

    private void reqWeatherInfos(String cityName){
        rx.Observable<String> obs;
        obs = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).
                getSpeUrlService(WEATHER_BASE_URL, Weather.class).getWeatherInfos(cityName);
        obs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"onError");
                    }

                    @Override
                    public void onNext(String s) {
                        WeatherInfoBean bean =    FastJSONParser.getBean(s,WeatherInfoBean.class);
                        Log.d(TAG,"onNext  s="+s);
                    }
                });
    }

    //requestPermissions方法执行后的回调方法
    /*
    * requestCode:相当于一个标志，
    * permissions：需要传进的permission，不能为空
    * grantResults：用户进行操作之后，或同意或拒绝回调的传进的两个参数;
    * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;
        for (int i = 0; i < grantResults.length; ++i) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
                // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    //解释原因，并且引导用户至设置页手动授权
                    new AlertDialog.Builder(this)
                            .setMessage("请在【权限管理】选项中开启【定位】\r\n" +
                                    "无法获取地理位置信息,将导致部分功能无法正常使用，需要到设置页面手动授权")
                            .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //引导用户至设置页手动授权
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //引导用户手动授权，权限请求失败
                                }
                            }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            //引导用户手动授权，权限请求失败
                        }
                    }).show();

                } else {
                    //权限请求失败，但未选中“不再提示”选项
                }
                break;
            }
        }
        if (hasAllGranted) {
            //权限请求成功
        }
    }
}
