package cn.gov.bjys.onlinetrain.act;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.view.TitleHeaderView;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.adapter.DooUserMessageAdapter;

/**
 * Created by dodozhou on 2017/11/8.
 */
public class UserMessageActivity extends FrameActivity implements BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.header)
    TitleHeaderView mHeader;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.rv)
    RecyclerView mRecyclerView;

    DooUserMessageAdapter mDooUserMessageAdapter;


    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_user_message_layout);
    }

    @Override
    public void initData() {
        super.initData();
        mDooUserMessageAdapter = new DooUserMessageAdapter(R.layout.item_user_message_item,null);
        mDooUserMessageAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setAdapter(mDooUserMessageAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }
}
