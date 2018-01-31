package cn.gov.bjys.onlinetrain.act;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.db.entity.ExamBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.RadarMarkerView;
import cn.gov.bjys.onlinetrain.bean.ExamsBean;
import cn.gov.bjys.onlinetrain.utils.ExamHelper;

/**
 * Created by dodozhou on 2017/10/30.
 */
public class ExamAnalysisActivity extends FrameActivity implements View.OnClickListener{

    public static String TAG = ExamAnalysisActivity.class.getSimpleName();


    protected Typeface mTfLight;

    @Bind(R.id.radar_chart)
    RadarChart mRadarChart;

    @Bind(R.id.bg_change)
    Button bg_change;
    @Bind(R.id.value_gone)
    Button value_gone;
    @Bind(R.id.switch_animate)
    Button switch_animate;


    @Bind(R.id.question_layout)
    LinearLayout content_layout;


    @Bind(R.id.score)
    TextView score;

    @Bind(R.id.ret)
    TextView ret;

    @Bind(R.id.history_btn)
    TextView history_btn;

    @OnClick({R.id.bg_change, R.id.value_gone, R.id.switch_animate,R.id.history_btn})
    public void onTabClick(View v) {
        switch (v.getId()) {
            case R.id.bg_change: {
                ArrayList<IRadarDataSet> sets = (ArrayList<IRadarDataSet>) mRadarChart.getData()
                        .getDataSets();
                for (IRadarDataSet set : sets) {
                    if (set.isDrawFilledEnabled())
                        set.setDrawFilled(false);
                    else
                        set.setDrawFilled(true);
                }
                mRadarChart.invalidate();
                break;
            }
            case R.id.value_gone: {
                for (IDataSet<?> set : mRadarChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mRadarChart.invalidate();
                break;
            }
            case R.id.switch_animate: {
                if (mRadarChart.isRotationEnabled())
                    mRadarChart.setRotationEnabled(false);
                else
                    mRadarChart.setRotationEnabled(true);
                mRadarChart.invalidate();
                break;
            }

            case R.id.history_btn:
                startAct(ExamHistoryActivity.class);
                break;
        }
    }


    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_exam_analysis_layout);
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
    }

    @Override
    public void initViews() {
        super.initViews();
        initAllDatas();
        initRadarView();
        initScore();
        initContentLayout();
    }

    ExamsBean mExamsBean;

    List<ExamBean> rightNums = new ArrayList<>();
    List<ExamBean> errorNums = new ArrayList<>();
    List<ExamBean> notDoNums = new ArrayList<>();


    List<ExamBean> multipleNums = new ArrayList<>();
    List<ExamBean> simpleNums = new ArrayList<>();
    List<ExamBean> truefalseNums = new ArrayList<>();


    List<ExamBean> multipleErrorNums = new ArrayList<>();
    List<ExamBean> simpleErrorNums = new ArrayList<>();
    List<ExamBean> truefalseErrorNums = new ArrayList<>();

    private int scoreNum = 0;

    private void initAllDatas() {
        rightNums.clear();
        errorNums.clear();
        notDoNums.clear();

        multipleNums.clear();
        simpleNums.clear();
        truefalseNums.clear();

        multipleErrorNums.clear();
        simpleErrorNums.clear();
        truefalseErrorNums.clear();



        mExamsBean = ExamHelper.getInstance().getmExamsBean();
        List<ExamBean> mExamPagers = ExamHelper.getInstance().getmExamPagers();
        for (ExamBean examBean : mExamPagers) {
           String questionType = examBean.getQuestionType();
           if("multiplechoice".equals(questionType)){
                multipleNums.add(examBean);
               switch (examBean.getDoRight()) {
                   case ExamBean.ERROR:
                       multipleErrorNums.add(examBean);
                       break;
               }
           }else if("simplechoice".equals(questionType)){
                simpleNums.add(examBean);
               switch (examBean.getDoRight()) {
                   case ExamBean.ERROR:
                       simpleErrorNums.add(examBean);
                       break;
               }
           }else if("truefalse".equals(questionType)){
                truefalseNums.add(examBean);
                switch (examBean.getDoRight()) {
                   case ExamBean.ERROR:
                       truefalseErrorNums.add(examBean);
                       break;
               }
           }

            switch (examBean.getDoRight()) {
                case ExamBean.RIGHT:
                    rightNums.add(examBean);
                    break;
                case ExamBean.ERROR:
                    errorNums.add(examBean);
                    break;
                case ExamBean.NOT_DO:
                    notDoNums.add(examBean);
                    break;

            }
        }

        scoreNum = (int) ((rightNums.size() / (mExamPagers.size() * 1.0f)) * 100);

    }

    private void initRadarView() {
        initRadar();
    }


    public void initRadar() {
        // 描述，在底部
        mRadarChart.getDescription().setEnabled(false);
        Description desc = new Description();
        desc.setText("错题分布网状图");
        mRadarChart.setDescription(desc);
        // 绘制线条宽度，圆形向外辐射的线条
        mRadarChart.setWebLineWidth(1.0f);
        // 内部线条宽度，外面的环状线条
        mRadarChart.setWebLineWidthInner(1.0f);
        // 所有线条WebLine透明度
        mRadarChart.setWebAlpha(255);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        // 点击的时候弹出对应的布局显示数据
        RadarMarkerView mv = new RadarMarkerView(this, R.layout.radar_markerview);
        mv.setChartView(mRadarChart); // For bounds control
        // set the marker to the chart
        mRadarChart.setMarker(mv);

        setData();

        XAxis xAxis = mRadarChart.getXAxis();
        // X坐标值字体样式
        xAxis.setTypeface(mTfLight);
        // X坐标值字体大小
        xAxis.setTextSize(14f);

        YAxis yAxis = mRadarChart.getYAxis();
        //设定Y坐标的最大值
        yAxis.setAxisMaximum(100);
        // Y坐标值字体样式
        yAxis.setTypeface(mTfLight);
        // Y坐标值标签个数
        yAxis.setLabelCount(3, true);
        // Y坐标值字体大小
        yAxis.setTextSize(8f);


        xAxis.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mParties[(int) value % mParties.length];
            }
        });
        // Y坐标值是否从0开始
        yAxis.setStartAtZero(true);
//        Legend l = mRadarChart.getLegend();
//        // 图例位置
//        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
//        // 图例字体样式
//        l.setTypeface(mTfLight);
//        // 图例X间距
//        l.setXEntrySpace(2f);
//        // 图例Y间距
//        l.setYEntrySpace(1f);
        Legend l = mRadarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        // 图例字体样式
        l.setTypeface(mTfLight);
        // 图例X间距
        l.setXEntrySpace(7f);
        // 图例Y间距
        l.setYEntrySpace(5f);


    }


    private String[] mParties = new String[]{
            "单选", "判断", "多选"
    };

    public void setData() {

        float mult = 50;
        int cnt = 3; // 不同的维度Party A、B、C...总个数

        // Y的值，数据填充
        ArrayList<RadarEntry> yVals1 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> yVals2 = new ArrayList<RadarEntry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < cnt; i++) {
            yVals1.add(new RadarEntry((int) (Math.random() * mult) + mult / 2, i));
        }

        for (int i = 0; i < cnt; i++) {
            switch (i){
                case 0:
                    yVals1.add(new RadarEntry(simpleErrorNums.size(), i));
                    break;
                case 1:
                    yVals1.add(new RadarEntry(truefalseErrorNums.size(), i));
                    break;
                case 2:
                    yVals1.add(new RadarEntry(multipleErrorNums.size(), i));
                    break;
            }
        }

        // Party A、B、C..
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < cnt; i++)
            xVals.add(mParties[i % mParties.length]);

//        RadarDataSet set1 = new RadarDataSet(yVals1, "Set 1");
//        // Y数据颜色设置
//        set1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
//        // 是否实心填充区域
//        set1.setDrawFilled(true);
//        // 数据线条宽度
//        set1.setLineWidth(2f);

        RadarDataSet set2 = new RadarDataSet(yVals2, "错题分布");
        set2.setColor(getResources().getColor(R.color.normal_blue));
        set2.setDrawFilled(true);
        set2.setLineWidth(2f);
        //画出小圆圈
        set2.setDrawHighlightCircleEnabled(true);
        //取消横竖的指示线
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
//        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        // 数据字体样式
        data.setValueTypeface(mTfLight);
        // 数据字体大小
        data.setValueTextSize(8f);
        // 是否绘制Y值到图表
        data.setDrawValues(true);

        mRadarChart.setData(data);
        mRadarChart.invalidate();
    }

    public void initScore() {
        //TODO 数据确定后接入
        score.setText(scoreNum+"分");
        ret.setText("答错"+errorNums.size()+"题" +
                        (notDoNums.size() > 0 ? "未作"+notDoNums+"题":"") +
                "，" +
                //TODO 这里需要修改不是id   等具体数据过来即可
                (scoreNum > mExamsBean.getId()?"成绩合格":"成绩不合格"));
    }


    public void initContentLayout() {
        //TODO 数据确定后接入
        for (int i = 0; i < 3; i++) {
            content_layout.addView(getSimpleLayout(getName(i)));
        }
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

    public View getSimpleLayout(String name) {
        View view = View.inflate(this, R.layout.item_mistakes_analysis_item, null);
        TextView tvName = (TextView) view.findViewById(R.id.name);
        tvName.setText(name);
        TextView tvContent = (TextView) view.findViewById(R.id.content);
        long errorRate = 0;
        if("判断题".equals(name)){
            errorRate = (long) (truefalseErrorNums.size()/(truefalseNums.size()*1.0f) * 100L);
            view.setTag(name);
            view.setOnClickListener(this);
        }else if("单选题".equals(name)){
            errorRate = (long) (simpleErrorNums.size()/(simpleNums.size()*1.0f) * 100L);
            view.setTag(name);
            view.setOnClickListener(this);
        }else if("多选题".equals(name)){
            errorRate = (long) (multipleErrorNums.size()/(multipleNums.size()*1.0f) * 100L);
            view.setTag(name);
            view.setOnClickListener(this);
        }
        tvContent.setText("错题率"+errorRate+"%");
        return view;
    }


    @Override
    public void onClick(View v) {
     String name = (String) v.getTag();
        if("判断题".equals(name)){

        }else if("单选题".equals(name)){

        }else if("多选题".equals(name)){

        }
    }
}
