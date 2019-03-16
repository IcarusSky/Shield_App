package com.crec.shield.ui2_2.activity;
import com.crec.shield.base.BaseActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.contract.EquipmentManagementContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.crec22.project.management.EquipmentManagerData;
import com.crec.shield.entity.crec22.project.management.ShieldDanttData;
import com.crec.shield.presenter.EquipmentManagementPresenter;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EquipmentManagementActivity extends BaseActivity<EquipmentManagementPresenter> implements EquipmentManagementContract.View {

    @BindView(R.id.tbmName)
    TextView tbmName;
    @BindView(R.id.tbmType)
    TextView tbmType;
    @BindView(R.id.manageNo)
    TextView manageNo;
    @BindView(R.id.chargerName)
    TextView chargerName;
    @BindView(R.id.manufacturer)
    TextView manufacturer;
    @BindView(R.id.chargerNumber)
    TextView chargerNumber;
    @BindView(R.id.factoryDate)
    TextView factoryDate;
    @BindView(R.id.currentLocation)
    TextView currentLocation;
    @BindView(R.id.buyDate)
    TextView buyDate;
    @BindView(R.id.completeDate)
    TextView completeDate;
    @BindView(R.id.tbmVest)
    TextView tbmVest;
    @BindView(R.id.availableDate)
    TextView availableDate;
    @BindView(R.id.currentMile)
    TextView currentMile;
    @BindView(R.id.bar1)
    CombinedChart bar1;
    @BindView(R.id.btn_left)
    ImageView button;

    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例
    private CombinedData barData;
    private Unbinder unbinder;
//    private LimitLine limitLine;        //限制线

    @Override
    public int getLayoutId() {
        return R.layout.activity_equipment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mPresenter.getShieldData();  //获取盾构数据
        mPresenter.getData();        //获取设备基本信息
        unbinder = ButterKnife.bind(this);

    }

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @OnClick(R.id.btn_left)//返回键
    public void onViewClicked() {
        super.onBackPressed();
        finish();
    }
    @Override
    public void showData(EquipmentManagerData data) {
        tbmName.setText(data.getTbmName());
        tbmName.setVisibility(View.VISIBLE);

        tbmType.setText(data.getTbmType());
        tbmType.setVisibility(View.VISIBLE);

        manageNo.setText(data.getManageNo());
        manageNo.setVisibility(View.VISIBLE);

        chargerName.setText(data.getChargerName());
        chargerName.setVisibility(View.VISIBLE);

        manufacturer.setText(data.getManufacturer());
        manufacturer.setVisibility(View.VISIBLE);

        chargerNumber.setText(data.getChargerNumber());
        chargerNumber.setVisibility(View.VISIBLE);

        factoryDate.setText(data.getFactoryDate());
        factoryDate.setVisibility(View.VISIBLE);

        currentLocation.setText(data.getCurrentLocation());
        currentLocation.setVisibility(View.VISIBLE);

        buyDate.setText(data.getBuyDate());
        buyDate.setVisibility(View.VISIBLE);

        completeDate.setText(data.getCompleteDate());
        completeDate.setVisibility(View.VISIBLE);

        tbmVest.setText(data.getTbmVest());
        tbmVest.setVisibility(View.VISIBLE);

        availableDate.setText(data.getAvailableDate());
        availableDate.setVisibility(View.VISIBLE);

        currentMile.setText(data.getCurrentMile());
        currentMile.setVisibility(View.VISIBLE);


    }

    @Override
    public void showShieldDanttData(ShieldDanttData data) {

        showBarChart(data);
    }


    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.14
     * @description 初始化BarChart图表
     */
    private void initBarChart() {
        /***图表设置***/
        bar1.setBackgroundColor(Color.WHITE);//背景颜色
        bar1.setDrawGridBackground(true);//显示图表网格
        //背景阴影
        bar1.setDrawBarShadow(false);
        bar1.setHighlightFullBarEnabled(false);
        //显示边框
        bar1.setDrawBorders(true);
        //设置动画效果
//        barChart.animateY(1000, Easing.Linear);
//        barChart.animateX(1000, Easing.Linear);

        /***XY轴的设置***/
        //X轴设置显示位置在底部
        xAxis = bar1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        //Y轴初始化
        leftAxis = bar1.getAxisLeft();
        rightAxis = bar1.getAxisRight();
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);

        /***折线图例 标签 设置***/
        legend = bar1.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
        //不显示柱状图顶部值
        //barDataSet.setDrawValues(false);
    }

    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.14
     * @description BarChart图表部件整体显示及其中数据
     */
    public void showBarChart(ShieldDanttData sData) {
        //得到数据，并转换数据类型
        Integer[] normalInDate = new Integer[sData.getMonthNo().size()];
        Integer[] unusualStopDate = new Integer[sData.getMonthNo().size()];
        Integer[] normalStopDate = new Integer[sData.getMonthNo().size()];
        String[] monthNo = new String[sData.getMonthNo().size()];
        sData.getNormalInDate().toArray(normalInDate);
        sData.getUnusualStopDate().toArray(unusualStopDate);
        sData.getNormalStopDate().toArray(normalStopDate);
        sData.getMonthNo().toArray(monthNo);
        //将数据数组补齐
        for (int i = 0; i < monthNo.length; i++) {
            if (normalInDate[i] == null)
                normalInDate[i] = 0;
            if (unusualStopDate[i] == null)
                unusualStopDate[i] = 0;
            if (normalStopDate[i] == null)
                normalStopDate[i] = 0;
        }
        //设置图表数据
        barData = new CombinedData();
        barData.setData(getBarData(normalInDate, unusualStopDate, normalStopDate, monthNo));
        /***图表设置***/
        bar1.setBackgroundColor(Color.WHITE);//背景颜色
        bar1.setDrawGridBackground(true);//显示图表网格
        //背景阴影
        bar1.setDrawBarShadow(false);
        bar1.setHighlightFullBarEnabled(false);
        //显示边框
        bar1.setDrawBorders(false);
        //定义单位图例
        bar1.getDescription().setText("月");
        bar1.getDescription().setTextAlign(Paint.Align.RIGHT);
        //显示图的类型为：柱状、折线或饼状
        bar1.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });
        /***XY轴设置*/
        xAxis = bar1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setGranularity(1f);
        xAxis.setDrawAxisLine(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(sData.getMonthNo()));
        xAxis.setAxisMaximum(barData.getXMax() + 0.5f);
        //Y轴设置
        YAxis leftAxis = bar1.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawAxisLine(true);
        YAxis rightAxis = bar1.getAxisRight();
        rightAxis.setEnabled(false);
        /**图例设置*/
        Legend l = bar1.getLegend();
        l.setWordWrapEnabled(true);  // 设置图例是否重新创建一行
        l.setTextSize(13f);          //图例字体大小
        //图例位置
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);     //图例是否在图表内

        bar1.setData(barData);
    }

    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.14
     * @description 柱状图设置及其中数据
     */
    private BarData getBarData(Integer[] normalInDate, Integer[] unusualStopDate, Integer[] normalStopDate, String[] monthNo) {
        //得到数据
        ArrayList<BarEntry> entries1 = new ArrayList<>();
//        ArrayList<BarEntry> entries2 = new ArrayList<>();   显示多组柱状图时使用
//        ArrayList<BarEntry> entries3 = new ArrayList<>();
        for (int index = 0; index < monthNo.length; index++) {

            float[] a = {normalInDate[index], unusualStopDate[index], normalStopDate[index]};
            entries1.add(new BarEntry(index, a));
//            entries2.add(new BarEntry(index,unusualStopDate[index]));  显示多组柱状图时使用
//            entries3.add(new BarEntry(index, normalStopDate[index]));
        }
        //为柱状图设置数据及图例颜色等
        BarDataSet set = new BarDataSet(entries1,"");
        set.setColors(new int[]{Color.rgb(153,153,255), Color.rgb(51,204,255), Color.rgb(255, 255, 0)});
        set.setStackLabels(new String[]{"正常掘进时长","故障停机时长","正常停机时长"});
        set.setValueTextSize(8f);
        BarData d = new BarData(set);
        float barWidth = 0.3f;
        return d;
    }
}
    /**显示多组柱状图时使用*/
//        BarDataSet set1 = new BarDataSet(entries1, "正常掘进时长");
//        set1.setColor(Color.rgb(255,215,0));  // 黄色
//        set1.setValueTextColor(Color.rgb(255,215,0));
//        set1.setValueTextSize(10f);
//        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
//
//        BarDataSet set2 = new BarDataSet(entries2, "故障停机时长");
//        set2.setColor(Color.rgb(30,144,255));  // 蓝色
//        set2.setValueTextColor(Color.rgb(30,144,255));
//        set2.setValueTextSize(10f);
//        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
//
//        BarDataSet set3 = new BarDataSet(entries3, "正常停机时长");
//        set3.setColor(Color.rgb(255,144,30));  // 红色
//        set3.setValueTextColor(Color.rgb(255,144,30));
//        set3.setValueTextSize(10f);
//        set3.setAxisDependency(YAxis.AxisDependency.LEFT);
//
//        float groupSpace = 0.06f;
//        float barSpace = 0.02f; // x2 dataset
//        float barWidth = 0.45f; // x2 dataset
//        (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"
//
//        BarData d = new BarData(set1, set2, set3);
//        d.setBarWidth(barWidth);
//
//        make this BarData object grouped
//        d.groupBars(0, groupSpace, barSpace);
//        start at x = 0
