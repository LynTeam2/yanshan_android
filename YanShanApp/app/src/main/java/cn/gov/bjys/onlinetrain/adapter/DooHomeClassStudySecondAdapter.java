package cn.gov.bjys.onlinetrain.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ycl.framework.utils.util.GlideProxy;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.File;
import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.CategoriesBean;
import cn.gov.bjys.onlinetrain.utils.AssetsHelper;


/**
 * Created by Administrator on 2018/1/27 0027.
 */
public class DooHomeClassStudySecondAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder> {

    public DooHomeClassStudySecondAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }


    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View view = super.getItemView(layoutResId, parent);
        AutoUtils.autoSize(view);
        return view;
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if (!(item instanceof CategoriesBean))
            return;
        CategoriesBean temp = (CategoriesBean) item;
        ImageView img = helper.getView(R.id.icon);
        GlideProxy.loadImgForFilePlaceHolderDontAnimate(img,
                new File(AssetsHelper.getYSPicPath(temp.getIcon())) ,
                R.drawable.icon_189_174);



        helper.setText(R.id.title, temp.getCategoryName());
        helper.setText(R.id.content, temp.getIntroduction());

        helper.addOnClickListener(R.id.next_linear);
    }
}
