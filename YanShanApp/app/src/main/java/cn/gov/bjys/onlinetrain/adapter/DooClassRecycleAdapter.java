package cn.gov.bjys.onlinetrain.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.ClassBean;


public class DooClassRecycleAdapter extends BaseQuickAdapter<ClassBean,BaseViewHolder> {

    public DooClassRecycleAdapter(int layoutResId, List<ClassBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View v = super.getItemView(layoutResId, parent);
        AutoUtils.auto(v);
        return v;
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassBean item) {
            helper.setText(R.id.class_name,item.getClassName());
    }
}
