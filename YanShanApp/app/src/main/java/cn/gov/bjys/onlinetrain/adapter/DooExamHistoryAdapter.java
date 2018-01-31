package cn.gov.bjys.onlinetrain.adapter;

import android.text.format.DateUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ycl.framework.db.entity.SaveExamPagerBean;
import com.ycl.framework.utils.util.DateUtil;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;

/**
 * Created by dodo on 2018/1/31.
 */

public class DooExamHistoryAdapter extends BaseQuickAdapter<SaveExamPagerBean, BaseViewHolder> {

    public DooExamHistoryAdapter(int layoutResId, List<SaveExamPagerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SaveExamPagerBean item) {
        helper.setText(R.id.score, item.getmScore()+"");
        helper.setText(R.id.time_length, item.getUseTimes() + ":00");
        helper.setText(R.id.time, DateUtil.formatNianToFen(item.getCreateTime()));
    }
}
