package cn.gov.bjys.onlinetrain.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.ExamsBean;

/**
 * Created by Administrator on 2018/1/28 0028.
 */
public class DooHomeExamSecondAdapter <T> extends BaseQuickAdapter<T,BaseViewHolder> {
    public DooHomeExamSecondAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }


    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View v = super.getItemView(layoutResId, parent);
        AutoUtils.auto(v);
        return v;
    }
    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if (!(item instanceof ExamsBean))
            return;
        ExamsBean temp = (ExamsBean) item;
//        ImageView img = helper.getView(R.id.icon);
//        GlideProxy.loadImgForFilePlaceHolderDontAnimate(img, new File(AssetsHelper.getYSPicPath(temp.getIcon())) ,R.drawable.icon_189_174);

        helper.setText(R.id.title, temp.getExamName());
        helper.setText(R.id.content, temp.getIntroduction());

        helper.addOnClickListener(R.id.next_linear);
    }
}
