package cn.gov.bjys.onlinetrain.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycl.framework.adapter.SimpleBaseAdapter;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;

/**
 * Created by dodo on 2017/11/17.
 */

public class DooHomeClassAdapter extends SimpleBaseAdapter {

    public DooHomeClassAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_homeclass_class_grid;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        DooHomeGridViewAdapter.HomeGridBean bean = (DooHomeGridViewAdapter.HomeGridBean) data.get(position);
        ImageView img = (ImageView) holder.getView(R.id.img);
        img.setImageResource(bean.getSrcId());
        TextView mTv = (TextView) holder.getView(R.id.name);
        mTv.setText(bean.getName());
        return convertView;
    }

}
