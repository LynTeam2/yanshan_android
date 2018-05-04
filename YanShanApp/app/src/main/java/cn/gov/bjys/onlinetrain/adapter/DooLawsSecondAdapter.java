package cn.gov.bjys.onlinetrain.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

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
            helper.setText(R.id.title_name,item.getLawName());
    }
}
