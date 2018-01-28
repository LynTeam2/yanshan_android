package cn.gov.bjys.onlinetrain.utils;

import cn.gov.bjys.onlinetrain.bean.ExamsBean;

/**
 * Created by Administrator on 2018/1/28 0028.
 */
public class ExamHelper {

    private volatile static ExamHelper instance;
    private ExamHelper() {

    }
    public static ExamHelper getInstance(){
        if (instance == null) {
            synchronized (ExamHelper.class) {
                if (instance == null) {
                    instance = new ExamHelper();
                }
            }
        }
        return instance;
    }


    private static ExamsBean mExamsBean;

    public  ExamsBean getmExamsBean() {
        return mExamsBean;
    }

    public  void setmExamsBean(ExamsBean mExamsBean) {
        ExamHelper.mExamsBean = mExamsBean;
    }
}
