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

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.utils.util.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

import cn.gov.bjys.onlinetrain.fragment.ShopFragment;
import cn.gov.bjys.onlinetrain.utils.DifferSystemUtil;

/**
 * 查询所在城市
 */
public class SearchCityHelper extends AsyncTask<Void, Void, List<Address>> {
    private Context mContext;
    private Location mLocation;
    private WeakReference<FrameFragment> frameFragmentWeakReference;

    public SearchCityHelper(FrameFragment f,Location location) {
        frameFragmentWeakReference = new WeakReference<FrameFragment>(f);
        mContext = frameFragmentWeakReference.get().getContext();
        this.mLocation = location;
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
//        mLocation = getLocation();
    }

    @Override
    protected List<Address> doInBackground(Void... params) {
        return getAddress(mLocation);
    }

    @Override
    protected void onPostExecute(List<Address> addresses) {
        super.onPostExecute(addresses);
        if (frameFragmentWeakReference.get() instanceof ShopFragment) {
            ((ShopFragment) frameFragmentWeakReference.get()).setCityName(addresses);
        }

    }

}
