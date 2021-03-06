package cn.gov.bjys.onlinetrain.utils;

import com.ycl.framework.db.entity.ExamBean;
import com.ycl.framework.utils.util.FastJSONParser;

import java.util.ArrayList;
import java.util.List;

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
        return mExamsBean.deepClone();
    }

    public  void setmExamsBean(ExamsBean mExamsBean) {
        ExamHelper.mExamsBean = mExamsBean;
    }


    public static List<ExamBean> mExamPagers;

    public  List<ExamBean> getmExamPagers() {
       String str = FastJSONParser.convertObjToJson(mExamPagers);
        return FastJSONParser.getBeanList(str, ExamBean.class);
    }

    public  void setmExamPagers(List<ExamBean> mExamPagers) {
        ExamHelper.mExamPagers = mExamPagers;
    }

    public static long mExamCount;

    public  long getmExamCount() {
        return mExamCount;
    }

    public  void setmExamCount(long mExamCount) {
        ExamHelper.mExamCount = mExamCount;
    }
}
