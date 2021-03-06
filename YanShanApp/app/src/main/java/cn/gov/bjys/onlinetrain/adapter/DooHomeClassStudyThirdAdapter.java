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
import cn.gov.bjys.onlinetrain.bean.CourseBean;
import cn.gov.bjys.onlinetrain.utils.AssetsHelper;

/**
 * Created by Administrator on 2018/1/27 0027.
 */
public class DooHomeClassStudyThirdAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder> {

    public DooHomeClassStudyThirdAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View v = super.getItemView(layoutResId, parent);
        AutoUtils.auto(v);
        return  v;
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if (!(item instanceof CourseBean))
            return;
        CourseBean temp = (CourseBean) item;
        ImageView img = helper.getView(R.id.img);
/*        GlideProxy.loadImgForFilePlaceHolderDontAnimate(img,
                new File(AssetsHelper.getYSPicPath(temp.getIcon())) ,
                R.drawable.icon_463_216);*/

        GlideProxy.loadImgForUrlPlaceHolderDontAnimate(img,
                temp.getIcon() ,
                R.drawable.icon_463_216);
        helper.setText(R.id.name, temp.getCourseName());
    }
}