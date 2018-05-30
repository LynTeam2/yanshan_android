package cn.gov.bjys.onlinetrain.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycl.framework.adapter.SimpleBaseAdapter;
import com.ycl.framework.utils.util.GlideProxy;

import java.io.File;
import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.CategoriesBean;
import cn.gov.bjys.onlinetrain.utils.AssetsHelper;


@Deprecated
public class DooHomeClassPracticeAdapter extends SimpleBaseAdapter {
    public DooHomeClassPracticeAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_homeclass_practice_grid;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        CategoriesBean bean = (CategoriesBean) data.get(position);
        ImageView img = (ImageView) holder.getView(R.id.img);
        GlideProxy.loadImgForFilePlaceHolderDontAnimate(img,
                new File(AssetsHelper.getYSPicPath(bean.getIcon())) ,
                R.drawable.icon_189_174);
        TextView mTv = (TextView) holder.getView(R.id.name);
        mTv.setText(bean.getCategoryName());
        return convertView;
    }
}
