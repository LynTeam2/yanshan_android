package cn.gov.bjys.onlinetrain.adapter;

import android.content.Context;
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
        switch (position) {
            case 0:
                img.setImageResource(R.drawable.weather_zaoshang_icon);
                break;
            case 1:
            case 2:
                img .setImageResource(R.drawable.weather_zhongwu_icon);
                break;
            case 3:
                img.setImageResource(R.drawable.weather_zaoshang_icon);
                break;
            case 4:
                img .setImageResource(R.drawable.weather_night_icon);
                break;
            default:
                break;
        }

        TextView tmp = (TextView) holder.getView(R.id.wendu);
        tmp.setText(temp.getTmp()+"°");
        return convertView;
    }
}
