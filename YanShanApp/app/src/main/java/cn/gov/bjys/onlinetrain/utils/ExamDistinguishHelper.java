package cn.gov.bjys.onlinetrain.utils;

import com.ycl.framework.db.entity.ExamBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/31 0031.
 */
public class ExamDistinguishHelper {

    private volatile static ExamDistinguishHelper instance;
    private ExamDistinguishHelper() {

    }
    public static ExamDistinguishHelper getInstance(){
        if (instance == null) {
            synchronized (ExamDistinguishHelper.class) {
                if (instance == null) {
                    instance = new ExamDistinguishHelper();
                }
            }
        }
        return instance;
    }

    //安监分类
    private static ArrayList<ExamBean> mWeiXianHuaXue,mQiYeHangYe,mYunShu,mJianZhuShiGong,mRenYuanMiJiChangShuo,mTeZhongSheBei,mXiaoFang;
    //难度分类
    private static ArrayList<ExamBean> mPrimary, mMiddle,mSenior;//低级中级高级
    //题型分类
    private static ArrayList<ExamBean> mTureFalse,mSimple,mMulti;//判断 单选 多选


    public ArrayList<ExamBean> getmWeiXianHuaXue() {
        return mWeiXianHuaXue;
    }

    public void setmWeiXianHuaXue(ArrayList<ExamBean> mWeiXianHuaXue) {
        this.mWeiXianHuaXue = mWeiXianHuaXue;
    }

    public ArrayList<ExamBean> getmQiYeHangYe() {
        return mQiYeHangYe;
    }

    public void setmQiYeHangYe(ArrayList<ExamBean> mQiYeHangYe) {
        this.mQiYeHangYe = mQiYeHangYe;
    }

    public ArrayList<ExamBean> getmYunShu() {
        return mYunShu;
    }

    public void setmYunShu(ArrayList<ExamBean> mYunShu) {
        this.mYunShu = mYunShu;
    }

    public ArrayList<ExamBean> getmJianZhuShiGong() {
        return mJianZhuShiGong;
    }

    public void setmJianZhuShiGong(ArrayList<ExamBean> mJianZhuShiGong) {
        this.mJianZhuShiGong = mJianZhuShiGong;
    }

    public ArrayList<ExamBean> getmRenYuanMiJiChangShuo() {
        return mRenYuanMiJiChangShuo;
    }

    public void setmRenYuanMiJiChangShuo(ArrayList<ExamBean> mRenYuanMiJiChangShuo) {
        this.mRenYuanMiJiChangShuo = mRenYuanMiJiChangShuo;
    }

    public ArrayList<ExamBean> getmTeZhongSheBei() {
        return mTeZhongSheBei;
    }

    public void setmTeZhongSheBei(ArrayList<ExamBean> mTeZhongSheBei) {
        this.mTeZhongSheBei = mTeZhongSheBei;
    }

    public ArrayList<ExamBean> getmXiaoFang() {
        return mXiaoFang;
    }

    public void setmXiaoFang(ArrayList<ExamBean> mXiaoFang) {
        this.mXiaoFang = mXiaoFang;
    }

    public ArrayList<ExamBean> getmPrimary() {
        return mPrimary;
    }

    public void setmPrimary(ArrayList<ExamBean> mPrimary) {
        this.mPrimary = mPrimary;
    }

    public ArrayList<ExamBean> getmMiddle() {
        return mMiddle;
    }

    public void setmMiddle(ArrayList<ExamBean> mMiddle) {
        this.mMiddle = mMiddle;
    }

    public ArrayList<ExamBean> getmSenior() {
        return mSenior;
    }

    public void setmSenior(ArrayList<ExamBean> mSenior) {
        this.mSenior = mSenior;
    }

    public ArrayList<ExamBean> getmTureFalse() {
        return mTureFalse;
    }

    public void setmTureFalse(ArrayList<ExamBean> mTureFalse) {
        this.mTureFalse = mTureFalse;
    }

    public ArrayList<ExamBean> getmSimple() {
        return mSimple;
    }

    public void setmSimple(ArrayList<ExamBean> mSimple) {
        this.mSimple = mSimple;
    }

    public ArrayList<ExamBean> getmMulti() {
        return mMulti;
    }

    public void setmMulti(ArrayList<ExamBean> mMulti) {
        this.mMulti = mMulti;
    }
}
