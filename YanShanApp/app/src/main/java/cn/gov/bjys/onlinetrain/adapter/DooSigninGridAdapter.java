package cn.gov.bjys.onlinetrain.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ycl.framework.adapter.SimpleBaseAdapter;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.SignInBean;

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
        mouth.setText(bean.getMouth()+"月");
        TextView day = (TextView) holder.getView(R.id.day);
        day.setText(bean.getDay());
        TextView signType = (TextView) holder.getView(R.id.sign_type);
        switch (bean.getType()){
            case SignInBean.FINISH:
                signType.setBackground(context.getResources().getDrawable(R.drawable.bg_corner30px_normalgraylight3));
                signType.setText("已结束");
                break;

            case SignInBean.ING:
                signType.setBackground(context.getResources().getDrawable(R.drawable.bg_corner30px_normalbluelight1));
                signType.setText("签到");
                break;
            case SignInBean.FUTURE:
                signType.setBackground(context.getResources().getDrawable(R.drawable.bg_corner30px_normalbluelight1));
                signType.setText("签到");
                break;
            case SignInBean.SUC:
                signType.setBackground(context.getResources().getDrawable(R.drawable.bg_corner30px_normalgraylight3));
                signType.setText("签到成功");
                break;
        }

        return convertView;
    }
}
