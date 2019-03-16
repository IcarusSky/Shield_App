package com.crec.shield.ui2_2.activity;

import com.crec.shield.base.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.contract.ManagementDataContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.crec22.project.companymanagementdata.ManagementData;
import com.crec.shield.entity.crec22.project.management.RingRatio;
import com.crec.shield.global.AppConstant;
import com.crec.shield.presenter.ManagementDataPresenter;
import com.crec.shield.utils.SPUtils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ManagementDataActivity extends BaseActivity<ManagementDataPresenter> implements ManagementDataContract.View{

    private Unbinder unbinder;
    private ManagementData projectSystemData;
    private RingRatio ringRatio;          //  环数比例

//    @BindView(R.id.toolbar_title)
//    Spinner mToolbar_title;
    @BindView(R.id.btn_left)
    ImageView mBtnBack;

    // 进度管理
    @BindView(R.id.progress_management)
    ImageView progressManagement;
    @BindView(R.id.progress_chart)
    PieChart progressChart;
    @BindView(R.id.percent)
    TextView percent;
    @BindView(R.id.yesterday_finish)
    TextView yesterdayFinish;
    @BindView(R.id.today_finish)
    TextView todayFinish;
    @BindView(R.id.seven_finish)
    TextView sevenFinish;

    // 安全管理
    @BindView(R.id.safa_chart)
    PieChart safa_Chart;
    @BindView(R.id.first_risk)
    TextView firstRisk;
    @BindView(R.id.second_risk)
    TextView secondRisk;
    @BindView(R.id.third_risk)
    TextView thirdRisk;
    @BindView(R.id.forth_risk)
    TextView forthRisk;



    // 设备管理
    @BindView(R.id.equipment_management)
    ImageView equipmentManagement;
    @BindView(R.id.equipment_chart)
    PieChart equipmentChart;
    @BindView(R.id.normal_tunne)
    TextView normalTunne;
    @BindView(R.id.normal_stop)
    TextView normalStop;
    @BindView(R.id.fault_stop)
    TextView faultStop;
    @BindView(R.id.under_repair)
    TextView underRepair;



    // 质量管理
    @BindView(R.id.quality_chart)
    PieChart qualityChart;
    @BindView(R.id.ample)
    TextView ample;
    @BindView(R.id.medium)
    TextView medium;
    @BindView(R.id.bad)
    TextView bad;
    String mToken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
    String orgId = SPUtils.get(AppConstant.LOGINSTATUS.company_id, "").toString();
    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_company_managementdata;
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
        mPresenter.getData(mToken,orgId);        //获取设备基本信息
        unbinder = ButterKnife.bind(this);

    }

    @OnClick({R.id.progress_management, R.id.safety_management,R.id.equipment_management,R.id.quality_management})
    public void onViewClicked(ImageView button) {
        switch (button.getId()){
            case R.id.progress_management:
                Intent progressIntent = new Intent();
                progressIntent.setClass(getContext(), ProgressManagementActivity.class);
                startActivity(progressIntent);
                break;
            case R.id.safety_management:
                Intent safetyIntent = new Intent();
                safetyIntent.setClass(getContext(), CompanySafeManagementActivity.class);
                startActivity(safetyIntent);
                break;
            case R.id.equipment_management:
                Intent equipmentIntent = new Intent();
                equipmentIntent.setClass(getContext(), CompanyEquipmentManagementActivity.class);
                startActivity(equipmentIntent);
                break;
            case R.id.quality_management:
                Intent qualityIntent = new Intent();
                qualityIntent.setClass(getContext(), CompanyManagementQualityActivity.class);
                startActivity(qualityIntent);
                break;
        }
    }
    @OnClick(R.id.btn_left)//返回键
    public void onViewClicked() {
        super.onBackPressed();
        finish();
    }
    @Override
    public void showData(ManagementData data) {
        initProgressChart(data);  // 进度管理
        initSafetyData(data);     // 安全管理
        initEquipmentChart(data); // 设备管理
        initQualityChart(data);    //质量管理


    }
    /**
     * @company vinelinx
     * @author shenbo
     * @date 2018.2.18
     * @description 公司级-管理数据-进度管理
     * */
    private void initProgressChart(ManagementData managementData) {

        int current = managementData.getFinishCylinder();
        int total = managementData.getTotalCylinder();
        float ratio = (float)current/total*100;// 环形进度圈比例
        int r=(int)ratio;
        int yesterdayfinish = managementData.getYesterdayFinish();
        int todayfinish = managementData.getTodayFinish();
        int sevenfinish = managementData.getSevenFinish();

        percent.setText(current+"/"+total);
        yesterdayFinish.setText(yesterdayfinish+"环");
        todayFinish.setText(todayfinish+"环");
        sevenFinish.setText(sevenfinish+"环");

        List<PieEntry> strings = new ArrayList<>();
        strings.add(new PieEntry(100-r, ""));
        strings.add(new PieEntry(r, ""));

        PieDataSet dataSet = new PieDataSet(strings, "");

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.radio));
        colors.add(getResources().getColor(R.color.gray1));
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(0f);
        pieData.setDrawValues(true);

        progressChart.setData(pieData);
        progressChart.invalidate();
        progressChart.setCenterText(r+"%");  // 中心文本显示进度百分比
        progressChart.setCenterTextSize(20f);
        progressChart.setHoleRadius(80f);    //  设置中心圆半径
        progressChart.setTransparentCircleRadius(0f);
        progressChart.setExtraOffsets(0, 5, 0, 0);// 设置饼状图距离上下左右的偏移量


        Legend mLegend = progressChart.getLegend();  // 设置比例图
        mLegend.setEnabled(false); // 设置禁用比例块

        Description mDescription = progressChart.getDescription(); // 设置右下方的文字描述
        mDescription.setEnabled(false); // 设置禁用右下方的文字描述

    }
    /**
     * @company vinelinx
     * @author shenbo
     * @date 2018.2.18
     * @description 公司级-管理数据-安全管理
     * */
    private void initSafetyData(ManagementData managementData) {

        int firstrisk = managementData.getOneLevel();
        int secondrisk = managementData.getTwoLevel();
        int thirdrisk = managementData.getThreeLevel();
        int forthrisk = managementData.getFourLevel();


        firstRisk.setText("风险源"+firstrisk+"个");
        secondRisk.setText("风险源"+secondrisk+"个");
        thirdRisk.setText("风险源"+thirdrisk+"个");
        forthRisk.setText("风险源"+forthrisk+"个");

        List<PieEntry> strings = new ArrayList<>();
        strings.add(new PieEntry(firstrisk, ""));   //设置饼图数据
        strings.add(new PieEntry(secondrisk, ""));
        strings.add(new PieEntry(thirdrisk, ""));
        strings.add(new PieEntry(forthrisk, ""));
        PieDataSet dataSet = new PieDataSet(strings, "");

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.Danger));
        colors.add(getResources().getColor(R.color.Warning));
        colors.add(getResources().getColor(R.color.radio));
        colors.add(getResources().getColor(R.color.Safe));
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(0f);
        pieData.setDrawValues(true);

        safa_Chart.setData(pieData);
        safa_Chart.invalidate();
        safa_Chart.setCenterText("风险源预计");  // 中心文本显示进度百分比
        safa_Chart.setCenterTextSize(15f);
        safa_Chart.setHoleRadius(70f);    //  设置中心圆半径
        safa_Chart.setTransparentCircleRadius(0f);
        safa_Chart.setExtraOffsets(0, 5, 0, 5);// 设置饼状图距离上下左右的偏移量


        Legend mLegend = safa_Chart.getLegend();  // 设置比例图
        mLegend.setEnabled(false); // 设置禁用比例块

        Description mDescription = safa_Chart.getDescription(); // 设置右下方的文字描述
        mDescription.setEnabled(false); // 设置禁用右下方的文字描述

    }
    /**
     * @company vinelinx
     * @author shenbo
     * @date 2018.2.18
     * @description 公司级-管理数据-设备管理
     * */
    private void initEquipmentChart(ManagementData managementData) {


        float normaltunne = (float)managementData.getNormalTunne();    //  （正常掘进）
        float normalstop = (float)managementData.getNormalStop();    //  （正常停机）
        float faultstop = (float)managementData.getFaultStop();      //  （故障停机）
        float repair =(float)managementData.getUnderRepair();         //(维修中)


        normalTunne.setText(managementData.getNormalTunne()+"台");
        normalStop.setText(managementData.getNormalStop()+"台");
        faultStop.setText(managementData.getFaultStop()+"台");
        underRepair.setText(managementData.getUnderRepair()+"台");

        List<PieEntry> string = new ArrayList<>();//设置饼图数据
        if(normaltunne==0){string.add(new PieEntry(normaltunne, ""));}
        else{string.add(new PieEntry(normaltunne, "正常掘进"));}
        if(normalstop==0){string.add(new PieEntry(normalstop, ""));}
        else{string.add(new PieEntry(normalstop, "正常停机"));}
        if(faultstop==0){string.add(new PieEntry(faultstop, ""));}
        else{string.add(new PieEntry(faultstop, "故障停机"));}
        if(repair==0){string.add(new PieEntry(repair, ""));}
        else{string.add(new PieEntry(repair, "维修中"));}
//        string.add(new PieEntry(normaltunne, "正常掘进"));
//        string.add(new PieEntry(faultstop, "故障停机"));
//        string.add(new PieEntry(normalstop, "正常停机"));
//        string.add(new PieEntry(repair, "维修中"));

        PieDataSet dataSet = new PieDataSet(string, "");

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.cornflowerblue));
        colors.add(getResources().getColor(R.color.crimson));
        colors.add(getResources().getColor(R.color.darkorchid));
        colors.add(getResources().getColor(R.color.goldenrod));
        dataSet.setColors(colors);
//        dataSet.setValueLinePart1OffsetPercentage(100f);
//        dataSet.setValueLinePart1Length(0.6f);
//        dataSet.setValueLinePart2Length(0.2f);
//        dataSet.setHighlightEnabled(true);
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);//标签显示在外面，关闭显示在饼图里面

        dataSet.setValueLineColor(0xff000000);  //设置指示线条颜色,必须设置成这样，才能和饼图区域颜色一致

        PieData pieData = new PieData(dataSet);
//        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(0f);
        pieData.setDrawValues(true);
//        pieData.setHighlightEnabled(true);

        equipmentChart.setData(pieData);
        equipmentChart.invalidate();
        equipmentChart.setHoleRadius(0f);  // 饼状图中间的圆的半径大小
        equipmentChart.setTransparentCircleRadius(0);  // 设置中间透明圈的大小
        equipmentChart.setExtraOffsets(0, 5, 0, 5);//设置饼状图距离上下左右的偏移量

        Legend mLegend = equipmentChart.getLegend();  //设置比例图
        mLegend.setEnabled(false); // 禁用比例图

        Description mDescription = equipmentChart.getDescription();  // 设置右下方的文字描述
        mDescription.setEnabled(false);  // 设置禁用右下方的文字描述

    }
    /**
     * @company vinelinx
     * @author shenbo
     * @date 2018.2.18
     * @description 公司级-管理数据-质量管理
     * */

    private void initQualityChart(ManagementData managementData) {

        int Ample = managementData.getQualitAmple();
        int Medium = managementData.getQualitMedium();
        int Bad = managementData.getQualitBad();

        List<PieEntry> string = new ArrayList<>();//设置饼图数据
        if(Ample==0){string.add(new PieEntry(Ample, ""));}
        else{string.add(new PieEntry(Ample, "优"));}
        if(Medium==0){string.add(new PieEntry(Medium, ""));}
        else{string.add(new PieEntry(Medium, "中"));}
        if(Bad==0){string.add(new PieEntry(Bad, ""));}
        else{string.add(new PieEntry(Bad, "差"));}


        PieDataSet dataSet = new PieDataSet(string, "");

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.Safe));
        colors.add(getResources().getColor(R.color.Warning));
        colors.add(getResources().getColor(R.color.Danger));
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(0f);
        pieData.setDrawValues(true);

        qualityChart.setData(pieData);
        qualityChart.invalidate();
        qualityChart.setHoleRadius(0);      // 饼状图中间的圆的半径大小
        qualityChart.setTransparentCircleRadius(0);    // 设置中间透明圈的大小
//        qualityChart.setDrawSliceText(false);     //设置隐藏饼图上文字，只显示百分比
        qualityChart.setExtraOffsets(0, 5, 0, 5);//设置饼状图距离上下左右的偏移量


        Legend mLegend = qualityChart.getLegend();  //设置比例图
        mLegend.setEnabled(false); // 设置禁用比例块
//        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);  // 右中显示
//        mLegend.setFormSize(15f);    // 比例块字体大小
//        mLegend.setXEntrySpace(2f);  // 设置距离饼图的距离，防止与饼图重合
//        mLegend.setYEntrySpace(12f);
//        mLegend.setXOffset(40f);
//        //设置比例块换行...
//        mLegend.setWordWrapEnabled(true);
//        mLegend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
//
//        mLegend.setTextColor(getResources().getColor(R.color.text_com_color));
//        mLegend.setTextSize(13f);
//
//        // 重置比例块内容
//        List<LegendEntry> mLegendEntry = new ArrayList<>();
//        mLegendEntry.add(new LegendEntry("正常环数 320环", Legend.LegendForm.CIRCLE, 13f, 2f, null ,R.color.Safe)); //text_com_color
//        mLegendEntry.add(new LegendEntry("异常环数 32环", Legend.LegendForm.CIRCLE, 13f, 2f, null,R.color.Danger));
//        mLegend.setCustom(mLegendEntry);
//        mLegend.setForm(Legend.LegendForm.CIRCLE);  //设置比例块形状，默认为方块,此处设置为圆形

        Description mDescription = qualityChart.getDescription();  // 设置右下方的文字描述
        mDescription.setEnabled(false);  // 设置禁用右下方的文字描述

    }
}
