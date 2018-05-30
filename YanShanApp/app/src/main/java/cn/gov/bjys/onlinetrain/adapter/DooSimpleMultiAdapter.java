package cn.gov.bjys.onlinetrain.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ycl.framework.utils.helper.ContextHelper;
import com.ycl.framework.utils.util.GlideProxy;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.PracticeActivity;
import cn.gov.bjys.onlinetrain.act.PracticePrepareActivity;
import cn.gov.bjys.onlinetrain.bean.YSClassBean;


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
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View v = super.getItemView(layoutResId, parent);
        AutoUtils.auto(v);
        return v;
    }
    @Override
    protected void convert(BaseViewHolder helper, YSClassBean item) {
        switch (helper.getItemViewType()){
            case YSClassBean.GRID_COLUMN_2:
                GlideProxy.loadImgForUrlPlaceHolderDontAnimate((ImageView) helper.getView(R.id.img),item.getmImgUrl(),R.mipmap.ic_launcher);
                helper.setText(R.id.name, item.getName());
                break;
            case YSClassBean.LINEAR_COLUMN_1:
                GlideProxy.loadImgForUrlPlaceHolderDontAnimate((ImageView) helper.getView(R.id.icon),item.getmImgUrl(),getNormalImg(helper.getLayoutPosition()));
                helper.setText(R.id.title, item.getName());
                helper.setText(R.id.content, item.getContent());
                helper.getView(R.id.next_linear).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(ContextHelper.getRequiredActivity(mContext), PracticePrepareActivity.class));
                    }
                });
                break;
        }
    }
    private int getNormalImg(int p){
        int r = 0;
        switch (p){
            case 0:
                r = R.drawable.kecheng_huo;
                break;
            case 1:
                r = R.drawable.kecheng_shigong;
                break;
            case 2:
                r = R.drawable.kecheng_ren;
                break;
            case 3:
                r = R.drawable.kecheng_yunshu;
                break;
            case 4:
                r = R.drawable.kecheng_bangshou;
                break;
            case 5:
                r = R.drawable.kecheng_xiaofang;
                break;
            case 6:
                r = R.drawable.kecheng_dian;
                break;
        }
        return r;
    }
}
