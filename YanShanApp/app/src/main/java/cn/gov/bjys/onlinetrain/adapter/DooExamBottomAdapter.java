package cn.gov.bjys.onlinetrain.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.ExamXqBean;

public class DooExamBottomAdapter extends BaseMultiItemQuickAdapter<ExamXqBean,BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public DooExamBottomAdapter(List<ExamXqBean> data) {
        super(data);
        addItemType(ExamXqBean.NOMAL, R.layout.item_examxq_normal_item);
        addItemType(ExamXqBean.CHOICE,R.layout.item_examxq_choice_item);
        addItemType(ExamXqBean.RIGHT,R.layout.item_examxq_right_item);
        addItemType(ExamXqBean.FAIL,R.layout.item_examxq_fail_item);
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View view = super.getItemView(layoutResId, parent);
        AutoUtils.autoSize(view);
        return view;
    }

    @Override
    protected void convert(BaseViewHolder helper, ExamXqBean item) {
        switch (helper.getItemViewType()){
            case ExamXqBean.NOMAL:
            case ExamXqBean.CHOICE:
            case ExamXqBean.FAIL:
            case ExamXqBean.RIGHT:
                helper.setText(R.id.content,(1+item.getmPosition())+"");
                break;
        }
    }
}
