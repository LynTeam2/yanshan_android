package cn.gov.bjys.onlinetrain.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ycl.framework.utils.util.GlideProxy;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.LawsBean;
import cn.gov.bjys.onlinetrain.bean.NativeLawsBean;

/**
 * Created by Administrator on 2018/4/15 0015.
 */
public class DooLawsAdapter extends BaseQuickAdapter<NativeLawsBean,BaseViewHolder> {


    public DooLawsAdapter(int layoutResId, List<NativeLawsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View v = super.getItemView(layoutResId, parent);
        AutoUtils.auto(v);
        return v;
    }


    @Override
    protected void convert(BaseViewHolder helper, NativeLawsBean item) {
//        helper.setText(R.id.title,item.getTitle());
        helper.setText(R.id.title, item.getName());
        ImageView bgImg = helper.getView(R.id.bg_img);
        GlideProxy.loadImgForUrlPlaceHolderDontAnimate(bgImg,item.getIcon(),R.drawable.icon_1084_584);
/*        switch (helper.getAdapterPosition()%5){
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
        }*/


    }
}
