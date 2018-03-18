package cn.gov.bjys.onlinetrain.act;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.db.business.QuestionInfoBusiness;
import com.ycl.framework.db.entity.ExamBean;
import com.ycl.framework.utils.sp.SavePreference;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.adapter.DooHomeClassMistakesAdapter;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import cn.gov.bjys.onlinetrain.utils.YSUserInfoManager;

/**
 * Created by dodo on 2018/2/1.
 */

public class UserMoreErrorActivity extends FrameActivity implements SwipeRefreshLayout.OnRefreshListener{

    @Override
    protected void setRootView() {
            setContentView(R.layout.activity_more_error_layout);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Bind(R.id.mistake_collection_gridview)
    GridView mistake_collection_gridview;
    DooHomeClassMistakesAdapter mDooHomeClassMistakesAdapter;



    @Override
    public void initViews() {
        super.initViews();
        initMistakesGv();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshErrorDatas();
    }

    public void initMistakesGv() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        if (mDooHomeClassMistakesAdapter == null) {
            mDooHomeClassMistakesAdapter = new DooHomeClassMistakesAdapter(this, getErrorData());
        }
        mistake_collection_gridview.setAdapter(mDooHomeClassMistakesAdapter);
        mistake_collection_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               ExamBean bean = (ExamBean) mDooHomeClassMistakesAdapter.getDatas().get(position);
               ArrayList<ExamBean> list = new ArrayList<>();
               list.add(bean);
               Bundle mBundle = new Bundle();
                mBundle.putInt(PracticeActivity.TAG, PracticeActivity.CUOTI);
                mBundle.putParcelableArrayList("PracticeActivityDatas",list);
               startAct(PracticeActivity.class, mBundle);
            }
        });
    }

    private List<ExamBean> getErrorData() {
        String allErrorStr = SavePreference.getStr(this, YSConst.UserInfo.USER_ERROR_IDS + YSUserInfoManager.getsInstance().getUserId());
        String[] errors = allErrorStr.split(",");
        List<ExamBean> datas = new ArrayList<>();
        for (int i = 0; i < errors.length; i++) {
            if (TextUtils.isEmpty(errors[i])) {
                continue;
            }
            ExamBean bean = QuestionInfoBusiness.getInstance(this).queryBykey(errors[i]);
            datas.add(bean);
        }
        return datas;
    }

    @Override
    public void onRefresh() {
        refreshErrorDatas();
        mSwipeRefreshLayout.setRefreshing(false);
    }


    private void refreshErrorDatas(){
        List<ExamBean> datas = getErrorData();
        mDooHomeClassMistakesAdapter.replaceAll(datas);
    }
}
