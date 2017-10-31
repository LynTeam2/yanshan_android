package cn.gov.bjys.onlinetrain.act;

import android.graphics.Typeface;

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
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ycl.framework.base.FrameActivity;

import java.util.ArrayList;

import butterknife.Bind;
import cn.gov.bjys.onlinetrain.R;
import cn.gov.bjys.onlinetrain.act.view.RadarMarkerView;

/**
 * Created by dodozhou on 2017/10/30.
 */
public class ExamAnalysisActivity extends FrameActivity {
    
    protected Typeface mTfLight;
    
    @Bind(R.id.radar_chart)
    RadarChart mRadarChart;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_exam_analysis_layout);
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
    }

    @Override
    public void initViews() {
        super.initViews();
        initRadarView();
    }
    private void initRadarView(){
        initRadar();
    }



    public void initRadar(){
        // 描述，在底部
        mRadarChart.getDescription().setEnabled(false);
        Description desc = new Description();
        desc.setText("我是描述");
        mRadarChart.setDescription(desc);
        // 绘制线条宽度，圆形向外辐射的线条
        mRadarChart.setWebLineWidth(1.5f);
        // 内部线条宽度，外面的环状线条
        mRadarChart.setWebLineWidthInner(1.0f);
        // 所有线条WebLine透明度
        mRadarChart.setWebAlpha(100);

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
        xAxis.setTextSize(12f);

        YAxis yAxis = mRadarChart.getYAxis();
        //设定Y坐标的最大值
        yAxis.setAxisMaximum(100);
        // Y坐标值字体样式
        yAxis.setTypeface(mTfLight);
        // Y坐标值标签个数
        yAxis.setLabelCount(3, true);
        // Y坐标值字体大小
        yAxis.setTextSize(15f);


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












    private String[] mParties = new String[] {
            "单选", "判断", "视频", "阅读", "多选"
    };

    public void setData() {

        float mult = 50;
        int cnt = 5; // 不同的维度Party A、B、C...总个数

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
            yVals2.add(new RadarEntry((int) (Math.random() * mult) + mult / 2, i));
        }

        // Party A、B、C..
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < cnt; i++)
            xVals.add(mParties[i % mParties.length]);

        RadarDataSet set1 = new RadarDataSet(yVals1, "Set 1");
        // Y数据颜色设置
        set1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        // 是否实心填充区域
        set1.setDrawFilled(true);
        // 数据线条宽度
        set1.setLineWidth(2f);

        RadarDataSet set2 = new RadarDataSet(yVals2, "Set 2");
        set2.setColor(ColorTemplate.VORDIPLOM_COLORS[4]);
        set2.setDrawFilled(true);
        set2.setLineWidth(2f);
        //画出小圆圈
        set2.setDrawHighlightCircleEnabled(true);
        //取消横竖的指示线
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);
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
}
