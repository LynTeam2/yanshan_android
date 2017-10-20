package cn.gov.bjys.onlinetrain.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.gov.bjys.onlinetrain.bean.ExamXqBean;

/**
 * Created by dodozhou on 2017/10/20.
 */
public class DooExamBottomAdapter extends BaseMultiItemQuickAdapter<ExamXqBean,BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public DooExamBottomAdapter(List<ExamXqBean> data) {
        super(data);
        addItemType(ExamXqBean.NOMAL,);
        addItemType(ExamXqBean.CHOICE,);
        addItemType(ExamXqBean.RIGHT,);
        addItemType(ExamXqBean.FAIL,);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExamXqBean item) {

    }
}
