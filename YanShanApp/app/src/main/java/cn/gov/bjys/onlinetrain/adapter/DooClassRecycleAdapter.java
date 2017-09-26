package cn.gov.bjys.onlinetrain.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.ClassBean;

/**
 * Created by dodozhou on 2017/9/26.
 */
public class DooClassRecycleAdapter extends BaseQuickAdapter<ClassBean,BaseViewHolder> {

    public DooClassRecycleAdapter(int layoutResId, List<ClassBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassBean item) {
            helper.setText(R.id.class_name,item.getClassName());
    }
}
