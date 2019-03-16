package com.crec.shield.ui2_2.activity;

import com.crec.shield.R;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.contract.QualityManagementContract;
import com.crec.shield.demo.LoginActivity;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.presenter.QualityManagementPresenter;
import com.crec.shield.utils.T;
import com.crec.shield.utils.WebViewUtil;
import com.github.mikephil.charting.charts.CombinedChart;
import com.crec.shield.utils.AppUtils;
import com.crec.shield.entity.crec22.project.qualitymanagement.QualityBiasnEntity;

import java.util.ArrayList;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import butterknife.BindView;
import butterknife.OnClick;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

/**
 * @company vinelinx
 * @author zhangding
 * @date 2019.1.25
 */
public class QualityManagementActivity extends BaseActivity<QualityManagementPresenter> implements QualityManagementContract.View {
    //数据绑定
    @BindView(R.id.horizontal_error)
    CombinedChart HorizontalErrorChart;
    @BindView(R.id.vertical_error)
    CombinedChart VerticalErrorChart;
    @BindView(R.id.SegmentChart)
    WebView GroundSettingChart;
    @BindView(R.id.quality_table)
    LinearLayout qualityTable;


    private QualityBiasnEntity qualityBiasnEntity = new QualityBiasnEntity();
    private CombinedData horizontalData;
    private CombinedData verticalData;

    public static final String GROUND_SETTING_CHART = "file:///android_asset/html/qualitymanagement/QualityManagement.html";

    @Override
    public int getLayoutId() {
        return R.layout.activity_quality_management;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getData();
        initGroundSetting();
    }

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void showData(QualityBiasnEntity data) {
        qualityBiasnEntity = data;
        initHorizontalError(qualityBiasnEntity);
    }


    /**
     * @company vinelinx
     * @author zhangding
     * @date 2019.1.25
     * @description 管片水平偏差
     */
    private void initHorizontalError(QualityBiasnEntity qualityBiasnEntity) {
        showHorizontalDataOnChart(qualityBiasnEntity);
        showVerticalDataOnChart(qualityBiasnEntity);
        Legend legendH = HorizontalErrorChart.getLegend();
        legendH.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legendH.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        Legend legendV = VerticalErrorChart.getLegend();
        legendV.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legendV.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
    }


    /**
     *@company vinelinx
     * @author zhangding
     * @date 2019.1.28
     * @description 展示水平偏差数据
     * @return
     */
    private void showHorizontalDataOnChart(QualityBiasnEntity qualityBiasnEntity) {
        //封装水平偏差数据
        float[] xSign = new float[qualityBiasnEntity.getLevelBiasn().getX().size()];
        float[] ySign = new float[qualityBiasnEntity.getLevelBiasn().getY().size()];
        float[] xTotal = new float[qualityBiasnEntity.getLevelTbm().getX().size()];
        float[] yTotal = new float[qualityBiasnEntity.getLevelTbm().getY().size()];
        //构造柱状图数据（管片水平偏差）
        for (int i = 0; i < qualityBiasnEntity.getLevelBiasn().getX().size(); i++) {
            xSign[i] = (float) qualityBiasnEntity.getLevelBiasn().getX().get(i);
        }
        for (int i = 0; i < qualityBiasnEntity.getLevelBiasn().getY().size(); i++) {
            ySign[i] = (float) qualityBiasnEntity.getLevelBiasn().getY().get(i);
        }
        //构造折线图数据（盾构导向水平偏差）
        for (int i = 0; i < qualityBiasnEntity.getLevelTbm().getX().size(); i++) {
            xTotal[i] = (float) qualityBiasnEntity.getLevelTbm().getX().get(i);
        }
        for (int i = 0; i < qualityBiasnEntity.getLevelTbm().getY().size(); i++) {
            yTotal[i] = (float) qualityBiasnEntity.getLevelTbm().getY().get(i);
        }
        // 绘制图表数据（水平偏差）
        horizontalData = new CombinedData();
        // 设置折线图数据（水平偏差）
        horizontalData.setData(getLineData(xTotal, yTotal, "盾构导向水平偏差"));
        // 设置柱状图数据（水平偏差）
        horizontalData.setData(getBarData(xSign, ySign, "管片水平偏差"));

        HorizontalErrorChart.getDescription().setText("偏差数：mm");
        HorizontalErrorChart.setBackgroundColor(Color.WHITE);
        HorizontalErrorChart.setDrawGridBackground(false);
        HorizontalErrorChart.setDrawBarShadow(false);
        HorizontalErrorChart.setHighlightFullBarEnabled(false);

        // draw bars behind lines
        HorizontalErrorChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        XAxis xAxis = HorizontalErrorChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setAxisMinimum(1f);
        xAxis.setGranularity(1f);

        //格式化x轴数据
//        String[] str = new String[qualityBiasnEntity.getLevelBiasn().getX().size()];
//        for (int j = 0; j < qualityBiasnEntity.getLevelBiasn().getX().size(); j++) {
//            str[j] = qualityBiasnEntity.getLevelBiasn().getX().get(j) + "环";
//        }
//        LabelFormatter labelFormatter = new LabelFormatter(str);
//        xAxis.setValueFormatter(labelFormatter);

        YAxis leftAxis = HorizontalErrorChart.getAxisLeft();
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        leftAxis.setStartAtZero(false);
        // 设置x轴的LimitLine
        LimitLine yLimitLine = new LimitLine(0);
        yLimitLine.setLineWidth(1f);
        yLimitLine.setLineColor(Color.RED);
        // 获得左侧侧坐标轴将限制线添加进去
        leftAxis.addLimitLine(yLimitLine);

        HorizontalErrorChart.setData(horizontalData);
        HorizontalErrorChart.invalidate();
        HorizontalErrorChart.getAxisRight().setEnabled(false);
    }

    /**
     *@company vinelinx
     * @author zhangding
     * @date 2019.1.28
     * @description 展示垂直偏差数据
     * @return
     */
    private void showVerticalDataOnChart(QualityBiasnEntity qualityBiasnEntity) {
        //封装垂直偏差数据
        float[] xSign = new float[qualityBiasnEntity.getHighBiasn().getX().size()];
        float[] ySign = new float[qualityBiasnEntity.getHighBiasn().getY().size()];
        float[] xTotal = new float[qualityBiasnEntity.getHighTbm().getX().size()];
        float[] yTotal = new float[qualityBiasnEntity.getHighTbm().getY().size()];
        //构造柱状图数据（管片垂直偏差）
        for (int i = 0; i < qualityBiasnEntity.getHighBiasn().getX().size(); i++) {
            xSign[i] = (float) qualityBiasnEntity.getHighBiasn().getX().get(i);
        }
        for (int i = 0; i < qualityBiasnEntity.getHighBiasn().getY().size(); i++) {
            ySign[i] = (float) qualityBiasnEntity.getHighBiasn().getY().get(i);
        }
        //构造折线图数据（盾构导向垂直偏差）
        for (int i = 0; i < qualityBiasnEntity.getHighTbm().getX().size(); i++) {
            xTotal[i] = (float) qualityBiasnEntity.getHighTbm().getX().get(i);
        }
        for (int i = 0; i < qualityBiasnEntity.getHighTbm().getY().size(); i++) {
            yTotal[i] = (float) qualityBiasnEntity.getHighTbm().getY().get(i);
        }
        // 绘制图表数据（垂直偏差）
        verticalData = new CombinedData();
        // 设置折线图数据（垂直偏差）
        verticalData.setData(getLineData(xTotal, yTotal, "盾构导向垂直偏差"));
        // 设置柱状图数据（垂直偏差）
        verticalData.setData(getBarData(xSign, ySign, "管片垂直偏差"));


        VerticalErrorChart.getDescription().setText("偏差数：mm");
        VerticalErrorChart.setBackgroundColor(Color.WHITE);
        VerticalErrorChart.setDrawGridBackground(false);
        VerticalErrorChart.setDrawBarShadow(false);
        VerticalErrorChart.setHighlightFullBarEnabled(false);

        // draw bars behind lines
        VerticalErrorChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        XAxis xAxis = VerticalErrorChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setAxisMinimum(1f);
        xAxis.setGranularity(1f);


        //格式化x轴数据
//        String[] str = new String[qualityBiasnEntity.getHighBiasn().getX().size()];
//        for (int j = 0; j < qualityBiasnEntity.getHighBiasn().getX().size(); j++) {
//            str[j] = qualityBiasnEntity.getHighBiasn().getX().get(j) + "环";
//        }
//        LabelFormatter labelFormatter = new LabelFormatter(str);
//        xAxis.setValueFormatter(labelFormatter);

        YAxis leftAxis = VerticalErrorChart.getAxisLeft();
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        leftAxis.setStartAtZero(false);
        // 设置x轴的LimitLine
        LimitLine yLimitLine = new LimitLine(0);
        yLimitLine.setLineWidth(1f);
        yLimitLine.setLineColor(Color.RED);
        // 获得左侧侧坐标轴
        leftAxis.addLimitLine(yLimitLine);

        VerticalErrorChart.setData(verticalData);
        VerticalErrorChart.invalidate();
        VerticalErrorChart.getAxisRight().setEnabled(false);
    }

    /**
     *@company vinelinx
     * @author zhangding
     * @date 2019.1.28
     * @description 设置折线图绘制数据
     * @return
     */
    private LineData getLineData(float[] xTotal, float[] yTotal, String showType) {

        LineData d = new LineData();
        ArrayList<Entry> entries = new ArrayList<>();
        for (int index = 0; index < xTotal.length; index++) {
            entries.add(new Entry(xTotal[index], yTotal[index]));
        }
        LineDataSet set = new LineDataSet(entries, showType);
        set.setColor(Color.rgb(0, 0, 255));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(230, 51, 35));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(230, 51, 35));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(false);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(230, 51, 35));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);
        return d;
    }

    /**
     *@company vinelinx
     * @author zhangding
     * @date 2019.1.28
     * @description 设置柱状图绘制数据
     * @return
     */
    private BarData getBarData(float[] xSign, float[] ySign, String showType) {
        ArrayList<BarEntry> entries1 = new ArrayList<>();
        for (int i = 0; i < xSign.length; i++) {
            entries1.add(new BarEntry(xSign[i], ySign[i]));
        }
        BarDataSet set1 = new BarDataSet(entries1, showType);
        set1.setColor(Color.rgb(0, 181, 181));
        set1.setValueTextColor(Color.rgb(255, 215, 0));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setDrawValues(false);
        float barWidth = 0.45f;
        BarData d = new BarData();
        d.addDataSet(set1);
        d.setBarWidth(barWidth);
        return d;
    }


    /**
     * @company vinelinx
     * @author zhangding
     * @date 2019.1.28
     * @description 初始化嵌套环形图
     */
    private void initGroundSetting() {
        final String ShieldGanttData = AppUtils.getJson("json/crec22/project/management/QualityManagement/QualityStatusScale.json", this);
        GroundSettingChart.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!GroundSettingChart.getSettings().getLoadsImagesAutomatically()) {
                    GroundSettingChart.getSettings().setLoadsImagesAutomatically(true);
                }
                GroundSettingChart.loadUrl("javascript:initData(" + ShieldGanttData + ")");
            }
        });
        GroundSettingChart.setWebChromeClient(new WebChromeClient());
        WebViewUtil.webSetting(GroundSettingChart.getSettings());
        GroundSettingChart.loadUrl(GROUND_SETTING_CHART);
        GroundSettingChart.setWebContentsDebuggingEnabled(true);
        WebSettings webSettings =   GroundSettingChart .getSettings();
        //设置此属性，可任意比例缩放
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
    }



    @OnClick(R.id.quality_upload)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setClass(QualityManagementActivity.this, QualityUploadActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.quality_table)
    public void onViewClicked2(){
        Intent progressIntent = new Intent();
        progressIntent.setClass(getContext(), ProjectQualityListActivity.class);
        startActivity(progressIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //断开View引用
        mPresenter.detachView();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
