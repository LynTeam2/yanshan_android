package cn.gov.bjys.onlinetrain.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ycl.framework.utils.util.GlideProxy;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.YSClassBean;

/**
 * Created by dodo on 2017/10/3.
 */
public class DooSimpleMultiAdapter extends BaseMultiItemQuickAdapter<YSClassBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public DooSimpleMultiAdapter(List<YSClassBean> data) {
        super(data);
        addItemType(YSClassBean.GRID_COLUMN_2,R.layout.item_grid_class_layout);
        addItemType(YSClassBean.LINEAR_COLUMN_1, R.layout.item_linear_class_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, YSClassBean item) {
        switch (helper.getItemViewType()){
            case YSClassBean.GRID_COLUMN_2:
                GlideProxy.loadImgForUrlPlaceHolderDontAnimate((ImageView) helper.getView(R.id.img),item.getmImgUrl(),R.mipmap.ic_launcher);
                helper.setText(R.id.name, item.getName());
                break;
            case YSClassBean.LINEAR_COLUMN_1:
                GlideProxy.loadImgForUrlPlaceHolderDontAnimate((ImageView) helper.getView(R.id.icon),item.getmImgUrl(),R.drawable.icon_190_175);
//                helper.setText(R.id.title, item.getName());
//                helper.setText(R.id.content, item.getContent());
                break;
        }
    }
}
