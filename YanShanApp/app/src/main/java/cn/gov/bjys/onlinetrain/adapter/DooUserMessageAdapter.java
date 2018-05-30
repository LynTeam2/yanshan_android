package cn.gov.bjys.onlinetrain.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.UserMessageBean;


public class DooUserMessageAdapter extends BaseQuickAdapter<UserMessageBean, BaseViewHolder> {

    public DooUserMessageAdapter(int layoutResId, List<UserMessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View view = super.getItemView(layoutResId, parent);
        AutoUtils.autoSize(view);
        return view;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserMessageBean item) {
        helper.setText(R.id.title,item.getTitle());
        helper.setText(R.id.content,item.getContent());
    }
}
