package cn.gov.bjys.onlinetrain.act;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.hp.hpl.sparta.Text;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.db.business.QuestionInfoBusiness;
import com.ycl.framework.db.entity.ExamBean;
import com.ycl.framework.db.entity.SaveExamPagerBean;
import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.utils.util.advanced.SpannableStringUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.RadarMarkerView;
import cn.gov.bjys.onlinetrain.act.view.RoundedRectProgressBar;
import cn.gov.bjys.onlinetrain.adapter.DooAnalysisGridAdapter;
import cn.gov.bjys.onlinetrain.api.ZipCallBackListener;
import cn.gov.bjys.onlinetrain.bean.ExamsBean;
import cn.gov.bjys.onlinetrain.task.HomeExamSecondTask;
import cn.gov.bjys.onlinetrain.utils.ExamHelper;

/**
 * Created by Administrator on 2018/4/1 0001.
 */
public class ExamAnalysisActivity3 extends FrameActivity implements View.OnClickListener, ZipCallBackListener {

    public static String TAG = ExamAnalysisActivity3.class.getSimpleName();





    @Bind(R.id.score)
    TextView score;

    @Bind(R.id.ret)
    TextView ret;


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

    @Bind(R.id.history_btn)
    TextView history_btn;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_exam_analysis_newlayout);
    }

    @Override
    public void initViews() {
        super.initViews();
        initZipAllExams();
        initAllDatas();
        initScore();
        initAllBar();
        initGridView();
    }
    HomeExamSecondTask mHomeExamSecondTask;
    List<ExamsBean> mAllExams;
    private void initZipAllExams(){
        mHomeExamSecondTask = new HomeExamSecondTask(this);
        mHomeExamSecondTask.execute();
    }

    private void initAllBar(){
        int panduanProgress = (int) (mTrueFalseErrorSize /(mTrueFalseSize*1.0f) *100);
        panduan_num.setText(mTrueFalseErrorSize +"");
        panduan_bar.setProgress(panduanProgress);


        int danxuanProgress = (int) (mSimpleErrorSize /(mSimpleSize*1.0f) *100);
        danxuan_num.setText(mSimpleErrorSize+"");
        danxuan_bar.setProgress(danxuanProgress);


        int duoxuanProgress = (int) (mMultiErrorSize /(mMultiSize*1.0f) *100);
        duoxuan_num.setText(mMultiErrorSize+"");
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
                    temp.setTitle("继续考试");
                    temp.setHint("成绩不满意? 再战");
                    break;
                default:
                    break;
            }
            mGridDatas.add(temp);
        }
    }

    private void initGridView(){

        dataReady();

        mDooAnalysisGridAdapter = new DooAnalysisGridAdapter(getBaseContext(),mGridDatas);
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
                        if(datas != null && datas.size() > 0) {
                            Bundle mBundle = new Bundle();
                            mBundle.putInt(PracticeActivity.TAG, PracticeActivity.TIXING);
                            mBundle.putParcelableArrayList("PracticeActivityDatas", (ArrayList<? extends Parcelable>) datas);
                            startAct(PracticeActivity.class, mBundle);
                        }else{
                            ToastUtil.showToast("没有错题哦！");
                        }
                        break;
                    case 3:
                        //重新考试
                        long pagerId = mNowPager.getExampagerid();
                        if(mAllExams != null && mAllExams.size() > 0){
                            for(ExamsBean bean : mAllExams){
                                if(bean.getId() == pagerId){
                                    ExamHelper.getInstance().setmExamsBean(bean);
                                    startAct(ExamPrepareActivity.class);
                                    break;
                                }
                            }
                        }
                        break;
                }
            }
        });

    }

    private SaveExamPagerBean mNowPager;

    private void initAllDatas() {
        Intent recIntent = getIntent();
        Bundle recBundle = recIntent.getExtras();
        mNowPager = recBundle.getParcelable(TAG);
        initAllNeedDatas();
    }

    private List<ExamBean> mTrueFalseErrorList = new ArrayList<>();
    private List<ExamBean> mSimpleErrorList = new ArrayList<>();
    private List<ExamBean> mMultiErrorList = new ArrayList<>();

    private int mMultiErrorSize = 0;
    private int mMultiSize = 0;
    private int mSimpleErrorSize = 0;
    private int mSimpleSize = 0;
    private int mTrueFalseSize = 0;
    private int mTrueFalseErrorSize = 0;

    private void initAllNeedDatas() {
        mMultiErrorSize = getSizeFor(mNowPager.getmMultiErrorPager().split(","));
        mMultiSize = getSizeFor(mNowPager.getmMultiPager().split(","));
        mSimpleErrorSize = getSizeFor(mNowPager.getmSimpleErrorPager().split(","));
        mSimpleSize = getSizeFor(mNowPager.getmSimplePager().split(","));
        mTrueFalseSize = getSizeFor(mNowPager.getmTrueFalsePager().split(","));
        mTrueFalseErrorSize = getSizeFor(mNowPager.getmTrueFalseErrorPager().split(","));

        for (String str : mNowPager.getmMultiErrorPager().split(",")) {
            if (TextUtils.isEmpty(str)) {
                continue;
            }
            ExamBean bean = QuestionInfoBusiness.getInstance(this).queryBykey(str);
            mMultiErrorList.add(bean);
        }

        for (String str : mNowPager.getmSimpleErrorPager().split(",")) {
            if (TextUtils.isEmpty(str)) {
                continue;
            }
            ExamBean bean = QuestionInfoBusiness.getInstance(this).queryBykey(str);
            mSimpleErrorList.add(bean);
        }

        for (String str : mNowPager.getmTrueFalseErrorPager().split(",")) {
            if (TextUtils.isEmpty(str)) {
                continue;
            }
            ExamBean bean = QuestionInfoBusiness.getInstance(this).queryBykey(str);
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




    private String getName(int i) {
        String str = "";
        switch (i) {
            case 0:
                str = "判断题";
                break;
            case 1:
                str = "单选题";
                break;
            case 2:
                str = "多选题";
                break;
            default:
                break;

        }
        return str;
    }




    @Override
    public void onClick(View v) {

    }


    @OnClick({R.id.history_btn})
    public void onTabClick(View v) {
        switch (v.getId()) {
            case R.id.history_btn:
                Bundle mBundle = new Bundle();
                mBundle.putLong(ExamHistoryActivity.TAG, mNowPager.getExampagerid());
                startAct(ExamHistoryActivity.class,mBundle);
                break;
        }
    }

    @Override
    public void zipCallBackListener(List datas) {
        mAllExams = datas;
    }

    @Override
    public void zipCallBackSuccess() {

    }

    @Override
    public void zipCallBackFail() {

    }
}
