package com.crec.shield.ui2_2.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.ProDetailQualityAdapter;
import com.crec.shield.adapter.ProjectUnnormalDetailAdapter;
import com.crec.shield.contract.CompanyEquipmentManagementContract;
import com.crec.shield.contract.UnnormalWorkConditionContract;
import com.crec.shield.entity.crec22.project.companyequipmentmanagement.EquipmentData;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalHeadData;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalListItemEntity;
import com.crec.shield.presenter.CompanyEquipmentManagementPresenter;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.presenter.UnnormalWorkConditionPresenter;
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

public class CompanyEquipmentManagementActivity extends BaseActivity<UnnormalWorkConditionPresenter> implements UnnormalWorkConditionContract.View {
    //设备列表
    @BindView(R.id.recyclerViewTable)
     RecyclerView recyclerViewTable;
    @BindView(R.id.btn_left)
    ImageView button;
    //状态分布图
    @BindView(R.id.status_chart)
    PieChart statusChart;
    @BindView(R.id.total)
    TextView total;

    @BindView(R.id.tunnul)
    TextView tunnuL;
    @BindView(R.id.pause)
    TextView pausE;
    @BindView(R.id.captaintest)
    TextView captaintesT;
    @BindView(R.id.unkennel)
    TextView unkenneL;
    @BindView(R.id.inrepair)
    TextView inrepaiR;
    @BindView(R.id.save)
    TextView savE;
    @BindView(R.id.dismantle)
    TextView dismantlE;
    @BindView(R.id.transport)
    TextView transporT;

    //周维修制造
    @BindView(R.id.weekrepair)
    TextView weekrepair;
    @BindView(R.id.weekrebuild)
    TextView weekrebuild;
    //故障时间
    @BindView(R.id.lastmonthfault)
    TextView lastmonthfault;
    @BindView(R.id.thismonthfault)
    TextView thismonthfault;
    @BindView(R.id.lastweekfault)
    TextView lastweekfault;
    @BindView(R.id.thisweekfault)
    TextView thisweekfault;
    private Unbinder unbinder;
    private ProjectUnnormalDetailAdapter qualityAdapter;
    private List<UnnormalListItemEntity> riskListData=new ArrayList<UnnormalListItemEntity>();
    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_company_equipment_management;
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
        recyclerViewTable.setLayoutManager(layoutManager);

        unbinder = ButterKnife.bind(this);
        initProgressChart();
        initFaultTime();
        initWeekStatus();
        mPresenter.getUnnormalListData();
//        mPresenter.getUnnormalData();
//        mPresenter.getUnnormalGanttData();
//        mPresenter.getUnnormalListData();
//        initData();  //  初始化地面沉降统计图

    }
    @OnClick(R.id.btn_left)//返回键
    public void onViewClicked() {
        super.onBackPressed();
        finish();
    }
    @Override
    public void showUnnormalData(UnnormalHeadData data) {

    }

    @Override
    public void showUnnormalGanttData(String s) {

    }

    @Override
    public void showUnnormalListData(List<UnnormalListItemEntity> data) {
        riskListData=data;
        initTable();
    }

    @Override
    public void showWithoutData(String type) {

    }
    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.26
     * @description 初始化周数据
     */
    private void initWeekStatus(){

        weekrepair.setText("1台");
        lastmonthfault.setVisibility(View.VISIBLE);

        weekrebuild.setText("1台");
        lastmonthfault.setVisibility(View.VISIBLE);
    }
    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.26
     * @description 初始化状态分布图
     */
    private void initProgressChart() {

//        int current = managementData.getFinishCylinder();
//        int total = managementData.getTotalCylinder();
//        int ratio = managementData.getFinishRatio();         // 环形进度圈比例
//        int yesterdayfinish = managementData.getYesterdayFinish();
//        int todayfinish = managementData.getTodayFinish();
//        int sevenfinish = managementData.getSevenFinish();
        int tunnul = 28;
        int pause = 1;
        int captaintest = 5;
        int unkennel = 3;
        int inrepair = 4;
        int save = 5;
        int dismantle= 3;
        int transport = 2;
        int Total=48;
        String Tunnul=String.valueOf(tunnul);
        String Pause=String.valueOf(pause);
        String Captaintest=String.valueOf(captaintest);
        String Unkennel=String.valueOf(unkennel);
        String Inrepair=String.valueOf(inrepair);
        String Save=String.valueOf(save);
        String Dismantle=String.valueOf(dismantle);
        String Transport=String.valueOf(transport);
        total.setText("总  计："+Total+"台");
        tunnuL.setText("掘进"+tunnul+"台");
        pausE.setText("暂停"+pause+"台");
        captaintesT.setText("组长调试"+captaintest+"台");
        unkenneL.setText("出洞"+unkennel+"台");
        inrepaiR.setText("场内维修"+inrepair+"台");
        savE.setText("存放"+save+"台");
        dismantlE.setText("拆机、吊装"+dismantle+"台");
        transporT.setText("运输"+transport+"台");

        List<PieEntry> strings = new ArrayList<>();
        strings.add(new PieEntry(tunnul, Tunnul+"台"));
        strings.add(new PieEntry(pause, Pause));
        strings.add(new PieEntry(captaintest, Captaintest));
        strings.add(new PieEntry(unkennel, Unkennel));
        strings.add(new PieEntry(inrepair, Inrepair));
        strings.add(new PieEntry(save, Save));
        strings.add(new PieEntry(dismantle, Dismantle));
        strings.add(new PieEntry(transport, Transport));

        PieDataSet dataSet = new PieDataSet(strings, "");

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.login));
        colors.add(getResources().getColor(R.color.cornflowerblue));
        colors.add(getResources().getColor(R.color.Safe));
        colors.add(getResources().getColor(R.color.goldenrod));
        colors.add(getResources().getColor(R.color.lightcoral));
        colors.add(getResources().getColor(R.color.blueviolet));
        colors.add(getResources().getColor(R.color.orangered));
        colors.add(getResources().getColor(R.color.tan));
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(0f);
        pieData.setDrawValues(false);

        statusChart.setData(pieData);
        statusChart.invalidate();
//        progressChart.setCenterText(ratio+"%");  // 中心文本显示进度百分比
//        progressChart.setCenterTextSize(20f);
        statusChart.setEntryLabelTextSize(18f);
        statusChart.setHoleRadius(0f);    //  设置中心圆半径
        statusChart.setTransparentCircleRadius(0f);
        statusChart.setExtraOffsets(0, 10, 0, 0);// 设置饼状图距离上下左右的偏移量


        Legend mLegend = statusChart.getLegend();  // 设置比例图
        mLegend.setEnabled(false); // 设置禁用比例块

        Description mDescription = statusChart.getDescription(); // 设置右下方的文字描述
        mDescription.setEnabled(false); // 设置禁用右下方的文字描述

    }
    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.26
     * @description 初始化故障时间
     */
    private void initFaultTime(){
        lastmonthfault.setText(35+"小时");
        lastmonthfault.setVisibility(View.VISIBLE);

        thismonthfault.setText(40+"小时");
        thismonthfault.setVisibility(View.VISIBLE);

        lastweekfault.setText(4+"小时");
        lastweekfault.setVisibility(View.VISIBLE);

        thisweekfault.setText(4+"小时");
        thisweekfault.setVisibility(View.VISIBLE);


    }
    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.26
     * @description 初始化设备列表
     */
    private void initTable() {
        String risk_content = "";
        String cross_building = "";

        int i = 0;
        for (UnnormalListItemEntity bean : riskListData) {

            if (i == 0) {
                risk_content = bean.getUnusual_content();
                cross_building = bean.getCross_building();
            } else {
                if (risk_content != null && bean.getUnusual_content() != null) {
                    if (risk_content.length() < bean.getUnusual_content().length()) {
                        risk_content = bean.getUnusual_content();
                    }
                }
                if (cross_building != null && bean.getCross_building() != null) {
                    if (cross_building.length() < bean.getCross_building().length()) {
                        cross_building = bean.getCross_building();
                    }
                }
            }
            i++;
        }
        /**
         * 占位字符集合
         */
        List<String> maxDatas = new ArrayList<>();
        maxDatas.add("详情查看");
        maxDatas.add("开始中");
        maxDatas.add("结束中");
        maxDatas.add("Ⅲ级异常");
        maxDatas.add(risk_content);
        maxDatas.add(cross_building);
        maxDatas.add("已解决中");
        qualityAdapter = new ProjectUnnormalDetailAdapter(getApplicationContext(), riskListData, maxDatas);
        recyclerViewTable.setAdapter(qualityAdapter);

    }


}
