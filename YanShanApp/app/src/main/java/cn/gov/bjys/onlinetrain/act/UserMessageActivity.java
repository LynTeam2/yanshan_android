package cn.gov.bjys.onlinetrain.act;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.view.TitleHeaderView;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.adapter.DooUserMessageAdapter;
import cn.gov.bjys.onlinetrain.bean.UserMessageBean;

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
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }
    @Override
    public void initData() {
        super.initData();
        mDooUserMessageAdapter = new DooUserMessageAdapter(R.layout.item_user_message_item,null);
        mDooUserMessageAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setAdapter(mDooUserMessageAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDooUserMessageAdapter.setNewData(prepareDatas());
        mDooUserMessageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startAct(UserMessageDetailActivity.class);
            }
        });
    }

    public List<UserMessageBean>  prepareDatas(){
        List<UserMessageBean> list = new ArrayList<>();
        for(int i=0;i<50;i++){
            UserMessageBean bean = new UserMessageBean();
            bean.setTitle("欢迎加入");
            bean.setContent("系统消息");
            list.add(bean);
        }
    return list;
    }


    @Override
    public void onRefresh() {
        mDooUserMessageAdapter.setNewData(prepareDatas());
        mSwipeRefreshLayout.setRefreshing(false);//关闭旋转
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
