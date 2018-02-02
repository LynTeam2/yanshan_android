package cn.gov.bjys.onlinetrain.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycl.framework.adapter.SimpleBaseAdapter;
import com.ycl.framework.db.entity.ExamBean;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;

/**
 * Created by dodo on 2017/11/17.
 */

public class DooHomeClassMistakesAdapter extends SimpleBaseAdapter {
    public DooHomeClassMistakesAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_homeclass_mistake_item;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {

        ImageView flagView = (ImageView) holder.getView(R.id.flag_view);
        if(position == data.size()-1){
            flagView.setVisibility(View.GONE);
        }else{
            switch (position % 5){
                case 0:
                    flagView.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_zise));
                    break;
                case 1:
                    flagView.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_huangse));
                    break;
                case 2:
                    flagView.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_lanse));
                    break;
                case 3:
                    flagView.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_hongse));
                    break;
                case 4:
                    flagView.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_danlanse));
                    break;
                default:
                    break;
            }
        }
        ExamBean bean = (ExamBean) data.get(position);
//        ((DooCircleView)holder.getView(R.id.flag_view)).setResColor(bean.getColor());
        ((TextView)holder.getView(R.id.content)).setText(bean.getQuestions());
        return convertView;
    }


}
