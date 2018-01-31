package cn.gov.bjys.onlinetrain.utils;

import com.ycl.framework.db.entity.ExamBean;

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
    private List<ExamBean>
    //难度分类
    private List<ExamBean>
    //题型分类
    private List<ExamBean>


}
