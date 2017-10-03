package cn.gov.bjys.onlinetrain.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.ycl.framework.base.FrameFragment;

import java.util.ArrayList;
import java.util.List;

import cn.gov.bjys.onlinetrain.bean.ExamBean;
import cn.gov.bjys.onlinetrain.fragment.ExaminationFragments.VideoExaminationFragment;

/**
 * Created by dodozhou on 2017/9/28.
 */
public class DooExamStateFragmentAdapter<T> extends FragmentStatePagerAdapter {

    private List<FrameFragment> mFragments;

    private List<T> mDatas;//考试题目bean

    public DooExamStateFragmentAdapter(FragmentManager fm, List<T> datas) {
        super(fm);
        mDatas = datas;
        initNullFragments();
    }

    private void initNullFragments(){
        if(mFragments == null){
            mFragments = new ArrayList<>();
        }
        mFragments.clear();
        for(int i=0; i < mDatas.size(); i++){
            mFragments.add(null);
        }
    }

    @Override
    public Fragment getItem(int position) {
       FrameFragment f =  mFragments.get(position);
        if(null == f){
            //初始化fragment
            T bean = mDatas.get(position);
            //确定bean的类型 取type
            int examType = 0;
            if(bean instanceof ExamBean){
                examType = ((ExamBean) bean).getType();
            }

            switch (examType){
                case ExamBean.VIDEO_EXAM:
                    f = VideoExaminationFragment.newInstance((ExamBean) bean);
                    break;

                default:
                    f = VideoExaminationFragment.newInstance((ExamBean) bean);
                    break;
            }

            mFragments.set(position,f);
        }
        return f;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        FrameFragment  f = mFragments.get(position);
        if(null != f){
            mFragments.set(position,null);
        }
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }
}
