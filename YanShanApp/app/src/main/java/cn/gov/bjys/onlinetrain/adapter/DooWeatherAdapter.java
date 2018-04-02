package cn.gov.bjys.onlinetrain.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.WeatherInfoBean;

/**
 * Created by dodo on 2018/3/7.
 */

public class DooWeatherAdapter extends BaseQuickAdapter<WeatherInfoBean.detailWeatherInfo, BaseViewHolder> {


    public DooWeatherAdapter(int layoutResId, List<WeatherInfoBean.detailWeatherInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeatherInfoBean.detailWeatherInfo item) {
        ImageView todayIcon = helper.getView(R.id.today);
        if (helper.getAdapterPosition() != 0) {
            todayIcon.setVisibility(View.INVISIBLE);
        }

        helper.setText(R.id.xingqi, fixDate(item.getDate()));

        helper.setText(R.id.type, item.getType());

        helper.setText(R.id.wendu,
                getWenduValue(item.getLow()) + "~" + getWenduValue(item.getHigh())
        );
//                getWenduAverage(item.getHigh(), item.getLow()));

        ImageView icon = helper.getView(R.id.icon);

        setWeatherIcon(icon, item.getType());
    }


    private String fixDate(String inputStr) {
        String outputStr = "";

        int index = inputStr.indexOf("星期");

        outputStr = inputStr.substring(index);

        return outputStr;
    }

    //获取温度int转String值
    private String getWenduValue(String wendu){
        float hWendu = 0;
        int indexH = wendu.indexOf("温");
        String hStr = wendu.substring(indexH+1, wendu.length() - 2).trim();
        try {
            hWendu = Float.valueOf(hStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (int)(hWendu) + "°";
    }


    //获取平均温度
    private String getWenduAverage(String high, String low) {
        float hWendu = 0;
        int indexH = high.indexOf("温");
        String hStr = high.substring(indexH+1, high.length() - 2).trim();


        float lWendu = 0;
        int indexL = low.indexOf("温");
        String lStr = low.substring(indexL+1, low.length() - 2).trim();

        try {
            hWendu = Float.valueOf(hStr);
            lWendu = Float.valueOf(lStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (int)(hWendu + lWendu +0.5f) / 2L + "°";
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
