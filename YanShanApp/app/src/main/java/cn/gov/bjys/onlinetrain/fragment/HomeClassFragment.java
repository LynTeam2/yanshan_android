package cn.gov.bjys.onlinetrain.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.ycl.framework.base.FrameFragment;
import com.ycl.framework.db.business.ExamPagerInfoBusiness;
import com.ycl.framework.db.business.QuestionInfoBusiness;
import com.ycl.framework.db.entity.ExamBean;
import com.ycl.framework.db.entity.SaveExamPagerBean;
import com.ycl.framework.utils.util.ToastUtil;
import com.ycl.framework.view.TitleHeaderView;
import com.zls.www.statusbarutil.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.ExamHistoryActivity;
import cn.gov.bjys.onlinetrain.act.PracticeActivity;
import cn.gov.bjys.onlinetrain.act.view.RadarMarkerView;
import cn.gov.bjys.onlinetrain.utils.YSUserInfoManager;


public class HomeClassFragment extends FrameFragment implements View.OnClickListener{

    @Bind(R.id.header)
    TitleHeaderView mHeader;


    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mTfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

        View view = inflater.inflate(R.layout.fragment_score_layout, container, false);
        StatusBarUtil.addStatusForFragment(getActivity(), view.findViewById(R.id.status_bar_layout));
        view.findViewById(R.id.status_bar_layout).setBackgroundResource(R.color.transparent);
        return view;
    }



    @Bind(R.id.score_layout)
    LinearLayout score_layout;

    @Bind(R.id.no_score_layout)
    RelativeLayout no_score_layout;

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

    @OnClick({R.id.bg_change, R.id.value_gone, R.id.switch_animate, R.id.history_btn})
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
                Bundle mBundle = new Bundle();
                if(checkNowPagerErr("暂无历史纪录")) {
                    return;
                }
                mBundle.putLong(ExamHistoryActivity.TAG, mNowPager.getExampagerid());
                startAct(ExamHistoryActivity.class,mBundle);
                break;
        }
    }

     private boolean checkNowPagerErr(String hintStr){
         if(mNowPager == null){
             ToastUtil.showToast(hintStr);
             return true;
         }
         return  false;
     }


    @Override
    public void initViews() {
        super.initViews();
        mHeader.hideLeftImg();
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if(visible){
            List<SaveExamPagerBean> mAllPagers = ExamPagerInfoBusiness.getInstance(getActivity()).queryAllBykey(YSUserInfoManager.getsInstance().getUserId());
            if(mAllPagers.size() > 0){
                mNowPager = mAllPagers.get(0);
                score_layout.setVisibility(View.VISIBLE);
                no_score_layout.setVisibility(View.GONE);
                initAllDatas();
                initRadarView();
                initScore();
                initContentLayout();
            }else{
                score_layout.setVisibility(View.GONE);
                no_score_layout.setVisibility(View.VISIBLE);
            }


        }
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
            ExamBean bean = QuestionInfoBusiness.getInstance(getActivity()).queryBykey(str);
            mMultiErrorList.add(bean);
        }

        for (String str : mNowPager.getmSimpleErrorPager().split(",")) {
            if (TextUtils.isEmpty(str)) {
                continue;
            }
            ExamBean bean = QuestionInfoBusiness.getInstance(getActivity()).queryBykey(str);
            mSimpleErrorList.add(bean);
        }

        for (String str : mNowPager.getmTrueFalseErrorPager().split(",")) {
            if (TextUtils.isEmpty(str)) {
                continue;
            }
            ExamBean bean = QuestionInfoBusiness.getInstance(getActivity()).queryBykey(str);
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
        RadarMarkerView mv = new RadarMarkerView(getActivity(), R.layout.radar_markerview);
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
        int value = 0;
        for (int i = 0; i < cnt; i++) {
            switch (i) {
                case 0:
                    String[] strs1 = mNowPager.getmSimpleErrorPager().split(",");
                    if (strs1.length == 1 && TextUtils.isEmpty(strs1[0])) {
                        value = 0;
                    } else {
                        value = strs1.length;
                    }
                    yVals2.add(new RadarEntry(value, i));
                    break;
                case 1:
                    String[] strs2 = mNowPager.getmTrueFalseErrorPager().split(",");
                    if (strs2.length == 1 && TextUtils.isEmpty(strs2[0])) {
                        value = 0;
                    } else {
                        value = strs2.length;
                    }
                    yVals2.add(new RadarEntry(value, i));
                    break;
                case 2:
                    String[] strs3 = mNowPager.getmMultiErrorPager().split(",");
                    if (strs3.length == 1 && TextUtils.isEmpty(strs3[0])) {
                        value = 0;
                    } else {
                        value = strs3.length;
                    }
                    yVals2.add(new RadarEntry(value, i));
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
            notdoSize = errors.length;
        }


        score.setText(mNowPager.getmScore() + "分");
        ret.setText("答错" + errSize + "题" +
                ((notdoSize > 0) ? ("未作" + notdoSize + "题") : ("")) + "，" +
                (mNowPager.ismJige() ? "成绩合格" : "成绩不合格"));
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
        View view = View.inflate(getActivity(), R.layout.item_mistakes_analysis_item, null);
        TextView tvName = (TextView) view.findViewById(R.id.name);
        tvName.setText(name);
        TextView tvContent = (TextView) view.findViewById(R.id.content);
        long errorRate = 0;
        if ("判断题".equals(name)) {
            errorRate = (long) (mTrueFalseErrorSize / (mTrueFalseSize * 1.0f) * 100L);
            view.setTag(name);
            view.setOnClickListener(this);
        } else if ("单选题".equals(name)) {
            errorRate = (long) (mSimpleErrorSize / (mSimpleSize * 1.0f) * 100L);
            view.setTag(name);
            view.setOnClickListener(this);
        } else if ("多选题".equals(name)) {
            errorRate = (long) (mMultiErrorSize / (mMultiSize * 1.0f) * 100L);
            view.setTag(name);
            view.setOnClickListener(this);
        }
        tvContent.setText("错题率" + errorRate + "%");
        return view;
    }


    @Override
    public void onClick(View v) {
        String name = (String) v.getTag();
        if ("判断题".equals(name)) {
            if(mTrueFalseErrorList.size() > 0) {
                Bundle mBundle = new Bundle();
                mBundle.putInt(PracticeActivity.TAG, PracticeActivity.TIXING);
                mBundle.putParcelableArrayList("PracticeActivityDatas", (ArrayList<? extends Parcelable>) mTrueFalseErrorList);
                startAct(PracticeActivity.class, mBundle);
            }else{
                ToastUtil.showToast("没有错题哦！");
            }
        } else if ("单选题".equals(name)) {
            if(mSimpleErrorList.size() > 0) {
                Bundle mBundle = new Bundle();
                mBundle.putInt(PracticeActivity.TAG, PracticeActivity.TIXING);
                mBundle.putParcelableArrayList("PracticeActivityDatas", (ArrayList<? extends Parcelable>) mSimpleErrorList);
                startAct(PracticeActivity.class, mBundle);
            }else{
                ToastUtil.showToast("没有错题哦！");
            }
        } else if ("多选题".equals(name)) {
            if(mMultiErrorList.size() > 0) {
                Bundle mBundle = new Bundle();
                mBundle.putInt(PracticeActivity.TAG, PracticeActivity.TIXING);
                mBundle.putParcelableArrayList("PracticeActivityDatas", (ArrayList<? extends Parcelable>) mMultiErrorList);
                startAct(PracticeActivity.class,mBundle);
            }else{
                ToastUtil.showToast("没有错题哦！");
            }
        }
    }


}
