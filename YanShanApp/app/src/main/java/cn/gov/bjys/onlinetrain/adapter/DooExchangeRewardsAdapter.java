package cn.gov.bjys.onlinetrain.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ycl.framework.utils.util.ToastUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.RewardBean;


public class DooExchangeRewardsAdapter extends BaseQuickAdapter<RewardBean,BaseViewHolder> {

    public DooExchangeRewardsAdapter(int layoutResId, List<RewardBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View v = super.getItemView(layoutResId, parent);
        AutoUtils.auto(v);
        return v;
    }
    @Override
    protected void convert(BaseViewHolder helper, final RewardBean item) {
            helper.setText(R.id.name,item.getName());
            helper.getView(R.id.reward_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast("兑换成功"
//                            +item.getName()
                    );
                }
            });
    }
}
