package cn.gov.bjys.onlinetrain.utils;

import cn.gov.bjys.onlinetrain.bean.CourseBean;

/**
 * Created by Administrator on 2018/1/28 0028.
 */
public class PracticeHelper {
    private volatile static PracticeHelper instance;
    private PracticeHelper() {

    }
    public static PracticeHelper getInstance(){
        if (instance == null) {
            synchronized (PracticeHelper.class) {
                if (instance == null) {
                    instance = new PracticeHelper();
                }
            }
        }
        return instance;
    }


    private static CourseBean mCourseBean;

    public  CourseBean getmCourseBean() {
        if(mCourseBean == null){
            mCourseBean = new CourseBean();
        }
        return mCourseBean;
    }

    public  void setmCourseBean(CourseBean mCourseBean) {
        PracticeHelper.mCourseBean = mCourseBean;
    }
}
