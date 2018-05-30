package cn.gov.bjys.onlinetrain.task;

import android.content.Context;

import com.ycl.framework.db.business.QuestionInfoBusiness;
import com.ycl.framework.db.entity.ExamBean;

import java.util.ArrayList;
import java.util.List;

import cn.gov.bjys.onlinetrain.utils.ExamDistinguishHelper;



public class InitAllExamTask extends BaseAsyncTask {

    private Context mContext;

    public InitAllExamTask(Context mContext) {
        this.mContext = mContext;
    }

    //安监分类
    private ArrayList<ExamBean> mWeiXianHuaXue, mQiYeHangYe, mYunShu, mJianZhuShiGong, mRenYuanMiJiChangShuo, mTeZhongSheBei, mXiaoFang;
    //难度分类
    private ArrayList<ExamBean> mPrimary, mMiddle, mSenior;//低级中级高级
    //题型分类
    private ArrayList<ExamBean> mTureFalse, mSimple, mMulti;//判断 单选 多选

    private void initList() {
        //安监
        mWeiXianHuaXue = new ArrayList<>();
        mQiYeHangYe = new ArrayList<>();
        mYunShu = new ArrayList<>();
        mJianZhuShiGong = new ArrayList<>();
        mRenYuanMiJiChangShuo = new ArrayList<>();
        mTeZhongSheBei = new ArrayList<>();
        mXiaoFang = new ArrayList<>();

        //难度
        mPrimary = new ArrayList<>();
        mMiddle = new ArrayList<>();
        mSenior = new ArrayList<>();

        //题型
        mTureFalse = new ArrayList<>();
        mSimple = new ArrayList<>();
        mMulti = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(Void... params) {
        List<ExamBean> mList = QuestionInfoBusiness.getInstance(mContext).queryAll();
        initList();
        for (ExamBean bean : mList) {
            //安监动态
            String ajType = bean.getAjType();
            if ("危险化学品".equals(ajType)) {
                mWeiXianHuaXue.add(bean);
            } else if ("企业行业".equals(ajType)) {
                mQiYeHangYe.add(bean);
            } else if ("运输".equals(ajType)) {
                mYunShu.add(bean);
            } else if ("建筑施工".equals(ajType)) {
                mJianZhuShiGong.add(bean);
            } else if ("人员密集场所".equals(ajType)) {
                mRenYuanMiJiChangShuo.add(bean);
            } else if ("特种设备".equals(ajType)) {
                mTeZhongSheBei.add(bean);
            } else if ("消防".equals(ajType)) {
                mXiaoFang.add(bean);
            }

            //难度
            String difficulty = bean.getDifficulty();
            if ("1".equals(difficulty)) {
                mPrimary.add(bean);
            } else if ("2".equals(difficulty)) {
                mMiddle.add(bean);
            } else if ("3".equals(difficulty)) {
                mSenior.add(bean);
            }

            //题型
            String questionType = bean.getQuestionType();
            if ("tf".equals(questionType)) {
                mTureFalse.add(bean);
            } else if ("sc".equals(questionType)) {
                mSimple.add(bean);
            } else if ("mc".equals(questionType)) {
                mMulti.add(bean);
            }
        }

        //安监
        ExamDistinguishHelper.getInstance().setmWeiXianHuaXue(mWeiXianHuaXue);//
        ExamDistinguishHelper.getInstance().setmYunShu(mQiYeHangYe);//
        ExamDistinguishHelper.getInstance().setmJianZhuShiGong(mYunShu);//
        ExamDistinguishHelper.getInstance().setmJianZhuShiGong(mJianZhuShiGong);//
        ExamDistinguishHelper.getInstance().setmRenYuanMiJiChangShuo(mRenYuanMiJiChangShuo);//
        ExamDistinguishHelper.getInstance().setmTeZhongSheBei(mTeZhongSheBei);//
        ExamDistinguishHelper.getInstance().setmXiaoFang(mXiaoFang);//

        //难度
        ExamDistinguishHelper.getInstance().setmPrimary(mPrimary);//
        ExamDistinguishHelper.getInstance().setmMiddle(mMiddle);//
        ExamDistinguishHelper.getInstance().setmSenior(mSenior);//

        //题型

        ExamDistinguishHelper.getInstance().setmTureFalse(mTureFalse);//
        ExamDistinguishHelper.getInstance().setmSimple(mSimple);//
        ExamDistinguishHelper.getInstance().setmMulti(mMulti);//

        return null;
    }

}
