package cn.gov.bjys.onlinetrain.act;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.db.entity.ExamBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.adapter.DooPracticeFenleiAdapter;
import cn.gov.bjys.onlinetrain.utils.ExamDistinguishHelper;

/**
 * Created by Administrator on 2018/1/29 0029.
 */
public class MorePracticeActivity  extends FrameActivity{

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_more_practice_layout);
    }

    @Bind(R.id.aj_gv)
    GridView aj_gv;
    DooPracticeFenleiAdapter mAjAdapter;

    @Bind(R.id.multi_gv)
    GridView multi_gv;
    DooPracticeFenleiAdapter mMultiAdapter;

    @Bind(R.id.di_gv)
    GridView di_gv;
    DooPracticeFenleiAdapter mPracticeAdapter;
    @Override
    public void initViews() {
        super.initViews();

        mAjAdapter = new DooPracticeFenleiAdapter(this,getAjStr());
        aj_gv.setAdapter(mAjAdapter);
        aj_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              String ajType = (String) mAjAdapter.getDatas().get(position);

                Bundle mBundle = new Bundle();
                mBundle.putInt(PracticeActivity.TAG,PracticeActivity.TIXING);
                ArrayList<ExamBean> mDatas = null;
                if ("危险化学品".equals(ajType)) {
                    mDatas = ExamDistinguishHelper.getInstance().getmWeiXianHuaXue();
                } else if ("企业行业".equals(ajType)) {
                    mDatas = ExamDistinguishHelper.getInstance().getmQiYeHangYe();
                } else if ("运输".equals(ajType)) {
                    mDatas = ExamDistinguishHelper.getInstance().getmYunShu();
                } else if ("建筑施工".equals(ajType)) {
                    mDatas = ExamDistinguishHelper.getInstance().getmJianZhuShiGong();
                } else if ("人员密集场所".equals(ajType)) {
                    mDatas = ExamDistinguishHelper.getInstance().getmRenYuanMiJiChangShuo();
                } else if ("特种设备".equals(ajType)) {
                    mDatas = ExamDistinguishHelper.getInstance().getmTeZhongSheBei();
                } else if ("消防".equals(ajType)) {
                    mDatas = ExamDistinguishHelper.getInstance().getmXiaoFang();
                }
                mBundle.putParcelableArrayList("PracticeActivityDatas",mDatas);
                startAct(PracticeActivity.class,mBundle);
            }
        });
        mMultiAdapter = new DooPracticeFenleiAdapter(this,getMultiStr());
        multi_gv.setAdapter(mMultiAdapter);
        multi_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String questionType = (String) mMultiAdapter.getDatas().get(position);
                Bundle mBundle = new Bundle();
                mBundle.putInt(PracticeActivity.TAG,PracticeActivity.TIXING);
                ArrayList<ExamBean> mDatas = null;

                if ("判断".equals(questionType)) {
                    mDatas = ExamDistinguishHelper.getInstance().getmTureFalse();
                } else if ("单选".equals(questionType)) {
                    mDatas = ExamDistinguishHelper.getInstance().getmSimple();
                } else if ("多选".equals(questionType)) {
                    mDatas = ExamDistinguishHelper.getInstance().getmMulti();
                }
                mBundle.putParcelableArrayList("PracticeActivityDatas",mDatas);
                startAct(PracticeActivity.class,mBundle);
            }
        });

        mPracticeAdapter = new DooPracticeFenleiAdapter(this,getDiStr());
        di_gv.setAdapter(mPracticeAdapter);
        di_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String difficulty = (String) mPracticeAdapter.getDatas().get(position);
                Bundle mBundle = new Bundle();
                mBundle.putInt(PracticeActivity.TAG,PracticeActivity.TIXING);
                ArrayList<ExamBean> mDatas = null;

                if ("初级".equals(difficulty)) {
                    mDatas = ExamDistinguishHelper.getInstance().getmPrimary();
                } else if ("中级".equals(difficulty)) {
                    mDatas = ExamDistinguishHelper.getInstance().getmMiddle();
                } else if ("高级".equals(difficulty)) {
                    mDatas = ExamDistinguishHelper.getInstance().getmSenior();
                }
                mBundle.putParcelableArrayList("PracticeActivityDatas",mDatas);
                startAct(PracticeActivity.class,mBundle);
            }
        });
    }


    public List<String> getAjStr(){
        List<String> ajStr = new ArrayList<>();
        ajStr.add("危险化学品");
        ajStr.add("企业行业");
        ajStr.add("运输");
        ajStr.add("建筑施工");
        ajStr.add("人员密集场所");
        ajStr.add("特种设备");
        ajStr.add("消防");
        return ajStr;
    }

    public List<String> getMultiStr(){
        List<String> ajStr = new ArrayList<>();
        ajStr.add("单选");
        ajStr.add("多选");
        ajStr.add("判断");
        return ajStr;
    }
    public List<String> getDiStr(){
        List<String> ajStr = new ArrayList<>();
        ajStr.add("初级");
        ajStr.add("中级");
        ajStr.add("高级");
        return ajStr;
    }
}
