package cn.gov.bjys.onlinetrain.act;

import android.widget.GridView;

import com.ycl.framework.base.FrameActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.adapter.DooPracticeFenleiAdapter;

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
        mMultiAdapter = new DooPracticeFenleiAdapter(this,getMultiStr());
        multi_gv.setAdapter(mMultiAdapter);
        mPracticeAdapter = new DooPracticeFenleiAdapter(this,getDiStr());
        di_gv.setAdapter(mPracticeAdapter);
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
