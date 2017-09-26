package cn.gov.bjys.onlinetrain.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.ClassBean;

/**
 * Created by dodozhou on 2017/9/26.
 */
public class DooMultiItemQuickAdapter extends BaseMultiItemQuickAdapter<ClassBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public DooMultiItemQuickAdapter(List<ClassBean> data) {
        super(data);
        addItemType(ClassBean.GRID_TYPE, R.layout.item_class_gridshow_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassBean item) {
      switch (item.getItemType()) {
          case ClassBean.GRID_TYPE:
          helper.setText(R.id.class_name, item.getClassName());
              break;
      }
      }
}
