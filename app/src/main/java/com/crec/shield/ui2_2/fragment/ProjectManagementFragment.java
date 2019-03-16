package com.crec.shield.ui2_2.fragment;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.di.FragmentComponent;
import com.crec.shield.ui2_2.activity.EquipmentManagementActivity;
import com.crec.shield.ui2_2.activity.QualityManagementActivity;
import com.crec.shield.ui2_2.activity.SafetyManagementActivity;
import com.crec.shield.base.BaseFragment;
import com.crec.shield.contract.ProjectManagementContract;
import com.crec.shield.entity.crec22.project.management.ProjectSystemData;
import com.crec.shield.entity.crec22.project.management.RingRatio;
import com.crec.shield.entity.crec22.project.management.StatusScale;
import com.crec.shield.presenter.ProjectManagementPresenter;
import com.crec.shield.ui2_2.activity.ProgressManagementActivity;
import com.crec.shield.utils.SysApplication;
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


public class ProjectManagementFragment extends BaseFragment <ProjectManagementPresenter> implements ProjectManagementContract.View{

    private Unbinder unbinder;
    private ProjectSystemData projectSystemData;
    private RingRatio ringRatio;          //  环数比例
    private StatusScale statusScale; // 设备状态比例饼图


    // 进度管理
    @BindView(R.id.progress_management)
    ImageView progressManagement;
    @BindView(R.id.progress_chart)
    PieChart progressChart;
    @BindView(R.id.percent)
    TextView percent;
    @BindView(R.id.current_ring)
    TextView currentRing;
    @BindView(R.id.today_rings)
    TextView todayRing;
    @BindView(R.id.yesterday_rings)
    TextView yesterdayRing;
    @BindView(R.id.week_rings)
    TextView weekRing;

    // 安全管理
    @BindView(R.id.safety_management)
    ImageView safetyManagement;
    @BindView(R.id.current_risk)
    TextView currentRisk;
    @BindView(R.id.coming_risk)
    TextView comingRisk;
    @BindView(R.id.total_risk)
    TextView totalRisk;
    @BindView(R.id.current_working_condition)
    TextView currentWorkingCondition;
    @BindView(R.id.coming_working_condition)
    TextView comingWorkingCondition;
    @BindView(R.id.total_working_condition)
    TextView totalWorkingCondition;

    // 设备管理
    @BindView(R.id.equipment_management)
    ImageView equipmentManagement;
    @BindView(R.id.equipment_chart)
    PieChart equipmentChart;
    @BindView(R.id.equipment_name)
    TextView equipmentName;
    @BindView(R.id.today_condition)
    TextView todayCondition;
    @BindView(R.id.total_rings)
    TextView totalRings;

    // 质量管理
    @BindView(R.id.quality_chart)
    PieChart qualityChart;
    @BindView(R.id.normal_rings)
    TextView normalRings;
    @BindView(R.id.abnormal_rings)
    TextView abnormalRings;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_management;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(this.getLayoutId(), container, false);
        SysApplication.getInstance().addActivity(getActivity());
        unbinder = ButterKnife.bind(this, view);
        mPresenter.attachView(this);
        mPresenter.getData();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                safetyIntent.setClass(getContext(), SafetyManagementActivity.class);
                startActivity(safetyIntent);
                break;
            case R.id.equipment_management:
                Intent equipmentIntent = new Intent();
                equipmentIntent.setClass(getContext(), EquipmentManagementActivity.class);
                startActivity(equipmentIntent);
                break;
            case R.id.quality_management:
                Intent qualityIntent = new Intent();
                qualityIntent.setClass(getContext(), QualityManagementActivity.class);
                startActivity(qualityIntent);
                break;
        }
    }

    @Override
    protected void initInject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void showData(ProjectSystemData data) {
        projectSystemData = data;
        initProgressChart(projectSystemData);  // 进度管理
        initSafetyData(projectSystemData);     // 安全管理
        initEquipmentChart(projectSystemData); // 设备管理
        initQualityChart(projectSystemData);   // 质量管理

    }

//    private void init(){
//        final List<String> com1 = new ArrayList<String>();
//        String mToken = SPUtils.get((AppConstant.LOGINSTATUS.token),"").toString();
//        String projectId = SPUtils.get((AppConstant.PROJECT.projectId),"").toString();
//        final String lineId = SPUtils.get((AppConstant.PROJECT.lineId),"").toString();
//        OkGo.post(Url.BASE_URL+ Url.OVERVIEW_CAMERA_LINE_CONDITION)
//                .params("token",mToken)
//                .params("projectId",projectId)
//                .execute(new JsonCallback<ProjectDetailsResponse>() {
//
//                    @Override
//                    public void onSuccess(ProjectDetailsResponse projectDetailsResponse, Call call, Response response) {
//                        if (projectDetailsResponse.getCode() == 1){
//                            ArrayAdapter<String> adapter = null;
//                            int index = 0;
//                            final List<ProjectDetailsEntity> projectDetailsEntity= projectDetailsResponse.getData();
//                            for (ProjectDetailsEntity project:projectDetailsEntity){
//                                com1.add(project.getConditionName()+"（"+project.getTag()+"）");
//                                if(lineId.equals(project.getConditionId()))
//                                {
//                                    index = projectDetailsEntity.indexOf(project);
//                                }
//                            }
//                            adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout_white, com1);  //下拉框字体颜色
//                            adapter.setDropDownViewResource(R.layout.spinner_layout_black);
//                            mToolbar_title.setAdapter(adapter);
//                            if(lineId==null || lineId.length()==0) {
//                                SPUtils.put(AppConstant.PROJECT.lineId, projectDetailsEntity.get(index).getConditionId());
//                            }
//                            mToolbar_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
//                                @Override
//                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                                    for (ProjectDetailsEntity project:projectDetailsEntity){
//                                        if (com1.get(position).equals(project.getConditionName()+"（"+project.getTag()+"）")){
//                                            SPUtils.put(AppConstant.PROJECT.lineTag, project.getTag());
//                                            SPUtils.put(AppConstant.PROJECT.lineId,project.getConditionId());
////                                            initFragmentView();
//                                        }
//                                    }
//                                }
//                                @Override
//                                public void onNothingSelected(AdapterView<?> parent) {
////                                    initFragmentView();
//                                }
//                            });
//                            mToolbar_title.setSelection(index,true);
//                        }
//                    }
//                });
//    }

    /*
     *  @company vinelinx
     * @author wangqi
     * @date 2018.1.16
     * @description 项目级-项目管理-进度管理
     * */
    private void initProgressChart(ProjectSystemData projectSystemData) {

        ringRatio =  projectSystemData.getRingRatio();  // 环形进度圈比例
        int current = ringRatio.getCurrent();
        int total = ringRatio.getTotal();
        String ratio = String.valueOf(current*100/total);
        int current_ring = projectSystemData.getPresentRings();
        int today_ring = projectSystemData.getTodayFinish();
        int yesterday_ring = projectSystemData.getYesterdayFinish();
        int week_ring = projectSystemData.getSevenFinish();

        percent.setText(current+"/"+total);
        currentRing.setText(current_ring+"环");
        todayRing.setText(today_ring+"环");
        yesterdayRing.setText(yesterday_ring+"环");
        weekRing.setText(week_ring+"环");

        List<PieEntry> strings = new ArrayList<>();
        strings.add(new PieEntry(30f, ""));
        strings.add(new PieEntry(70f, ""));

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
        progressChart.setCenterText(ratio+"%");  // 中心文本显示进度百分比
        progressChart.setCenterTextSize(20f);
        progressChart.setHoleRadius(80f);    //  设置中心圆半径
        progressChart.setTransparentCircleRadius(0f);
        progressChart.setExtraOffsets(0, 10, 0, 0);// 设置饼状图距离上下左右的偏移量


        Legend mLegend = progressChart.getLegend();  // 设置比例图
        mLegend.setEnabled(false); // 设置禁用比例块

        Description mDescription = progressChart.getDescription(); // 设置右下方的文字描述
        mDescription.setEnabled(false); // 设置禁用右下方的文字描述

    }

    /*
     *  @company vinelinx
     * @author wangqi
     * @date 2018.1.16
     * @description 项目级-项目管理-安全管理
     * */
    private void initSafetyData(ProjectSystemData projectSystemData) {

        int current_risk = projectSystemData.getPresentRings();
        int coming_risk = projectSystemData.getTodayFinish();
        int total_risk = projectSystemData.getYesterdayFinish();
        int current_working_condition = projectSystemData.getSevenFinish();
        int coming_working_condition = projectSystemData.getSevenFinish();
        int total_working_condition = projectSystemData.getSevenFinish();

        currentRisk.setText(current_risk+"环");
        comingRisk.setText(coming_risk+"环");
        totalRisk.setText(total_risk+"环");
        currentWorkingCondition.setText(current_working_condition+"环");
        comingWorkingCondition.setText(coming_working_condition+"环");
        totalWorkingCondition.setText(total_working_condition+"环");

    }

    /*
     *  @company vinelinx
     * @author wangqi
     * @date 2018.1.16
     * @description 项目级-项目管理-设备管理
     * */

    private void initEquipmentChart(ProjectSystemData projectSystemData) {


        statusScale = projectSystemData.getStatusScale();
        float driving = (float)statusScale.getDriving();    //  （掘进）
        float trouble = (float)statusScale.getTrouble();    //  （故障）
        float close = (float)statusScale.getClose();      //  （停机）
        String equipment_name = projectSystemData.getDeviceName();
        int today_condition = projectSystemData.getTodayStatus();
        int total_rings = projectSystemData.getTotalNumber();

        equipmentName.setText(equipment_name+"");
        todayCondition.setText(today_condition+"环");
        totalRings.setText(total_rings+"环");

        List<PieEntry> string = new ArrayList<>();
        string.add(new PieEntry(trouble, "故障"));
        string.add(new PieEntry(close, "停机"));
        string.add(new PieEntry(driving, "掘进"));

        PieDataSet dataSet = new PieDataSet(string, "");

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.Warning));
        colors.add(getResources().getColor(R.color.Danger));
        colors.add(getResources().getColor(R.color.radio));
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
        equipmentChart.setExtraOffsets(0, 3, 0, 3);//设置饼状图距离上下左右的偏移量

        Legend mLegend = equipmentChart.getLegend();  //设置比例图
        mLegend.setEnabled(false); // 禁用比例图

        Description mDescription = equipmentChart.getDescription();  // 设置右下方的文字描述
        mDescription.setEnabled(false);  // 设置禁用右下方的文字描述

    }

    /*
     *  @company vinelinx
     * @author wangqi
     * @date 2018.1.16
     * @description 项目级-项目管理-质量管理
     * */

    private void initQualityChart(ProjectSystemData projectSystemData) {

        int normal_rings = projectSystemData.getPresentRings();
        int abnormal_rings = projectSystemData.getTodayFinish();

        normalRings.setText("正常环数  "+normal_rings+" 环");
        abnormalRings.setText("异常环数  "+abnormal_rings+" 环");

        List<PieEntry> string = new ArrayList<>();
        string.add(new PieEntry(30f, "正常"));
        string.add(new PieEntry(70f, "异常"));

        PieDataSet dataSet = new PieDataSet(string, "");

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.Safe));
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
        qualityChart.setExtraOffsets(0, 3, 0, 3);//设置饼状图距离上下左右的偏移量


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


    @Override
    public void onDestroy() {
        super.onDestroy();
        //断开View引用
        mPresenter.detachView();
        unbinder.unbind();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showError() {

    }


}
