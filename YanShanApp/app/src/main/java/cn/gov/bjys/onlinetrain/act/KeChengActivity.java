package cn.gov.bjys.onlinetrain.act;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.view.TitleHeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.adapter.DooKechengAdapter;
import cn.gov.bjys.onlinetrain.bean.AnjianBean;
import cn.gov.bjys.onlinetrain.bean.KechengBean;

/**
 * Created by Administrator on 2017/10/15 0015.
 */
public class KeChengActivity extends FrameActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.header)
    TitleHeaderView mHeader;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.rv)
    RecyclerView mRecyclerView;

    private DooKechengAdapter mDooKechengAdapter;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_kecheng_layout);
    }

    @Override
    public void initViews() {
        super.initViews();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mDooKechengAdapter = new DooKechengAdapter(R.layout.item_kecheng_layout,getKechengDatas());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mDooKechengAdapter);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }

    private List<KechengBean> getKechengDatas(){
        List<KechengBean> datas = new ArrayList<>();
        for(int i=0; i < 10; i++){
            KechengBean bean = new KechengBean();
            bean.setContent("测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容");
            bean.setTitle("测试标题+" + i);
            datas.add(bean);
        }
        return datas;
    }
}
