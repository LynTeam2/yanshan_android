package cn.gov.bjys.onlinetrain.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.db.business.ExamPagerInfoBusiness;
import com.ycl.framework.db.business.QuestionInfoBusiness;
import com.ycl.framework.db.entity.ExamBean;
import com.ycl.framework.db.entity.SaveExamPagerBean;
import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.utils.util.advanced.SpannableStringUtils;
import com.ycl.framework.view.TitleHeaderView;
import com.zhy.autolayout.utils.AutoUtils;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.ExamHistoryActivity;
import cn.gov.bjys.onlinetrain.act.ExamPrepareActivity;
import cn.gov.bjys.onlinetrain.act.PracticeActivity;
import cn.gov.bjys.onlinetrain.act.view.RoundedRectProgressBar;
import cn.gov.bjys.onlinetrain.adapter.DooAnalysisGridAdapter;
import cn.gov.bjys.onlinetrain.utils.YSUserInfoManager;

/**
 * Created by Administrator on 2018/4/1 0001.
 */
public class HomeScoreFragment  extends FrameFragment implements View.OnClickListener{

    @Bind(R.id.score)
    TextView score;

    @Bind(R.id.ret)
    TextView ret;

    @Bind(R.id.header)
    TitleHeaderView header;

    @Bind(R.id.panduan_bar)
    RoundedRectProgressBar panduan_bar;
    @Bind(R.id.danxuan_bar)
    RoundedRectProgressBar danxuan_bar;
    @Bind(R.id.duoxuan_bar)
    RoundedRectProgressBar duoxuan_bar;


    @Bind(R.id.panduan_num)
    TextView panduan_num;
    @Bind(R.id.danxuan_num)
    TextView danxuan_num;
    @Bind(R.id.duoxuan_num)
    TextView duoxuan_num;

    @Bind(R.id.grid_view)
    GridView grid_view;
    DooAnalysisGridAdapter mDooAnalysisGridAdapter;


    @OnClick({R.id.history_btn})
    public void onTabClick(View v) {
        switch (v.getId()) {
            case R.id.history_btn:
                if(mNowPager != null) {
                    Bundle mBundle = new Bundle();
                    mBundle.putLong(ExamHistoryActivity.TAG, mNowPager.getExampagerid());
                    startAct(ExamHistoryActivity.class, mBundle);
                }else{
                    ToastUtil.showToast("暂无历史成绩");
                }
                break;
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        header.setLeftViewVisible(View.GONE);
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_score2_layout, container, false);
        StatusBarUtil.addStatusForFragment(getActivity(), view.findViewById(R.id.status_bar_layout));
        view.findViewById(R.id.status_bar_layout).setBackgroundResource(R.color.normal_blue_light1);
        return view;
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if(visible){
            List<SaveExamPagerBean> mAllPagers = ExamPagerInfoBusiness.getInstance(getActivity()).queryAllBykey(YSUserInfoManager.getsInstance().getUserId());
            if(mAllPagers.size() > 0){
                mNowPager = mAllPagers.get(0);

                initAll();
            }else{
                score.setText("暂无成绩");
                ret.setText("完成一次考试即可查看");
            }
        }
    }

    private void initAll(){
        initAllDatas();
        initScore();
        initAllBar();
        initGridView();
    }


    private void initAllBar(){
        int panduanProgress = (int) (mTrueFalseRightSize /(mTrueFalseSize*1.0f) *100);
        panduan_num.setText(mTrueFalseRightSize + "/"+mTrueFalseSize);
        panduan_bar.setProgress(panduanProgress);


        int danxuanProgress = (int) (mSimpleRightSize /(mSimpleSize*1.0f) *100);
        danxuan_num.setText(mSimpleRightSize + "/"+mSimpleSize);
        danxuan_bar.setProgress(danxuanProgress);


        int duoxuanProgress = (int) (mMultiRightSize /(mMultiSize*1.0f) *100);
        duoxuan_num.setText(mMultiRightSize + "/" + mMultiSize);
        duoxuan_bar.setProgress(duoxuanProgress);
    }


    private List<DooAnalysisGridAdapter.ErrorLookBean> mGridDatas = new ArrayList<>();

    private void dataReady(){
        mGridDatas.clear();
        for(int i=0; i<4; i++){
            DooAnalysisGridAdapter.ErrorLookBean temp = new DooAnalysisGridAdapter.ErrorLookBean();
            switch (i){
                case 0:
                    temp.setImgRes(R.drawable.analysis_img1);
                    temp.setTitle("判断题错题");
                    temp.setHint("做错"+mTrueFalseErrorSize + "题");
                    temp.setDatas(mTrueFalseErrorList);
                    break;
                case 1:
                    temp.setImgRes(R.drawable.analysis_img2);
                    temp.setTitle("单选题错题");
                    temp.setHint("做错"+mSimpleErrorSize + "题");
                    temp.setDatas(mSimpleErrorList);
                    break;
                case 2:
                    temp.setImgRes(R.drawable.analysis_img3);
                    temp.setTitle("多选题错题");
                    temp.setHint("做错"+mMultiErrorSize + "题");
                    temp.setDatas(mMultiErrorList);
                    break;
                case 3:
                    temp.setImgRes(R.drawable.analysis_img4);
                    temp.setTitle("所有错题");
                    List<ExamBean> tempErr = new ArrayList<>();
                    tempErr.addAll(mTrueFalseErrorList);
                    tempErr.addAll(mSimpleErrorList);
                    tempErr.addAll(mMultiErrorList);
                    temp.setDatas(tempErr);
                    temp.setHint("做错"+tempErr.size() + "题");
                    break;
                default:
                    break;
            }
            mGridDatas.add(temp);
        }
    }

    private void initGridView(){

        dataReady();

        mDooAnalysisGridAdapter = new DooAnalysisGridAdapter(getContext(),mGridDatas);
        grid_view.setAdapter(mDooAnalysisGridAdapter);

        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DooAnalysisGridAdapter.ErrorLookBean temp = mGridDatas.get(position);
                List<ExamBean> datas = temp.getDatas();
                switch (position){
                    case 0:
                        //判断题
                    case 1:
                        //单选题
                    case 2:
                        //多选题
                    case 3:
                        if(datas != null && datas.size() > 0) {
                            Bundle mBundle = new Bundle();
                            mBundle.putInt(PracticeActivity.TAG, PracticeActivity.CUOTI);
                            mBundle.putInt(PracticeActivity.ERROR_POSTION, 0);
                            mBundle.putParcelableArrayList("PracticeActivityDatas", (ArrayList<? extends Parcelable>) datas);
                            startAct(PracticeActivity.class, mBundle);
                        }else{
                            ToastUtil.showToast("没有错题哦！");
                        }
                        break;
                }
            }
        });

    }

    private SaveExamPagerBean mNowPager;

    private void initAllDatas() {
        initAllNeedDatas();
    }

    private List<ExamBean> mTrueFalseErrorList = new ArrayList<>();
    private List<ExamBean> mSimpleErrorList = new ArrayList<>();
    private List<ExamBean> mMultiErrorList = new ArrayList<>();

    private int mMultiErrorSize = 0;
    private int mMultiSize = 0;
    private int mMultiRightSize = 0;

    private int mSimpleErrorSize = 0;
    private int mSimpleSize = 0;
    private int mSimpleRightSize = 0;

    private int mTrueFalseSize = 0;
    private int mTrueFalseErrorSize = 0;
    private int mTrueFalseRightSize = 0;

    private void initAllNeedDatas() {
        mMultiErrorSize = getSizeFor(mNowPager.getmMultiErrorPager().split(","));
        mMultiSize = getSizeFor(mNowPager.getmMultiPager().split(","));
        mMultiRightSize = getSizeFor(mNowPager.getmMultiRightPager().split(","));

        mSimpleErrorSize = getSizeFor(mNowPager.getmSimpleErrorPager().split(","));
        mSimpleSize = getSizeFor(mNowPager.getmSimplePager().split(","));
        mSimpleRightSize = getSizeFor(mNowPager.getmSimpleRightPager().split(","));


        mTrueFalseSize = getSizeFor(mNowPager.getmTrueFalsePager().split(","));
        mTrueFalseErrorSize = getSizeFor(mNowPager.getmTrueFalseErrorPager().split(","));
        mTrueFalseRightSize = getSizeFor(mNowPager.getmTrueFalseRightPager().split(","));

        mMultiErrorList.clear();
        for (String str : mNowPager.getmMultiErrorPager().split(",")) {
            if (TextUtils.isEmpty(str)) {
                continue;
            }
            ExamBean bean = QuestionInfoBusiness.getInstance(getContext()).queryBykey(str);
            mMultiErrorList.add(bean);
        }

        mSimpleErrorList.clear();
        for (String str : mNowPager.getmSimpleErrorPager().split(",")) {
            if (TextUtils.isEmpty(str)) {
                continue;
            }
            ExamBean bean = QuestionInfoBusiness.getInstance(getContext()).queryBykey(str);
            mSimpleErrorList.add(bean);
        }

        mTrueFalseErrorList.clear();
        for (String str : mNowPager.getmTrueFalseErrorPager().split(",")) {
            if (TextUtils.isEmpty(str)) {
                continue;
            }
            ExamBean bean = QuestionInfoBusiness.getInstance(getContext()).queryBykey(str);
            mTrueFalseErrorList.add(bean);
        }

    }


    private int getSizeFor(String[] strs) {
        if (strs.length == 1 && TextUtils.isEmpty(strs[0])) {
            return 0;
        } else {
            return strs.length;
        }
    }











    public void initScore() {
        //TODO 数据确定后接入
        //错题
        String[] errors = mNowPager.getmErrorPager().split(",");
        int errSize = 0;
        if (errors.length == 1 && TextUtils.isEmpty(errors[0])) {
            errSize = 0;
        } else {
            errSize = errors.length;
        }
        //没做题
        String[] notdo = mNowPager.getmNotDoPager().split(",");
        int notdoSize = 0;
        if (notdo.length == 1 && TextUtils.isEmpty(notdo[0])) {
            notdoSize = 0;
        } else {
            notdoSize = notdo.length;
        }

        SpannableStringUtils.Builder builderScore = new SpannableStringUtils.Builder()
                .append(mNowPager.getmScore()+"")
                .setFontSize(AutoUtils.getPercentWidthSize(180))
                .append("分")
                .setFontSize(AutoUtils.getPercentWidthSize(50));
        score.setText(builderScore.create());

        ret.setText("答错" + errSize + "题" +
                ((notdoSize > 0) ? ("，未作" + notdoSize + "题") : ("")) + "，" +
                (mNowPager.ismJige() ? "成绩合格" : "成绩不合格"));
    }


    @Override
    public void onClick(View v) {

    }
}
