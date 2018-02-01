package cn.gov.bjys.onlinetrain.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.db.business.QuestionInfoBusiness;
import com.ycl.framework.db.entity.ExamBean;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.util.ToastUtil;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.HomeClassStudySecondActivity;
import cn.gov.bjys.onlinetrain.act.MorePracticeActivity;
import cn.gov.bjys.onlinetrain.act.PracticeActivity;
import cn.gov.bjys.onlinetrain.act.PracticePrepareActivity;
import cn.gov.bjys.onlinetrain.act.view.TitleHeadNormalOne;
import cn.gov.bjys.onlinetrain.adapter.DooHomeClassAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooHomeClassMistakesAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooHomeClassPracticeAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooHomeGridViewAdapter;
import cn.gov.bjys.onlinetrain.task.InitAllExamTask;
import cn.gov.bjys.onlinetrain.utils.BannerComHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import cn.gov.bjys.onlinetrain.utils.YSUserInfoManager;


public class HomeClassFragment extends FrameFragment {

    @Bind(R.id.header)
    TitleHeadNormalOne mHeader;

    @Bind(R.id.more_class)
    ImageView more_class;

    @Bind(R.id.class_gridview)
    GridView class_gridview;

    @Bind(R.id.more_practice)
    ImageView more_practice;

    @Bind(R.id.practice_gridview)
    GridView practice_gridview;

    @Bind(R.id.more_mistakes)
    ImageView more_mistakes;

    @Bind(R.id.mistake_collection_gridview)
    GridView mistake_collection_gridview;

    @Bind(R.id.banner)
    ConvenientBanner banner;

    @Bind(R.id.no_error_layout)
    TextView no_error_layout;


    public static int[] res = {
            R.drawable.home_page_normal_img,
            R.drawable.home_page_normal_img
    };

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_home_class_layout, container, false);
        StatusBarUtil.addStatusForFragment(getActivity(), view.findViewById(R.id.status_bar_layout));
        view.findViewById(R.id.status_bar_layout).setBackgroundResource(R.color.transparent);
        return view;
    }

    @OnClick({R.id.more_class, R.id.more_practice, R.id.more_mistakes})
    public void onTabClick(View v) {
        switch (v.getId()) {
            case R.id.more_class:
                ToastUtil.showToast("更多课程");
//                startAct(ClassActivity.class);
                startAct(HomeClassStudySecondActivity.class);
                break;
            case R.id.more_practice:
                ToastUtil.showToast("更多练习");
                startAct(MorePracticeActivity.class);
                break;
            case R.id.more_mistakes:
                ToastUtil.showToast("更多错题");
                break;
        }
    }


    @Override
    protected void initViews() {
        super.initViews();
        //init all type exam
        initAllExam();
        //banner
        BannerComHelper.initLocationBanner(banner, res);
        initClassGv();
        initPracticeGv();
        initMistakesGv();
    }

    InitAllExamTask mInitAllExamTask;

    private void initAllExam() {
        mInitAllExamTask = new InitAllExamTask(getActivity());
        mInitAllExamTask.execute();
    }


    DooHomeClassAdapter mDooHomeClassAdapter;

    public void initClassGv() {
        if (mDooHomeClassAdapter == null) {
            mDooHomeClassAdapter = new DooHomeClassAdapter(getActivity(), getDatas());
        }
        class_gridview.setAdapter(mDooHomeClassAdapter);
        class_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startAct(PracticePrepareActivity.class);
            }
        });
    }


    public static List<DooHomeGridViewAdapter.HomeGridBean> getDatas() {
        ArrayList<DooHomeGridViewAdapter.HomeGridBean> beanArrayList = new ArrayList<>();
        //num 1
        DooHomeGridViewAdapter.HomeGridBean bean = new DooHomeGridViewAdapter.HomeGridBean();
        bean.setName("危险化学");
        bean.setSrcId(R.drawable.dangerous_chemistry);
        beanArrayList.add(bean);
        //num 2
        DooHomeGridViewAdapter.HomeGridBean bean2 = new DooHomeGridViewAdapter.HomeGridBean();
        bean2.setName("工业企业");
        bean2.setSrcId(R.drawable.industry);
        beanArrayList.add(bean2);
        //num 3
        DooHomeGridViewAdapter.HomeGridBean bean3 = new DooHomeGridViewAdapter.HomeGridBean();
        bean3.setName("交通运输");
        bean3.setSrcId(R.drawable.transportation);
        beanArrayList.add(bean3);
        //num 4
        DooHomeGridViewAdapter.HomeGridBean bean4 = new DooHomeGridViewAdapter.HomeGridBean();
        bean4.setName("消防管理");
        bean4.setSrcId(R.drawable.fire_control_management);
        beanArrayList.add(bean4);


        return beanArrayList;
    }

    public static List<DooHomeGridViewAdapter.HomeGridBean> getDatas2() {
        ArrayList<DooHomeGridViewAdapter.HomeGridBean> beanArrayList = new ArrayList<>();
        //num 1
        DooHomeGridViewAdapter.HomeGridBean bean = new DooHomeGridViewAdapter.HomeGridBean();
        bean.setName("野外自救");
        bean.setSrcId(R.drawable.dangerous_chemistry);
        beanArrayList.add(bean);
        //num 2
        DooHomeGridViewAdapter.HomeGridBean bean2 = new DooHomeGridViewAdapter.HomeGridBean();
        bean2.setName("防洪防汛");
        bean2.setSrcId(R.drawable.industry);
        beanArrayList.add(bean2);
        //num 3
        DooHomeGridViewAdapter.HomeGridBean bean3 = new DooHomeGridViewAdapter.HomeGridBean();
        bean3.setName("防盗预警");
        bean3.setSrcId(R.drawable.transportation);
        beanArrayList.add(bean3);
        //num 4
        DooHomeGridViewAdapter.HomeGridBean bean4 = new DooHomeGridViewAdapter.HomeGridBean();
        bean4.setName("天气遇冷");
        bean4.setSrcId(R.drawable.fire_control_management);
        beanArrayList.add(bean4);


        return beanArrayList;
    }

    DooHomeClassPracticeAdapter mDooHomeClassPracticeAdapter;

    public void initPracticeGv() {
        if (mDooHomeClassPracticeAdapter == null) {
            mDooHomeClassPracticeAdapter = new DooHomeClassPracticeAdapter(getActivity(), getDatas());
        }
        practice_gridview.setAdapter(mDooHomeClassPracticeAdapter);
        practice_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startAct(PracticePrepareActivity.class);
            }
        });
    }

    DooHomeClassMistakesAdapter mDooHomeClassMistakesAdapter;

    public void initMistakesGv() {
        if (mDooHomeClassMistakesAdapter == null) {
            mDooHomeClassMistakesAdapter = new DooHomeClassMistakesAdapter(getActivity(), getErrorData());
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

    @Override
    public void onVisibilityChanged(boolean visible) {
        if (visible) {
            mDooHomeClassMistakesAdapter.replaceAll(getErrorData());
        }
    }

    private List<ExamBean> getErrorData() {
        String allErrorStr = SavePreference.getStr(getActivity(), YSConst.UserInfo.USER_ERROR_IDS + YSUserInfoManager.getsInstance().getUserId());
        String[] errors = allErrorStr.split(",");
        List<ExamBean> datas = new ArrayList<>();
        for (int i = 0; i < errors.length; i++) {
            if (i >= 6) {
                break;
            }
            if (TextUtils.isEmpty(errors[i])) {
                continue;
            }
            ExamBean bean = QuestionInfoBusiness.getInstance(getActivity()).queryBykey(errors[i]);
            datas.add(bean);
        }
        more_mistakes.setVisibility(datas.size() >= 6 ? View.VISIBLE : View.GONE);
        no_error_layout.setVisibility(datas.size() > 0 ? View.VISIBLE : View.GONE);
        return datas;
    }
}
