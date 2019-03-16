package com.crec.shield.ui2_2.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.crec.shield.R;

import com.crec.shield.contract.ProgressManagementContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.crec22.project.management.ProjectSystemData;
import com.crec.shield.entity.crec22.project.progress.DayAndNightEntity;
import com.crec.shield.global.HtmlConstant;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.presenter.ProgressManagementPresenter;
import com.crec.shield.utils.WebViewUtil;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProgressManagementActivity extends BaseActivity<ProgressManagementPresenter> implements ProgressManagementContract.View {


//    @BindView(R.id.refreshLayout)
//    SmartRefreshLayout refreshLayout;
    @BindView(R.id.day_and_night)
    CombinedChart DayAndNightChart;
    @BindView(R.id.ShieldWorkProgressChart)
    WebView ShieldWorkProgressChart;
    @BindView(R.id.ShieldGanttChart)
    WebView ShieldGanttChart;
    @BindView(R.id.ProgressDesc)
    TextView ProgressDesc;

    @BindView(R.id.currency_circle_num)
    TextView currency_circle_num;
    @BindView(R.id.today_finished)
    TextView today_finished;
    @BindView(R.id.yesterday_finished)
    TextView yesterday_finished;
    @BindView(R.id.week_finished)
    TextView week_finished;

    private CombinedData data;
    private Unbinder unbinder;


    @Override
    public int getLayoutId() {
        return R.layout.activity_projectdetails_progress;
    }

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);

        String keyArg = this.getIntent().getStringExtra("key");
        String keyRiskArg = this.getIntent().getStringExtra("keyRisk");
        /*无后台测试用 请勿删除*/
        if (!(keyArg == null)) {
            Log.d("从MainActivity来的工效排名", keyArg);
        }
        if (!(keyRiskArg == null)) {
            Log.d("从MainActivity来的风险排名", keyRiskArg);
        }

        mPresenter.handleProgressRatioData();       //  获取并处理进度环比图数据
        mPresenter.handleOverallOverviewData();     //  获取并处理总体概览数据
        mPresenter.handleWorkEfficiencyChartData(); //  获取并处理白班夜班工效统计图数据
        mPresenter.handleShieldGanttChartData();    //  获取并处理盾构机运行状态数据
    }


    /**
     * @company vinelinx
     * @author WangQi
     * @date 2019.1.25
     * @description 展示进度环比图
     */
    @SuppressLint("JavascriptInterface")
    @Override
    public void showProgressRatio(final String ShieldWorkProgressData) {
        if(ShieldWorkProgressData != null){
            ShieldWorkProgressChart.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    if (!ShieldWorkProgressChart.getSettings().getLoadsImagesAutomatically()) {
                        ShieldWorkProgressChart.getSettings().setLoadsImagesAutomatically(true);
                    }
                    ShieldWorkProgressChart.loadUrl("javascript:initData(" + ShieldWorkProgressData + ")");
                }
            });
            ShieldWorkProgressChart.setWebChromeClient(new WebChromeClient());
            WebViewUtil.webSetting(ShieldWorkProgressChart.getSettings());
            ShieldWorkProgressChart.loadUrl(HtmlConstant.SHIELD_WORK_PROGRESS_CHART);
            ShieldWorkProgressChart.addJavascriptInterface(this, "android");
            ShieldWorkProgressChart.setWebContentsDebuggingEnabled(true);
        }
    }


    /**
     * @company vinelinx
     * @author WangQi
     * @date 2019.1.25
     * @description 展示总体概览
     */
    @Override
    public void showOverallOverview(ProjectSystemData projectSystemData) {

//        String Expect_data = progressEntity.getExpect_date() == null ? "" : progressEntity.getExpect_date();
//        ProgressDesc.setText(progressEntity.getCurrency_circle_num() + "/" + progressEntity.getTotal_length());
//        currency_circle_num.setText("" + progressEntity.getCurrency_circle_num());
//        today_finished.setText("" + progressEntity.getToday_finished());
//        yesterday_finished.setText(Expect_data);
//        week_finished.setText(Expect_data);

        int current_ring = projectSystemData.getPresentRings();
        int today_ring = projectSystemData.getTodayFinish();
        int yesterday_ring = projectSystemData.getYesterdayFinish();
        int week_ring = projectSystemData.getSevenFinish();
        ProgressDesc.setText(current_ring + "/" + today_ring);
        currency_circle_num.setText(current_ring+"环");
        today_finished.setText(today_ring+"环");
        yesterday_finished.setText(yesterday_ring+"环");
        week_finished.setText(week_ring+"环");
    }


    /**
     * @company vinelinx
     * @author WangQi
     * @date 2019.1.26
     * @description 展示白班夜班工效统计图
     */
    @Override
    public void showWorkEfficiencyChart(List<DayAndNightEntity> dayAndNightEntity) {
        showDataOnChart(dayAndNightEntity);
        Legend legend = DayAndNightChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
    }

    /**
     * 展示数据
     */
    private void showDataOnChart(List<DayAndNightEntity> dayAndNightEntity) {
        List<String> date = new ArrayList<String>();
        float[] day = new float[dayAndNightEntity.size()];
        float[] night = new float[dayAndNightEntity.size()];
        float[] total = new float[dayAndNightEntity.size()];
        for(int i=0; i<dayAndNightEntity.size(); i++){
            date.add(dayAndNightEntity.get(i).getDate());
            day[i] = ((float)dayAndNightEntity.get(i).getDay());
            night[i] = ((float)dayAndNightEntity.get(i).getNight());
            total[i] = ((float)dayAndNightEntity.get(i).getTotal());
        }
        data = new CombinedData();    // 绘制图表数据
        data.setData(getLineData(total));      // 设置折线图数据
        data.setData(getBarData(day, night));  // 设置柱状图数据


        // 设置一页最大显示个数为7，超出部分就滑动
        float ratio = (float) dayAndNightEntity.size()/(float) 7;
        //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
        DayAndNightChart.zoom(ratio,1f,0,0);

//        DayAndNightChart.setTouchEnabled(false);
//        DayAndNightChart.getDescription().setEnabled(false);
//        DayAndNightChart.setDrawGridBackground(false);  // 如果启用，chart绘图区后面的背景矩形将绘制
//        DayAndNightChart.setDrawBarShadow(false);  // 设置是否显示全部柱形条，不填充部分显示灰色
//        DayAndNightChart.setHighlightFullBarEnabled(true);  // BarChart设置是否高亮显示
//        DayAndNightChart.animateX(2000);  // 设置动画


        DayAndNightChart.getDescription().setEnabled(false);
        DayAndNightChart.setBackgroundColor(Color.WHITE);
        DayAndNightChart.setDrawGridBackground(false);
        DayAndNightChart.setDrawBarShadow(false);
        DayAndNightChart.setHighlightFullBarEnabled(false);

        // draw bars behind lines
        DayAndNightChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        Legend l = DayAndNightChart.getLegend();
        l.setWordWrapEnabled(true);  // 设置图例是否重新创建一行
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = DayAndNightChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = DayAndNightChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = DayAndNightChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(data.getXMax() + 0.25f);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(date));

        DayAndNightChart.setData(data);
        DayAndNightChart.invalidate();
    }

    /**
     * 设置折线图绘制数据
     *
     * @return
     */
    private LineData getLineData(float[] total) {

        LineData d = new LineData();
        ArrayList<Entry> entries = new ArrayList<>();

        for (int index = 0; index < total.length; index++)
            entries.add(new Entry(index + 0.5f, total[index]));

        LineDataSet set = new LineDataSet(entries, "总计");
        set.setColor(Color.rgb(230,51,35));  // 红色
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(230,51,35));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(230,51,35));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(230,51,35));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    /**
     * 设置柱状图绘制数据
     *
     * @return
     */
    private BarData getBarData(float[] day,float[] night) {

        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();

        for (int index = 0; index < day.length; index++) {
            entries1.add(new BarEntry(0, day[index]));
            entries2.add(new BarEntry(0, night[index]));
        }

        BarDataSet set1 = new BarDataSet(entries1, "白班");
        set1.setColor(Color.rgb(255,215,0));  // 黄色
        set1.setValueTextColor(Color.rgb(255,215,0));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet set2 = new BarDataSet(entries2, "夜班");
        set2.setColor(Color.rgb(30,144,255));  // 蓝色
        set2.setValueTextColor(Color.rgb(30,144,255));
        set2.setValueTextSize(10f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1, set2);
        d.setBarWidth(barWidth);

        // make this BarData object grouped
        d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }



    /**
     * @company vinelinx
     * @author WangQi
     * @date 2019.1.24
     * @description 展示盾构机运行状态
     */
    @SuppressLint("JavascriptInterface")
    @Override
    public void showShieldGanttChart(final String ShieldGanttData) {

        if(ShieldGanttData != null){
            ShieldGanttChart.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    if (!ShieldGanttChart.getSettings().getLoadsImagesAutomatically()) {
                        ShieldGanttChart.getSettings().setLoadsImagesAutomatically(true);
                    }
                    ShieldGanttChart.loadUrl("javascript:initData(" + ShieldGanttData + ")");
                }
            });
            ShieldGanttChart.setWebChromeClient(new WebChromeClient());
            WebViewUtil.webSetting(ShieldGanttChart.getSettings());
            ShieldGanttChart.loadUrl(HtmlConstant.SHIELD_GANTT_CHART);
            ShieldGanttChart.addJavascriptInterface(this, "android");
            ShieldGanttChart.setWebContentsDebuggingEnabled(true);
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
