package cn.gov.bjys.onlinetrain.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.LawsBean;

/**
 * Created by Administrator on 2018/4/15 0015.
 */
public class DooLawsAdapter extends BaseQuickAdapter<LawsBean,BaseViewHolder> {


    public DooLawsAdapter(int layoutResId, List<LawsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LawsBean item) {
        helper.setText(R.id.title,item.getTitle());

        ImageView bgImg = helper.getView(R.id.bg_img);

        switch (helper.getAdapterPosition()%5){
            case 0:
                bgImg.setImageResource(R.drawable.banshizhinan);
            break;
            case 1:
                bgImg.setImageResource(R.drawable.falvfagui);
            break;
            case 2:
                bgImg.setImageResource(R.drawable.gonggaotongzhi);
            break;
            case 3:
                bgImg.setImageResource(R.drawable.kuaiji);
            break;
            case 4:
                bgImg.setImageResource(R.drawable.zhengcefagui);
            break;
        }


    }
}
