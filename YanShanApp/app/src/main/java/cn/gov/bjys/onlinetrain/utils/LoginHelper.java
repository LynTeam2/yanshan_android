package cn.gov.bjys.onlinetrain.utils;

import com.ycl.framework.db.business.ExamPagerInfoBusiness;
import com.ycl.framework.db.entity.SaveExamPagerBean;
import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.HRetrofitNetHelper;
import com.ycl.framework.utils.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.gov.bjys.onlinetrain.BaseApplication;
import cn.gov.bjys.onlinetrain.api.BaseResponse;
import cn.gov.bjys.onlinetrain.api.UserApi;
import cn.gov.bjys.onlinetrain.bean.ExamDetailBean;
import cn.gov.bjys.onlinetrain.bean.ExamPagerHistoryBean;
import cn.gov.bjys.onlinetrain.bean.UserBean;
import cn.gov.bjys.onlinetrain.bean.event.LoginEvent;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lisheng.zhou on 2018/8/28.
 */
//获取登录信息并且获取考试信息详情

public class LoginHelper {
    public static void login(final String mUserName, final String mPassword) {

        Observable<BaseResponse<String>> obsLogin;
        obsLogin = HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).
                getSpeUrlService(YSConst.BaseUrl.BASE_URL, UserApi.class).userLogin(HRetrofitNetHelper.createReqJsonBody(MapParamsHelper.getLogin(mUserName, mPassword)));
        obsLogin.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<BaseResponse<String>, Observable<BaseResponse<String>>>() {
                    @Override
                    public Observable<BaseResponse<String>> call(BaseResponse<String> stringBaseResponse) {
                        if ("1".equals(stringBaseResponse.getCode())) {
                            //登陆成功
                            //保存登陆信息
                            saveLoginInfo(mUserName, mPassword);
                            //保存登陆用户信息
                            UserBean.UserParentBean parentBean = FastJSONParser.getBean(stringBaseResponse.getResults(), UserBean.UserParentBean.class);
                            UserBean bean = parentBean.getUser();
                            YSUserInfoManager.getsInstance().saveUserBean(bean);
                            YSUserInfoManager.getsInstance().saveUserId(bean.getId()+"");
                            EventBus.getDefault().post(new LoginEvent(1));
                            return HRetrofitNetHelper.getInstance(BaseApplication.getAppContext()).
                                    getSpeUrlService(YSConst.BaseUrl.BASE_URL, UserApi.class).getUserExamHistory();
                        } else {
                            EventBus.getDefault().post(new LoginEvent(0));
                            ToastUtil.showToast(stringBaseResponse.getMsg());
                            return null;
                        }
                    }
                })
                .subscribe(new Subscriber<BaseResponse<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse<String> stringBaseResponse) {
                        if ("1".equals(stringBaseResponse.getCode())) {
                            String res = stringBaseResponse.getResults();
                            ExamHistorys examHistorys = FastJSONParser.getBean(res,ExamHistorys.class);
                            List<ExamPagerHistoryBean> examList = examHistorys.getExamHistories();
                            saveUserNewPagerInfo(examList);
                        }
                    }
                });

    }

    public  static class ExamHistorys{
        private List<ExamPagerHistoryBean> examHistories;

        public List<ExamPagerHistoryBean> getExamHistories() {
            return examHistories;
        }

        public void setExamHistories(List<ExamPagerHistoryBean> examHistories) {
            this.examHistories = examHistories;
        }
    }

    //保存用户最新考卷信息
    public static void saveUserNewPagerInfo(List<ExamPagerHistoryBean> examList) {
        if(null == examList){
            return;
        }
        List<SaveExamPagerBean> nativeHistory = ExamPagerInfoBusiness.getInstance(BaseApplication.getAppContext()).queryAllBykey(YSUserInfoManager.getsInstance().getUserId());
        for (int i = 0; i < examList.size(); i++) {
            boolean isNewPager = true;
            ExamPagerHistoryBean bean = examList.get(i);
            bean.getExamId();
            for (int k = 0; k < nativeHistory.size(); k++) {
                SaveExamPagerBean temp = nativeHistory.get(k);
                if (bean.getExamId() == temp.getExampagerid()) {
                    isNewPager = false;
                    break;
                }
            }
            if (isNewPager) {
                //新的试卷保存到本地
                SaveExamPagerBean saveExamPagerBean = new SaveExamPagerBean();
                saveExamPagerBean.setUserid(YSUserInfoManager.getsInstance().getUserId());
                saveExamPagerBean.setExampagerid(bean.getExamId());
                saveExamPagerBean.setmScore(bean.getExamScore());
                saveExamPagerBean.setCreateTime(bean.getEndTime());
                saveExamPagerBean.setExamName(bean.getExamName());
                long duration = bean.getEndTime() - bean.getStartTime();//毫秒
                saveExamPagerBean.setUseTimes(duration/1000);//毫秒转秒
                List<ExamDetailBean> pager = bean.getExamDetailList();
                saveExamPager(pager,saveExamPagerBean);
            }

        }
    }
    //存下整张试卷
    public static void saveExamPager(List<ExamDetailBean> pager,SaveExamPagerBean bean) {

        StringBuilder allSb = new StringBuilder();
        StringBuilder errorSb = new StringBuilder();
        StringBuilder notdoSb = new StringBuilder();
        StringBuilder rightSb = new StringBuilder();

        StringBuilder mMultiSb = new StringBuilder();
        StringBuilder mMultiErrorSb = new StringBuilder();
        StringBuilder mMultiRightSb = new StringBuilder();
        StringBuilder mTurefalseSb = new StringBuilder();
        StringBuilder mTurefalseErrorSb = new StringBuilder();
        StringBuilder mTurefalseRightSb = new StringBuilder();
        StringBuilder mSimpleSb = new StringBuilder();
        StringBuilder mSimpleErrorSb = new StringBuilder();
        StringBuilder mSimpleRightSb = new StringBuilder();


        for (int i = 0; i < pager.size(); i++) {
            ExamDetailBean examBean = pager.get(i);
            String uid = examBean.getUid();
            int type = (int) examBean.getResult();//0没做，1正确，2错误
            String questionType = examBean.getQuestionType();
            if (i == 0) {
                allSb.append(uid + "");//allpager没有问题
                switch (type) {
                    case 2:
                        errorSb.append(uid + "");
                        if ("mc".equals(questionType)) {
                            mMultiErrorSb.append(uid + "");
                        } else if ("sc".equals(questionType)) {
                            mSimpleErrorSb.append(uid + "");
                        } else if ("tf".equals(questionType)) {
                            mTurefalseErrorSb.append(uid + "");
                        }
                        break;
                    case 0:
                        notdoSb.append(uid + "");
                        break;
                    case 1:
                        rightSb.append(uid + "");

                        if ("mc".equals(questionType)) {
                            mMultiRightSb.append(uid + "");
                        } else if ("sc".equals(questionType)) {
                            mSimpleRightSb.append(uid + "");
                        } else if ("tf".equals(questionType)) {
                            mTurefalseRightSb.append(uid + "");
                        }

                        break;
                }

                if ("mc".equals(questionType)) {
                    mMultiSb.append(uid + "");
                } else if ("sc".equals(questionType)) {
                    mSimpleSb.append(uid + "");
                } else if ("tf".equals(questionType)) {
                    mTurefalseSb.append(uid + "");
                }

            } else {
                allSb.append("," + uid);
                switch (type) {
                    case 2:
                        errorSb.append("," + uid);
                        if ("mc".equals(questionType)) {
                            mMultiErrorSb.append("," + uid);
                        } else if ("sc".equals(questionType)) {
                            mSimpleErrorSb.append("," + uid);
                        } else if ("tf".equals(questionType)) {
                            mTurefalseErrorSb.append("," + uid);
                        }
                        break;
                    case 0:
                        notdoSb.append("," + uid);
                        break;
                    case 1:
                        rightSb.append("," + uid);

                        if ("mc".equals(questionType)) {
                            mMultiRightSb.append("," + uid);
                        } else if ("sc".equals(questionType)) {
                            mSimpleRightSb.append("," + uid);
                        } else if ("tf".equals(questionType)) {
                            mTurefalseRightSb.append("," + uid);
                        }
                        break;
                }

                if ("mc".equals(questionType)) {
                    mMultiSb.append("," + uid);
                } else if ("sc".equals(questionType)) {
                    mSimpleSb.append("," + uid);
                } else if ("tf".equals(questionType)) {
                    mTurefalseSb.append("," + uid);
                }
            }
        }


        bean.setUserid(YSUserInfoManager.getsInstance().getUserId());
        bean.setmAllPager(DataHelper.clearEmptyString(allSb.toString()));
        bean.setmErrorPager(DataHelper.clearEmptyString(errorSb.toString()));
        bean.setmNotDoPager(DataHelper.clearEmptyString(notdoSb.toString()));
        bean.setmRightPager(DataHelper.clearEmptyString(rightSb.toString()));

        bean.setmMultiPager(DataHelper.clearEmptyString(mMultiSb.toString()));
        bean.setmMultiErrorPager(DataHelper.clearEmptyString(mMultiErrorSb.toString()));
        bean.setmMultiRightPager(DataHelper.clearEmptyString(mMultiRightSb.toString()));

        bean.setmSimplePager(DataHelper.clearEmptyString(mSimpleSb.toString()));
        bean.setmSimpleErrorPager(DataHelper.clearEmptyString(mSimpleErrorSb.toString()));
        bean.setmSimpleRightPager(DataHelper.clearEmptyString(mSimpleRightSb.toString()));

        bean.setmTrueFalsePager(DataHelper.clearEmptyString(mTurefalseSb.toString()));
        bean.setmTrueFalseErrorPager(DataHelper.clearEmptyString(mTurefalseErrorSb.toString()));
        bean.setmTrueFalseRightPager(DataHelper.clearEmptyString(mTurefalseRightSb.toString()));

        //插入数据库
        ExamPagerInfoBusiness.getInstance(BaseApplication.getAppContext()).createOrUpdate(bean);
    }

    private static void saveLoginInfo(String mUserName, String mPassword) {
        SavePreference.save(BaseApplication.getAppContext(), YSConst.UserInfo.KEY_USER_ACCOUNT, mUserName);
        SavePreference.save(BaseApplication.getAppContext(), YSConst.UserInfo.KEY_USER_PASSWORD, mPassword);
    }
}