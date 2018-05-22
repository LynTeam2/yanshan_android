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
import cn.gov.bjys.onlinetrain.bean.ClassStudyBean;
import cn.gov.bjys.onlinetrain.bean.CourseBean;
import cn.gov.bjys.onlinetrain.utils.AssetsHelper;

/**
 * Created by Administrator on 2017/10/15 0015.
 */
public class DooHomeClassStudyAdapter extends BaseQuickAdapter<CourseBean,BaseViewHolder> {
    public DooHomeClassStudyAdapter(int layoutResId, List<CourseBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View v  = super.getItemView(layoutResId, parent);
        AutoUtils.auto(v);
        return v;
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseBean item) {
/*        GlideProxy.loadImgForFilePlaceHolderDontAnimate((ImageView) helper.getView(R.id.img),
                new File(AssetsHelper.getFileName(item.getIcon())),
                R.drawable.icon_465_215);  */
        GlideProxy.loadImgForUrlPlaceHolderDontAnimate((ImageView) helper.getView(R.id.img),
                item.getIcon(),
                R.drawable.icon_465_215);


        helper.setText(R.id.class_name, item.getCourseName());
    }
}
