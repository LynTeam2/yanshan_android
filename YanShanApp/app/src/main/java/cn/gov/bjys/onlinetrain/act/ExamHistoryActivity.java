package cn.gov.bjys.onlinetrain.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.db.business.ExamPagerInfoBusiness;
import com.ycl.framework.db.entity.SaveExamPagerBean;
import com.ycl.framework.view.TitleHeaderView;

import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.adapter.DooExamHistoryAdapter;
import cn.gov.bjys.onlinetrain.utils.YSUserInfoManager;

/**
 * Created by dodo on 2018/1/31.
 */

public class ExamHistoryActivity extends FrameActivity {

    public final static String TAG = ExamHistoryActivity.class.getSimpleName();

    @Bind(R.id.header)
    TitleHeaderView header;

    @Bind(R.id.practice_nums)
    TextView practice_nums;
    @Bind(R.id.jige_nums)
    TextView jige_nums;
    @Bind(R.id.kaoshi_nums)
    TextView kaoshi_nums;
    @Bind(R.id.height_nums)
    TextView height_nums;
    @Bind(R.id.rv)
    RecyclerView rv;
    DooExamHistoryAdapter mDooExamHistoryAdapter;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_exam_history_layout);
    }


    private long mExamId;
    List<SaveExamPagerBean> mHistoryDatas;
    @Override
    public void initViews() {
        super.initViews();
        Intent recIntent = getIntent();
        Bundle recBundle = recIntent.getExtras();
        mExamId = recBundle.getLong(TAG);
        mHistoryDatas = ExamPagerInfoBusiness.getInstance(this).queryAllBykey(YSUserInfoManager.getsInstance().getUserId());
        int jigeCount = 0;
        long higest = 0;
        for(SaveExamPagerBean bean : mHistoryDatas){
            if(bean.ismJige()){
                jigeCount++;
            }
            higest = Math.max(bean.getmScore(),higest);
        }
        //做题次数
        practice_nums.setText(mHistoryDatas.size()+"");

        //及格次数
        jige_nums.setText(""+jigeCount);

        //考试次数
        kaoshi_nums.setText(""+mHistoryDatas.size());

        //最高分
        height_nums.setText(""+higest);

        initAdapter();
    }
    private void initAdapter(){
        mDooExamHistoryAdapter = new DooExamHistoryAdapter(R.layout.item_exam_history_item,mHistoryDatas);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mDooExamHistoryAdapter);
        mDooExamHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
             SaveExamPagerBean bean = (SaveExamPagerBean) adapter.getData().get(position);
             Bundle mBundle = new Bundle();
             mBundle.putParcelable(ExamAnalysisActivity2.TAG,bean);
             startAct(ExamAnalysisActivity2.class,mBundle);
            }
        });
    }
}
