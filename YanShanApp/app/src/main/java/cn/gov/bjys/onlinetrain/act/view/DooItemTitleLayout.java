package cn.gov.bjys.onlinetrain.act.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ycl.framework.utils.helper.ContextHelper;

import java.util.ArrayList;
import java.util.List;

import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.HomeClassStudySecondActivity;
import cn.gov.bjys.onlinetrain.act.HomeExamSecondActivity;
import cn.gov.bjys.onlinetrain.act.LawsListActivity;
import cn.gov.bjys.onlinetrain.act.MorePracticeActivity;
import cn.gov.bjys.onlinetrain.adapter.DooHomeGridViewAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooHomeGridViewAdapter.HomeGridBean;

/**
 * Created by Administrator on 2017/10/15 0015.
 */
public class DooItemTitleLayout extends DooRootLayout {
    public DooItemTitleLayout(Context context) {
        super(context);
    }

    public DooItemTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DooItemTitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

/*
    public DooItemTitleLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
*/

    GridView mGridView;
    DooHomeGridViewAdapter mDooHomeGVAdapter;
    @Override
    public void initViews() {
        mGridView = (GridView) findViewById(R.id.grid_view);
        mDooHomeGVAdapter = new DooHomeGridViewAdapter(getContext(),getDatas());
        mGridView.setAdapter(mDooHomeGVAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent;
                switch (position){
                    case 0:
                        getContext().startActivity(new Intent(ContextHelper.getRequiredActivity(getContext()), HomeClassStudySecondActivity.class));
                        break;
                    case 1:
                        getContext().startActivity(new Intent(ContextHelper.getRequiredActivity(getContext()), MorePracticeActivity.class));
                        break;
                    case 2:
                        getContext().startActivity(new Intent(ContextHelper.getRequiredActivity(getContext()), HomeExamSecondActivity.class));
                        break;
                    case 3:
//                        ToastUtil.showToast("功能暂未开发");
                        getContext().startActivity(new Intent(ContextHelper.getRequiredActivity(getContext()), LawsListActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    public View getRootView() {
        return View.inflate(getContext(), R.layout.view_item_title_layout, null);
    }

    public static List<HomeGridBean> getDatas(){
        ArrayList<HomeGridBean> beanArrayList = new ArrayList<>();
        //num 1
        HomeGridBean bean = new HomeGridBean();
        bean.setName("课程学习");
        bean.setSrcId(R.drawable.class_study);
        beanArrayList.add(bean);
        //num 2
        HomeGridBean bean4 = new HomeGridBean();
        bean4.setName("专项练习");
        bean4.setSrcId(R.drawable.special_exercise);
        beanArrayList.add(bean4);
        //num 3
        HomeGridBean bean2 = new HomeGridBean();
        bean2.setName("测评考试");
        bean2.setSrcId(R.drawable.examing_test);
        beanArrayList.add(bean2);
        //num 4
        HomeGridBean bean3 = new HomeGridBean();
        bean3.setName("法律法规");
        bean3.setSrcId(R.drawable.police);
        beanArrayList.add(bean3);



        return beanArrayList;
    }


    public void setOnItemClick(AdapterView.OnItemClickListener l){
        mGridView.setOnItemClickListener(l);
    }
}
