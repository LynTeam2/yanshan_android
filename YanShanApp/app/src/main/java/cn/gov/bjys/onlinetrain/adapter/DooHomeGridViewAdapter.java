package cn.gov.bjys.onlinetrain.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycl.framework.adapter.SimpleBaseAdapter;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;

/**
 * Created by Administrator on 2017/10/15 0015.
 */
public class DooHomeGridViewAdapter extends SimpleBaseAdapter {
    public DooHomeGridViewAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_home_grid_layout;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        HomeGridBean bean = (HomeGridBean) data.get(position);
        ImageView img = (ImageView) holder.getView(R.id.img);
        img.setImageResource(bean.getSrcId());
        TextView mTv = (TextView) holder.getView(R.id.name);
        mTv.setText(bean.getName());
        return convertView;
    }

    public static class HomeGridBean{
        private int srcId;
        private String name;

        public int getSrcId() {
            return srcId;
        }

        public void setSrcId(int srcId) {
            this.srcId = srcId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
