package cn.gov.bjys.onlinetrain.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.utils.util.ToastUtil;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.DooItemTitleLayout;
import cn.gov.bjys.onlinetrain.act.view.TitleHeadNormalOne;
import cn.gov.bjys.onlinetrain.adapter.DooHomeClassAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooHomeClassMistakesAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooHomeClassPracticeAdapter;
import cn.gov.bjys.onlinetrain.adapter.DooHomeGridViewAdapter;
import cn.gov.bjys.onlinetrain.utils.BannerComHelper;


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



    public static int[] res = {
            R.drawable.home_page_normal_img,
            R.drawable.home_page_normal_img
    };

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_home_class_layout, container, false);
        StatusBarUtil.addStatusForFragment(getActivity(),view.findViewById(R.id.status_bar_layout));
        view.findViewById(R.id.status_bar_layout).setBackgroundResource(R.color.transparent);
        return view;
    }

    @OnClick({R.id.more_class,R.id.more_practice,R.id.more_mistakes})
    public void onTabClick(View v){
        switch (v.getId()){
            case R.id.more_class:
                ToastUtil.showToast("更多课程");
                break;
            case R.id.more_practice:
                ToastUtil.showToast("更多练习");
                break;
            case R.id.more_mistakes:
                ToastUtil.showToast("更多错误");
                break;
        }
    }


    @Override
    protected void initViews() {
        super.initViews();
        //banner
        BannerComHelper.initLocationBanner(banner, res);
        initClassGv();
        initPracticeGv();
        initMistakesGv();
    }


    DooHomeClassAdapter mDooHomeClassAdapter;
    public void initClassGv(){
        if(mDooHomeClassAdapter == null){
            mDooHomeClassAdapter = new DooHomeClassAdapter(getActivity(), DooItemTitleLayout.getDatas());
        }
        class_gridview.setAdapter(mDooHomeClassAdapter);
    }


    DooHomeClassPracticeAdapter mDooHomeClassPracticeAdapter;
    public void initPracticeGv(){
        if(mDooHomeClassPracticeAdapter == null){
            mDooHomeClassPracticeAdapter = new DooHomeClassPracticeAdapter(getActivity(), DooItemTitleLayout.getDatas());
        }
        practice_gridview.setAdapter(mDooHomeClassPracticeAdapter);
    }
    DooHomeClassMistakesAdapter mDooHomeClassMistakesAdapter;
    public void initMistakesGv(){
        if(mDooHomeClassMistakesAdapter == null){
            mDooHomeClassMistakesAdapter = new DooHomeClassMistakesAdapter(getActivity(),getTestData());
        }
        mistake_collection_gridview.setAdapter(mDooHomeClassMistakesAdapter);
    }

    private List<DooHomeClassMistakesAdapter.MistakeBean> getTestData(){
        List<DooHomeClassMistakesAdapter.MistakeBean> lists = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            DooHomeClassMistakesAdapter.MistakeBean bean = new DooHomeClassMistakesAdapter.MistakeBean();
            bean.setColor(i < 3 ? R.color.normal_gray_light1:R.color.normal_red);
            bean.setContent("翻译是在准确、通顺的基础上，把一种语言信息转变成另一种语言信息的行为。翻译是将一种相对陌生的表达方式，转换成相对熟悉的表达方式的过程。其内容有语言、文字、图形、符号和视频翻译。其中，在甲语和乙语中，“翻”是指的这两种语言的转换，即先把一句甲语转换为一句乙语，然后再把");
            lists.add(bean);
        }
        return lists;
    }

}
