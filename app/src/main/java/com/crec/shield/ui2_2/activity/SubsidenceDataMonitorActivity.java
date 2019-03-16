package com.crec.shield.ui2_2.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.contract.SubsidenceDataMonitorContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.crec22.project.subsidencedatamonitor.GroundSettingData;
import com.crec.shield.presenter.SubsidenceDataMonitorPresenter;
import com.crec.shield.utils.LabelFormatter;
import com.crec.shield.utils.MyMarkerView;
import com.crec.shield.utils.MyTotalMarkerView;
import com.crec.shield.utils.T;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @company vinelinx
 * @author zhangding
 * @date 2019.1.25
 */
public class SubsidenceDataMonitorActivity extends BaseActivity<SubsidenceDataMonitorPresenter> implements SubsidenceDataMonitorContract.View {
    //数据绑定
    @BindView(R.id.speed_chart)
    LineChart mSpeedChart;
    @BindView(R.id.total_chart)
    LineChart mTotalChart;
    @BindView(R.id.table_info)
    TableLayout mTableLayout;
    @BindView(R.id.et_min)
    EditText mEtMin;
    @BindView(R.id.et_max)
    EditText mEtMax;
    @BindView(R.id.btn_select)
    Button mBtnSelect;
    private GroundSettingData groundSettingData = new GroundSettingData();
    private int lastCount=0;//设置计数器用来统计上次生成数据量
    private int lastSign=1;//设置哨兵用来做特别判断
    @Override
    public int getLayoutId() {
        return R.layout.activity_subsidence_monitor;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getData();
        //点击图表坐标监听
        mSpeedChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //查看覆盖物是否被回收
               // if (mSpeedChart.isMarkerAllNull()) {
                    //重新绑定覆盖物
                    createMakerView();
                    //并且手动高亮覆盖物
                    mSpeedChart.highlightValue(h);
             //   }
            }
            @Override
            public void onNothingSelected() {
            }
        });

        //点击图表坐标监听
        mTotalChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //查看覆盖物是否被回收
                // if (mSpeedChart.isMarkerAllNull()) {
                //重新绑定覆盖物
                createTotalView();
                //并且手动高亮覆盖物
                mTotalChart.highlightValue(h);
                //   }
            }
            @Override
            public void onNothingSelected() {
            }
        });
    }

    /**
     * 创建覆盖物
     */
    public void createMakerView() {
        //自定义的MarkerView对象
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mSpeedChart);
        mSpeedChart.setMarker(mv);
    }
    /**
     * 创建覆盖物
     */
    public void createTotalView() {
        //自定义的MarkerView对象
        MyTotalMarkerView mv = new MyTotalMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mTotalChart);
        mTotalChart.setMarker(mv);
    }

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void showData(GroundSettingData data) {
        groundSettingData = data;
        initData(groundSettingData);

    }

    @Override
    public void showSelectData(GroundSettingData data,String mMin,String mMax) {
        groundSettingData = data;
        dynamicSpeedDataOnChart(groundSettingData,mMin,mMax);
    }

    @OnClick(R.id.btn_select)
    public void onViewClicked() {
        if (null==mEtMin.getText()||mEtMin.getText().length()==0||null==mEtMax.getText()||mEtMax.getText().length()==0) {
            T.showLong(this, "最小环最大环数目不能为空");
            return;
        }else if(null!=mEtMin.getText()&&mEtMin.getText().length()!=0&&null!=mEtMax.getText()&&mEtMax.getText().length()!=0&&Integer.parseInt(mEtMax.getText().toString())<Integer.parseInt(mEtMin.getText().toString())){
            T.showLong(this, "最大环不能小于最小环数");
            return;
        }else{
            mPresenter.getSelectData(mEtMin.getText().toString(),mEtMax.getText().toString());
        }
    }

    /**
     * @company vinelinx
     * @author zhangding
     * @date 2019.1.25
     * @description折线图初始化数据
     */
    private void initData(GroundSettingData groundSettingData) {
        showSpeedDataOnChart(groundSettingData);
        Legend legendRate = mSpeedChart.getLegend();
        Legend legendTotal = mTotalChart.getLegend();
        legendRate.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legendTotal.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        initTableLayout(groundSettingData);
    }

    /**
     * @company vinelinx
     * @author zhangding
     * @date 2019.1.25
     * @description表格数据初始化
     */
    private void initTableLayout(GroundSettingData tableData){
        for(int rows=0;rows<tableData.getCode().size();rows++){
            TableRow tableRow = new TableRow(this);
            for(int cols=0;cols<5;cols++){
                TextView tv = new TextView(this);
                tv.setHeight(80);
                switch(cols){
                    case 0:
                        tv.setText(tableData.getCode().get(rows));
                        break;
                    case 1:
                        tv.setText(tableData.getRingNo().get(rows)+"");
                        break;
                    case 2:
                        tv.setText(tableData.getNowchange().get(rows)+"mm");
                        break;
                    case 3:
                        tv.setText(tableData.getTotalchange().get(rows)+"mm");
                        break;
                    case 4:
                        tv.setText(tableData.getChangeRate().get(rows)+"mm/s");
                        break;
                }
                tv.setGravity(Gravity.CENTER);
                tableRow.addView(tv);
                if(rows%2!=0){
                    tableRow.setBackgroundColor(getResources().getColor(R.color.bg_gray));
                }
            }
            mTableLayout.addView(tableRow);
        }
    }


    /**
     *@company vinelinx
     * @author zhangding
     * @date 2019.1.28
     * @description 展示水平偏差数据
     * @return
     */
    private void showSpeedDataOnChart(GroundSettingData groundSettingData) {
        //封装速率折线图X与Y轴数据
        String[] xSign = new String[groundSettingData.getCode().size()];
        Float[] ySign = new Float[groundSettingData.getChangeRate().size()];

        //封装累计折线图X与Y轴数据
        Float[] nowSign = new Float[groundSettingData.getNowchange().size()];
        Float[] totalSign = new Float[groundSettingData.getTotalchange().size()];

        //构造公用x轴折线图数据
        for (int i = 0; i < groundSettingData.getCode().size(); i++) {
            xSign[i] =  groundSettingData.getCode().get(i);
        }
        //构造速率折线图数据
        for (int i = 0; i < groundSettingData.getChangeRate().size(); i++) {
            ySign[i] = groundSettingData.getChangeRate().get(i);
        }
        //构造现有和累计折线图数据
        for (int i = 0; i < groundSettingData.getNowchange().size(); i++) {
            nowSign[i] = groundSettingData.getNowchange().get(i);
        }
        for (int i = 0; i < groundSettingData.getTotalchange().size(); i++) {
            totalSign[i] = groundSettingData.getTotalchange().get(i);
        }
        //针对速率折线图属性设置
        mSpeedChart.getDescription().setText("速率：mm/s");
        mSpeedChart.setBackgroundColor(Color.WHITE);
        mSpeedChart.setDrawGridBackground(false);
        XAxis xAxis = mSpeedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        //格式化x轴数据
        LabelFormatter labelFormatter = new LabelFormatter(xSign);
        xAxis.setValueFormatter(labelFormatter);

        YAxis leftAxis = mSpeedChart.getAxisLeft();
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        leftAxis.setStartAtZero(true);
        // 设置x轴的LimitLine
        LimitLine yLimitLine = new LimitLine(0);
        yLimitLine.setLineWidth(1f);
        yLimitLine.setLineColor(Color.RED);
        // 获得左侧侧坐标轴将限制线添加进去
        leftAxis.addLimitLine(yLimitLine);
        mSpeedChart.getAxisRight().setEnabled(false);
        mSpeedChart.setData(getLineData(ySign,"速率"));
        mSpeedChart.invalidate(); // refresh


        //针对累计折线图属性设置
        mTotalChart.getDescription().setText("距离：mm");
        mTotalChart.setBackgroundColor(Color.WHITE);
        mTotalChart.setDrawGridBackground(false);

        XAxis totalAxis = mTotalChart.getXAxis();
        totalAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        totalAxis.setGranularity(1f);
        //格式化x轴数据
        totalAxis.setValueFormatter(labelFormatter);

        YAxis totalLeftAxis = mTotalChart.getAxisLeft();
        //保证Y轴从0开始，不然会上移一点
        totalLeftAxis.setAxisMinimum(0f);
        totalLeftAxis.setStartAtZero(true);
        // 设置x轴的LimitLine
        LimitLine totalLimitLine = new LimitLine(0);
        totalLimitLine.setLineWidth(1f);
        totalLimitLine.setLineColor(Color.RED);
        // 获得左侧侧坐标轴将限制线添加进去
        totalLeftAxis.addLimitLine(totalLimitLine);

        mTotalChart.getAxisRight().setEnabled(false);
        mTotalChart.setData(getLineData(nowSign,totalSign,"本次变化量","累计变化量"));
        mTotalChart.invalidate(); // refresh
    }



    /**
     *@company vinelinx
     * @author zhangding
     * @date 2019.1.28
     * @description 设置折线图绘制数据
     * @return
     */
    private LineData getLineData( Float[] ySign, String showType) {
        LineData d = new LineData();
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < ySign.length; i++){
            entries.add(new Entry(i,ySign[i]));
        }
        LineDataSet set = new LineDataSet(entries,showType);
        set.setColor(Color.rgb(0, 0, 255));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(230, 51, 35));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(230, 51, 35));
        //set.setMode(LineDataSet.Mode.CUBIC_BEZIER);注释掉的原因是这里用曲线图绘制的时候某些临近0的点会出现附近的曲线进入到负值区域
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
     * @description 设置折线图绘制数据
     * @return
     */
    private LineData getLineData( Float[] x,Float[] y,String showTypeFirst,String showTypeSecond) {
        LineData d = new LineData();
        List<Entry> entries = new ArrayList<>();
        List<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < x.length; i++){
            entries.add(new Entry(i,x[i]));
        }
        for (int i = 0; i <y.length; i++){
            entries2.add(new Entry(i,y[i]));
        }
        LineDataSet set1 = new LineDataSet(entries,showTypeFirst);
        LineDataSet set2 = new LineDataSet(entries2,showTypeSecond);
        set1.setColor(Color.rgb(0, 0, 255));
        set1.setLineWidth(2.5f);
        set1.setCircleColor(Color.rgb(230, 51, 35));
        set1.setCircleRadius(5f);
        set1.setFillColor(Color.rgb(230, 51, 35));
        //set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setDrawValues(false);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.rgb(230, 51, 35));
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        set2.setColor(Color.rgb(85, 107, 47));
        set2.setLineWidth(2.5f);
        set2.setCircleColor(Color.rgb(230, 51, 35));
        set2.setCircleRadius(5f);
        set2.setFillColor(Color.rgb(230, 51, 35));
        //set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set2.setDrawValues(false);
        set2.setValueTextSize(10f);
        set2.setValueTextColor(Color.rgb(230, 51, 35));
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set1);
        d.addDataSet(set2);
        return d;
    }

    /**
     *@company vinelinx
     * @author zhangding
     * @date 2019.1.28
     * @description 动态查询数据
     * @return
     */
    private void dynamicSpeedDataOnChart(GroundSettingData groundSettingData,String mMin,String mMax){
        int  nullSign=0;
        //查询数据如果返回条数为0 那么就清空列表和图中数据
        for (int i = 0; i < groundSettingData.getRingNo().size(); i++) {
            if(groundSettingData.getRingNo().get(i)>=Integer.parseInt(mMin)&&groundSettingData.getRingNo().get(i)<=Integer.parseInt(mMax)){
                nullSign++;
            }
        }
        if(nullSign==0){
            mSpeedChart.clear();
            mTotalChart.clear();
            if(lastSign==1){
                mTableLayout.removeViews(1,lastCount!=0?lastCount:groundSettingData.getRingNo().size());
            }
            //将哨兵的值恢复为初始状态
            lastSign=0;
            return;
        }

        //统计每一次查询后数据的条目
        int newNum=0;
        for (int i = 0; i < groundSettingData.getRingNo().size(); i++) {
            if(groundSettingData.getRingNo().get(i)>=Integer.parseInt(mMin)&&groundSettingData.getRingNo().get(i)<=Integer.parseInt(mMax)){
                newNum++;
            }
        }
        //封装速率折线图X与Y轴数据
        String[] xSign = new String[newNum];
        Float[] ySign = new Float[newNum];
        Integer[] zSign = new Integer[newNum];
        //封装累计折线图X与Y轴数据
        Float[] nowSign = new Float[newNum];
        Float[] totalSign = new Float[newNum];

        //构造公用x轴折线图数据
        int countEdior=0;
        for (int i = 0; i < groundSettingData.getCode().size(); i++) {
            if(groundSettingData.getRingNo().get(i)>=Integer.parseInt(mMin)&&groundSettingData.getRingNo().get(i)<=Integer.parseInt(mMax)){
                xSign[countEdior] =  groundSettingData.getCode().get(i);
                countEdior++;
            }
        }
        //构造速率折线图数据
        countEdior=0;
        for (int i = 0; i < groundSettingData.getRingNo().size(); i++) {
            if(groundSettingData.getRingNo().get(i)>=Integer.parseInt(mMin)&&groundSettingData.getRingNo().get(i)<=Integer.parseInt(mMax)) {
                zSign[countEdior] = groundSettingData.getRingNo().get(i);
                countEdior++;
            }
        }
        //构造速率折线图数据
        countEdior=0;
        for (int i = 0; i < groundSettingData.getChangeRate().size(); i++) {
            if(groundSettingData.getRingNo().get(i)>=Integer.parseInt(mMin)&&groundSettingData.getRingNo().get(i)<=Integer.parseInt(mMax)) {
                ySign[countEdior] = groundSettingData.getChangeRate().get(i);
                countEdior++;
            }
        }
        //构造现有和累计折线图数据
        countEdior=0;
        for (int i = 0; i < groundSettingData.getNowchange().size(); i++) {
            if(groundSettingData.getRingNo().get(i)>=Integer.parseInt(mMin)&&groundSettingData.getRingNo().get(i)<=Integer.parseInt(mMax)) {
                nowSign[countEdior] = groundSettingData.getNowchange().get(i);
                countEdior++;
            }
        }
        countEdior=0;
        for (int i = 0; i < groundSettingData.getTotalchange().size(); i++) {
            if(groundSettingData.getRingNo().get(i)>=Integer.parseInt(mMin)&&groundSettingData.getRingNo().get(i)<=Integer.parseInt(mMax)) {
                totalSign[ countEdior] = groundSettingData.getTotalchange().get(i);
                countEdior++;
            }
        }
        //重构前先删除数据
        mSpeedChart.clear();
        mTotalChart.clear();
        //与之前方法类似属性设置
        mSpeedChart.getDescription().setText("速率：mm/s");
        mSpeedChart.setBackgroundColor(Color.WHITE);
        mSpeedChart.setDrawGridBackground(false);

        XAxis xAxis = mSpeedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setAxisMinimum(1f);
        xAxis.setGranularity(1f);

        //格式化x轴数据
        LabelFormatter labelFormatter = new LabelFormatter(xSign);
        xAxis.setValueFormatter(labelFormatter);

        YAxis leftAxis = mSpeedChart.getAxisLeft();
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        leftAxis.setStartAtZero(true);
        // 设置x轴的LimitLine
        LimitLine yLimitLine = new LimitLine(0);
        yLimitLine.setLineWidth(1f);
        yLimitLine.setLineColor(Color.RED);
        // 获得左侧侧坐标轴将限制线添加进去
        leftAxis.addLimitLine(yLimitLine);

        mSpeedChart.setData(getLineData(ySign,"速率"));
        mSpeedChart.invalidate(); // refresh
        mSpeedChart.getAxisRight().setEnabled(false);

        //绘制本次变化量及累计变化量
        mTotalChart.getDescription().setText("速率：mm/s");
        mTotalChart.setBackgroundColor(Color.WHITE);
        mTotalChart.setDrawGridBackground(false);

        XAxis totalAxis = mTotalChart.getXAxis();
        totalAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //totalAxis.setAxisMinimum(1f);
        totalAxis.setGranularity(1f);

        //格式化x轴数据
        totalAxis.setValueFormatter(labelFormatter);

        YAxis totalLeftAxis = mTotalChart.getAxisLeft();
        //保证Y轴从0开始，不然会上移一点
        totalLeftAxis.setAxisMinimum(0f);
        totalLeftAxis.setStartAtZero(true);
        // 设置x轴的LimitLine
        LimitLine totalLimitLine = new LimitLine(0);
        totalLimitLine.setLineWidth(1f);
        totalLimitLine.setLineColor(Color.RED);
        // 获得左侧侧坐标轴将限制线添加进去
        leftAxis.addLimitLine(totalLimitLine);

        mTotalChart.setData(getLineData(nowSign,totalSign,"本次变化量","累计变化量"));
        mTotalChart.invalidate(); // refresh
        mTotalChart.getAxisRight().setEnabled(false);
        if(lastSign==1){
            mTableLayout.removeViews(1, lastCount != 0 ? lastCount : groundSettingData.getRingNo().size());
        }
        lastCount = newNum;
        for (int rows = 0; rows < newNum; rows++) {
            TableRow tableRow = new TableRow(this);
            for (int cols = 0; cols < 5; cols++) {
                TextView tv = new TextView(this);
                switch (cols) {
                    case 0:
                        tv.setText(xSign[rows]);
                        break;
                    case 1:
                        tv.setText(zSign[rows] + "");
                        break;
                    case 2:
                        tv.setText(nowSign[rows] + "mm");
                        break;
                    case 3:
                        tv.setText(totalSign[rows] + "mm");
                        break;
                    case 4:
                        tv.setText(ySign[rows] + "mm/s");
                        break;
                }
                tv.setGravity(Gravity.CENTER);
                tableRow.addView(tv);
                if (rows % 2 != 0) {
                    tableRow.setBackgroundColor(getResources().getColor(R.color.bg_gray));
                }
            }
            mTableLayout.addView(tableRow);
        }
        lastSign = 1;
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

    @OnClick(R.id.home_page)
    public void onGroundClicked() {
        Intent intent = new Intent();
        intent.setClass(getContext(), ProjectListForCityActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.home_page_vedio)
    public void onVedioClicked() {
        Intent intent = new Intent();
        intent.setClass(getContext(), VedioListForCityActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.home_page_process)
    public void onListClicked() {
        Intent intent = new Intent();
        intent.setClass(getContext(), ProcessManagementActivity.class);
        startActivity(intent);
    }
}
