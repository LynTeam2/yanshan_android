package cn.gov.bjys.onlinetrain.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycl.framework.adapter.SimpleBaseAdapter;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.weather.Hourly;

/**
 * Created by Administrator on 2018/4/1 0001.
 */
public class DooWeatherHourAdapter extends SimpleBaseAdapter {

    public DooWeatherHourAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_weather_hour_item_layout;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        Hourly temp = (Hourly) data.get(position);
        TextView time = (TextView) holder.getView(R.id.time);
        time.setText(temp.getTime().substring(11, temp.getTime().length()));
        ImageView img = (ImageView) holder.getView(R.id.img);
        String iconTime ="";
        int shijian = 0;//0-24
        try {
            iconTime = temp.getTime().substring(11, temp.getTime().length() - 3);
            shijian = Integer.valueOf(iconTime);
        }catch (Exception e){
            e.printStackTrace();
            Log.d("dodow","icon show err");
        }
        if(shijian <= 6 || shijian >= 18){
            img .setImageResource(R.drawable.weather_night_icon);
        }else if(shijian >= 11 && shijian <= 15){
            img .setImageResource(R.drawable.weather_zhongwu_icon);
        }else {
            img.setImageResource(R.drawable.weather_zaoshang_icon);
        }
        TextView tmp = (TextView) holder.getView(R.id.wendu);
        tmp.setText(temp.getTmp()+"°");
        return convertView;
    }
}
