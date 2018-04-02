package cn.gov.bjys.onlinetrain.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ycl.framework.utils.util.DateUtil;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.WeatherInfoBean;
import cn.gov.bjys.onlinetrain.bean.weather.Forecast;

/**
 * Created by dodo on 2018/3/7.
 */

public class DooWeatherAdapter extends BaseQuickAdapter<Forecast, BaseViewHolder> {


    public DooWeatherAdapter(int layoutResId, List<Forecast> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Forecast item) {
        ImageView todayIcon = helper.getView(R.id.today);
        if (helper.getAdapterPosition() != 0) {
            todayIcon.setVisibility(View.INVISIBLE);
        }else {
            todayIcon.setVisibility(View.VISIBLE);
        }

        helper.setText(R.id.xingqi, DateUtil.getWeek(item.getDate()));

        helper.setText(R.id.type, item.getCond_txt_d());//晴间多云"

        helper.setText(R.id.wendu, item.getTmp_min()+"°~"+item.getTmp_max()+"°");

        ImageView icon = helper.getView(R.id.icon);

        setWeatherIcon(icon, item.getCond_txt_d());
    }






    //给天气状况选择图标
    private void setWeatherIcon(ImageView icon, String weatherStr) {

        if (weatherStr.contains("晴")) {
            //晴天图片
            icon.setImageResource(R.drawable.weather_icon_qing);
        } else if (weatherStr.contains("多云")) {
            //多云图片
            icon.setImageResource(R.drawable.weather_icon_yun);
        } else if (weatherStr.contains("阴")) {
            //阴天图片
            icon.setImageResource(R.drawable.weather_icon_yin);
        }else if (weatherStr.contains("雷")) {
            //打雷图片
            icon.setImageResource(R.drawable.weather_icon_lei);
        } else if (weatherStr.contains("雨")) {
            if (weatherStr.contains("小")) {
                //小雨图片
                icon.setImageResource(R.drawable.weather_icon_yu);
            } else {
                //雨图片
                icon.setImageResource(R.drawable.weather_icon_yu);
            }
        }


    }


}
