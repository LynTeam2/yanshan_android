package cn.gov.bjys.onlinetrain.fragment.ClassFragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ycl.framework.base.FrameFragment;

import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.ExamPrepareActivity;
import cn.gov.bjys.onlinetrain.act.HomeClassStudyThirdActivity;
import cn.gov.bjys.onlinetrain.adapter.DooHomeClassStudySecondAdapter;
import cn.gov.bjys.onlinetrain.api.ZipCallBackListener;
import cn.gov.bjys.onlinetrain.bean.CategoriesBean;
import cn.gov.bjys.onlinetrain.bean.ExamsBean;
import cn.gov.bjys.onlinetrain.task.HomeClassStudySencondTask;
import cn.gov.bjys.onlinetrain.task.HomeExamSecondTask;
import cn.gov.bjys.onlinetrain.utils.ExamHelper;

/**
 * Created by Administrator on 2018/1/28 0028.
 */
public class HomeExamSecondFragment extends FrameFragment implements SwipeRefreshLayout.OnRefreshListener ,ZipCallBackListener {

    HomeExamSecondTask mHomeExamSecondTask;

    public static HomeExamSecondFragment newInstance() {
        
        Bundle args = new Bundle();
        
        HomeExamSecondFragment fragment = new HomeExamSecondFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_linear_layout, container, false);
    }


    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Bind(R.id.rv)
    RecyclerView mRecyclerView;
    DooHomeClassStudySecondAdapter mItemAdapter;

    @Override
    protected void initViews() {
        super.initViews();
        mItemAdapter = new DooHomeClassStudySecondAdapter(R.layout.item_linear_class_layout,null);
        mItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //TODO 去课程界面
                ExamsBean bean = (ExamsBean) adapter.getData().get(position);
                ExamHelper.getInstance().setmExamsBean(bean);
                startAct(ExamPrepareActivity.class);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
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
        mHomeExamSecondTask = new HomeExamSecondTask(this);
        mHomeExamSecondTask.execute();
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

