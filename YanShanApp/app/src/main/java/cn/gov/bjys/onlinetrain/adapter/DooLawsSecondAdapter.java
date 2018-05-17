package cn.gov.bjys.onlinetrain.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.darsh.multipleimageselect.models.Image;
import com.ycl.framework.utils.util.GlideProxy;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.LawContentBean;
import cn.gov.bjys.onlinetrain.bean.LawsSecondBean;

/**
 * Created by dodo on 2018/5/4.
 */

public class DooLawsSecondAdapter extends BaseQuickAdapter<LawContentBean, BaseViewHolder> {

    public DooLawsSecondAdapter(int layoutResId, List<LawContentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LawContentBean item) {
        helper.setText(R.id.title_name, item.getLawName());
        ImageView img = helper.getView(R.id.img);
        GlideProxy.loadImgForUrlPlaceHolderDontAnimate(img, item.getIconPath(), R.drawable.icon_463_216);
    }
}
