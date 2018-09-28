package cn.gov.bjys.onlinetrain.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ycl.framework.db.entity.SaveExamPagerBean;
import com.ycl.framework.utils.util.DateUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;



public class DooExamHistoryAdapter extends BaseQuickAdapter<SaveExamPagerBean, BaseViewHolder> {

    public DooExamHistoryAdapter(int layoutResId, List<SaveExamPagerBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View v = super.getItemView(layoutResId, parent);
        AutoUtils.auto(v);
        return v;
    }
    @Override
    protected void convert(BaseViewHolder helper, SaveExamPagerBean item) {
        helper.setText(R.id.score, item.getmScore()+"分");
//        helper.setText(R.id.time_length, getUserTime(item.getUseTimes()));
        helper.setText(R.id.time_length, DateUtil.formatNianToFen(item.getCreateTime()));
//        helper.setText(R.id.time, DateUtil.formatNianToFen(item.getCreateTime()));
        helper.setText(R.id.time, item.getExamName());
    }


    private String getUserTime(long miao){
        String ret = "";
        return  ret = miao/60L +":"+  (miao%60 < 10 ? "0"+miao%60 : miao%60);
    }
}
