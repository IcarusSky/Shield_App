package com.crec.shield.contract;

import com.crec.shield.base.BaseView;
import com.crec.shield.base.Callback;
import com.crec.shield.entity.crec22.project.management.ProjectSystemData;
import com.crec.shield.entity.crec22.project.progress.DayAndNightEntity;

import java.util.List;

public interface ProgressManagementContract {

    interface Model {

        void getProgressRatioData(Callback callback);       //  获取进度环比图数据
        void getOverallOverviewData(Callback callback);     //  获取总体概览数据
        void getWorkEfficiencyChartData(Callback callback); //  获取白班夜班工效统计数据
        void getShieldGanttChartData(Callback callback);    //  获取盾构机运行状态数据

    }

    interface View extends BaseView {

        void showProgressRatio(String ShieldWorkProgressData); //  展示进度环比图
        void showOverallOverview(ProjectSystemData projectSystemData);  //  展示总体概览
        void showWorkEfficiencyChart(List<DayAndNightEntity> dayAndNightEntity); //  展示白班夜班工效统计
        void showShieldGanttChart(String ShieldGanttData);     //  展示盾构机运行状态

    }

    interface Presenter {

        void handleProgressRatioData();        //  处理进度环比图数据
        void handleOverallOverviewData();      //  处理总体概览数据
        void handleWorkEfficiencyChartData();  //  处理白班夜班工效统计数据
        void handleShieldGanttChartData();     //  处理盾构机运行状态数据

    }
}
