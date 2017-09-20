package cn.gov.bjys.onlinetrain.service;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.ycl.framework.utils.util.ToastUtil;

import java.util.List;
import java.util.Locale;

import cn.gov.bjys.onlinetrain.act.LifeHelpActivity;
import cn.gov.bjys.onlinetrain.utils.DifferSystemUtil;

/**
 * 查询所在城市
 */
public class SearchCityHelper extends AsyncTask<Void,Void,List<Address>>{
    private Context mContext;
    private Location mLocation;


    public SearchCityHelper(Context c){
        mContext = c;
    }

    //监听GPS位置改变后得到新的经纬度
    private LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.e("location", location.toString() + "....");
            // TODO Auto-generated method stub
            if (location != null) {
                //获取国家，省份，城市的名称
                Log.e("location", location.toString());
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

    private Location getLocation() {
        //获取位置管理服务

        //查找服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //定位精度: 最高
        criteria.setAltitudeRequired(false); //海拔信息：不需要
        criteria.setBearingRequired(false); //方位信息: 不需要
        criteria.setCostAllowed(true);  //是否允许付费
        criteria.setPowerRequirement(Criteria.POWER_LOW); //耗电量: 低功耗
//        String provider = myLocationManager.getBestProvider(criteria, true); //获取GPS信息
//        myLocationManager.requestLocationUpdates(provider,2000,5,locationListener);
//        Log.e("provider", provider);
//        List<String> list = myLocationManager.getAllProviders();
//        Log.e("provider", list.toString());
        //用于获取Location对象，以及其他
        String serviceName = Context.LOCATION_SERVICE;
        //实例化一个LocationManager对象
        myLocationManager = (LocationManager) mContext.getSystemService(serviceName);
        Location gpsLocation = null;
        Location netLocation = null;
//        myLocationManager.addGpsStatusListener();
        //权限判断

        int fineOps = checkOpsPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseOps = checkOpsPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION);

        //权限获取成功
        if(PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                && PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)){
           if(DifferSystemUtil.getSystem().equals(DifferSystemUtil.SYS_MIUI)){
                //如果是小米在判断是否获取成功
                if(fineOps == AppOpsManager.MODE_ALLOWED && coarseOps == AppOpsManager.MODE_ALLOWED){
                    //权限成功
                    return  calLocation(gpsLocation, netLocation);
                }else if(fineOps == AppOpsManager.MODE_IGNORED && coarseOps == AppOpsManager.MODE_IGNORED){
                    //权限拒绝
                    ActivityCompat.requestPermissions((LifeHelpActivity) mContext, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    ToastUtil.showToast("权限不足");
                    return null;
                }else{
                    //询问状态
                    return  calLocation(gpsLocation, netLocation);
                }
            }else{
                return  calLocation(gpsLocation, netLocation);
            }
        }else{
            ActivityCompat.requestPermissions((LifeHelpActivity) mContext, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ToastUtil.showToast("权限不足");
            return null;
        }
    }

    private Location calLocation(Location gpsLocation,Location netLocation){
            ToastUtil.showToast("权限足够");
            if (netWorkIsOpen()) {
                //3000代表每3000毫秒更新一次，500米范围外更新
                myLocationManager.requestLocationUpdates("network", 3000, 500, locationListener);
                netLocation = myLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (gpsIsOpen()) {
                myLocationManager.requestLocationUpdates("gps", 3000, 500, locationListener);
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

    // 获取地址信息
    private List<Address> getAddress(Location location) {
        List<Address> result = null;
        try {
            if (location != null) {
                Geocoder gc = new Geocoder(mContext, Locale.getDefault());
                result = gc.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mLocation = getLocation();
    }

    @Override
    protected List<Address> doInBackground(Void... params) {
        return getAddress(mLocation);
    }

    @Override
    protected void onPostExecute(List<Address> addresses) {
        super.onPostExecute(addresses);
        if(mContext instanceof LifeHelpActivity){
            ((LifeHelpActivity)mContext).setCityName(addresses);
        }
    }

    /**
     * true为通过
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
}
