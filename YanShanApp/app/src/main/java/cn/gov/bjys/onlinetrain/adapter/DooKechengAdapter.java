package cn.gov.bjys.onlinetrain.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ycl.framework.utils.util.GlideProxy;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.KechengBean;

/**
 * Created by Administrator on 2017/10/15 0015.
 */
public class DooKechengAdapter extends BaseQuickAdapter<KechengBean, BaseViewHolder> {


    public DooKechengAdapter(int layoutResId, List<KechengBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KechengBean item) {
        GlideProxy.loadImgForUrlPlaceHolderDontAnimate((ImageView) helper.getView(R.id.img),item.getImgSrc(),R.mipmap.ic_launcher);

        helper.setText(R.id.title,item.getTitle());

        helper.setText(R.id.content, item.getContent());

    }
}
