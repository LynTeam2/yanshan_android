package cn.gov.bjys.onlinetrain.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hp.hpl.sparta.Text;
import com.ycl.framework.adapter.SimpleBaseAdapter;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;

/**
 * Created by Administrator on 2018/1/29 0029.
 */
public class DooPracticeFenleiAdapter extends SimpleBaseAdapter {

    public DooPracticeFenleiAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_practice_fenlei_item;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        String contentStr = (String) data.get(position);

        TextView num = (TextView) holder.getView(R.id.num);
        num.setText((position+1)+"");
        TextView content = (TextView) holder.getView(R.id.content);
        content.setText(contentStr);
        return convertView;
    }

}
