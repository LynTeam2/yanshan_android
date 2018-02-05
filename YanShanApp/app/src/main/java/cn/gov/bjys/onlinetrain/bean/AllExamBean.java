package cn.gov.bjys.onlinetrain.bean;

import com.ycl.framework.db.entity.ExamBean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/3 0003.
 */
public class AllExamBean {
    List<ExamBean> questions;

    public List<ExamBean> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ExamBean> questions) {
        this.questions = questions;
    }
}
