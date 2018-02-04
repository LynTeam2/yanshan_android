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
import cn.gov.bjys.onlinetrain.act.HomeClassStudyThirdActivity;
import cn.gov.bjys.onlinetrain.act.MorePracticeActivity;
import cn.gov.bjys.onlinetrain.act.PracticeActivity;
import cn.gov.bjys.onlinetrain.act.PracticePrepareActivity;
import cn.gov.bjys.onlinetrain.act.view.TitleHeadNormalOne;
import cn.gov.bjys.onlinetrain.adapter.DooHomeClassAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooHomeClassMistakesAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooHomeClassPracticeAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooHomeGridViewAdapter;
import cn.gov.bjys.onlinetrain.api.ZipCallBackListener;
import cn.gov.bjys.onlinetrain.bean.CategoriesBean;
import cn.gov.bjys.onlinetrain.task.HomeClassStudySencondTask;
import cn.gov.bjys.onlinetrain.task.InitAllExamTask;
import cn.gov.bjys.onlinetrain.utils.BannerComHelper;
import cn.gov.bjys.onlinetrain.utils.ExamDistinguishHelper;
import cn.gov.bjys.onlinetrain.utils.YSConst;
import cn.gov.bjys.onlinetrain.utils.YSUserInfoManager;


public class HomeClassFragment extends FrameFragment implements  ZipCallBackListener {

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

        //banner
//        BannerComHelper.initLocationBanner(banner, res);
        BannerComHelper.initZipBanner(banner, "banner.json");

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
            mDooHomeClassAdapter = new DooHomeClassAdapter(getActivity(), beanList);
        }
        class_gridview.setAdapter(mDooHomeClassAdapter);
        class_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO 去课程界面
                CategoriesBean bean = (CategoriesBean) mDooHomeClassAdapter.getData().get(position);
                String jn = bean.getJsonName();
                Bundle mBundle = new Bundle();
                mBundle.putString("jsonName",jn);
                mBundle.putString("title",bean.getCategoryName());
                startAct(HomeClassStudyThirdActivity.class,mBundle);
            }
        });
    }





    DooHomeClassPracticeAdapter mDooHomeClassPracticeAdapter;

    public void initPracticeGv() {
        if (mDooHomeClassPracticeAdapter == null) {
            mDooHomeClassPracticeAdapter = new DooHomeClassPracticeAdapter(getActivity(), beanList);
        }
        practice_gridview.setAdapter(mDooHomeClassPracticeAdapter);
        practice_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoriesBean bean = (CategoriesBean) mDooHomeClassAdapter.getData().get(position);
                String ajType = bean.getCategoryName();

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
            gainZipDatas();
            initAllExam();
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
        no_error_layout.setVisibility(datas.size() > 0 ? View.GONE : View.VISIBLE);
        return datas;
    }

    HomeClassStudySencondTask mHomeClassStudySencondTask;
    public void gainZipDatas(){
        mHomeClassStudySencondTask = new HomeClassStudySencondTask(this);
        mHomeClassStudySencondTask.execute();
    }
    List<CategoriesBean> beanList = new ArrayList<>();
    @Override
    public void zipCallBackListener(List datas) {
        beanList.clear();
        if (datas.size() > 4) {
            beanList.addAll(datas.subList(0, 4));
        }else {
            beanList.addAll(datas);
        }
        mDooHomeClassAdapter.replaceAll(beanList);
        mDooHomeClassAdapter.notifyDataSetChanged();
        mDooHomeClassPracticeAdapter.replaceAll(beanList);
        mDooHomeClassPracticeAdapter.notifyDataSetChanged();
        }

    @Override
    public void zipCallBackSuccess() {

    }

    @Override
    public void zipCallBackFail() {

    }


}
