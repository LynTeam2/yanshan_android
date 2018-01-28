package cn.gov.bjys.onlinetrain.fragment.ClassFragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ycl.framework.base.FrameFragment;

import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.PracticePrepareActivity;
import cn.gov.bjys.onlinetrain.adapter.DooHomeClassStudyThirdAdapter;
import cn.gov.bjys.onlinetrain.api.ZipCallBackListener;
import cn.gov.bjys.onlinetrain.bean.CourseBean;
import cn.gov.bjys.onlinetrain.task.HomeClassStudyThirdTask;

/**
 * Created by Administrator on 2018/1/27 0027.
 */
public class HomeClassStudyThirdFragment extends FrameFragment implements SwipeRefreshLayout.OnRefreshListener ,ZipCallBackListener {

    HomeClassStudyThirdTask mHomeClassStudyThirdTask;
    public static HomeClassStudyThirdFragment newInstance(String jn) {
        Bundle args = new Bundle();
        args.putString("jsonName",jn);
        HomeClassStudyThirdFragment fragment = new HomeClassStudyThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_linear_layout, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Bind(R.id.rv)
    RecyclerView mRecyclerView;
    DooHomeClassStudyThirdAdapter mItemAdapter;

    @Override
    protected void initViews() {
        super.initViews();
        mItemAdapter = new DooHomeClassStudyThirdAdapter(R.layout.item_grid_class_layout,null);
        mItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CourseBean bean = (CourseBean) adapter.getData().get(position);
                try {
                    int type = Integer.valueOf(bean.getCourseType());
                    int id = bean.getId();
                    Bundle mBundle = new Bundle();
                    mBundle.putInt("type",type);
                    mBundle.putInt("id", id);
                    startAct(PracticePrepareActivity.class, mBundle);
                }catch (Exception e){

                }
                }
        });
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mItemAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        gainZipDatas();
    }

    @Override
    public void onRefresh() {
        gainZipDatas();
    }

    public void gainZipDatas(){
            Bundle mBundle = getArguments();
            String jn = (String) mBundle.get("jsonName");
            mHomeClassStudyThirdTask = new HomeClassStudyThirdTask(this,jn);
            mHomeClassStudyThirdTask.execute();
    }

    @Override
    public void zipCallBackListener(List datas) {
        mItemAdapter.setNewData(datas);
    }

    @Override
    public void zipCallBackSuccess() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void zipCallBackFail() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}