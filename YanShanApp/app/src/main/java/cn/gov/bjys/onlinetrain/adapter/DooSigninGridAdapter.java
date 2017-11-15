package cn.gov.bjys.onlinetrain.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ycl.framework.adapter.SimpleBaseAdapter;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.SignInBean;

/**
 * Created by dodozhou on 2017/11/14.
 */
public class DooSigninGridAdapter extends SimpleBaseAdapter {

    public DooSigninGridAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_sign_in_item;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        SignInBean bean = (SignInBean) data.get(position);
        TextView mouth = (TextView) holder.getView(R.id.mouth);
        mouth.setText(bean.getMouth());
        TextView day = (TextView) holder.getView(R.id.day);
        day.setText(bean.getDay());
        TextView signType = (TextView) holder.getView(R.id.sign_type);
        if (bean.getType() == SignInBean.FINISH) {
            signType.setBackground(context.getResources().getDrawable(R.drawable.bg_corner30px_normalgraylight3));
            signType.setText("已结束");
        } else {
            signType.setBackground(context.getResources().getDrawable(R.drawable.bg_corner30px_normalbluelight1));
            signType.setText("签到");
        }
        return convertView;
    }
}
