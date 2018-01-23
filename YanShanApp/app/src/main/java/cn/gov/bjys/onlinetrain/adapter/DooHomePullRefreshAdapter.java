package cn.gov.bjys.onlinetrain.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ycl.framework.utils.util.GlideProxy;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.bean.AnjianBean;

/**
 * Created by Administrator on 2017/10/15 0015.
 */
public class DooHomePullRefreshAdapter extends BaseQuickAdapter<AnjianBean, BaseViewHolder> {
    public final static String TAG = DooHomePullRefreshAdapter.class.getSimpleName();
    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
      final   View v = super.getItemView(layoutResId, parent);
        v.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "w = " + v.getWidth()+"  h =" +v.getHeight());
            }
        });
        AutoUtils.auto(v);
        return v;
    }

    public DooHomePullRefreshAdapter(int layoutResId, List<AnjianBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnjianBean item) {
        GlideProxy.loadImgForUrlPlaceHolderDontAnimate((ImageView) helper.getView(R.id.img),item.getImgSrc(),R.drawable.icon_165_165);

//        helper.setText(R.id.title,item.getTitle());
//
//        helper.setText(R.id.content, item.getContent());

    }
}
