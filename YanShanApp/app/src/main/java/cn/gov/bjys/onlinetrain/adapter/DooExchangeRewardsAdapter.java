package cn.gov.bjys.onlinetrain.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ycl.framework.utils.util.ToastUtil;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.RewardBean;

/**
 * Created by dodozhou on 2017/11/14.
 */
public class DooExchangeRewardsAdapter extends BaseQuickAdapter<RewardBean,BaseViewHolder> {

    public DooExchangeRewardsAdapter(int layoutResId, List<RewardBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final RewardBean item) {
            helper.setText(R.id.name,item.getName());
            helper.getView(R.id.reward_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast("兑换"+item.getName());
                }
            });
    }
}
