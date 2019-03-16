package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.ProgressManagementContract;
import com.crec.shield.entity.crec22.project.management.ProjectSystemData;
import com.crec.shield.entity.crec22.project.progress.DayAndNightEntity;
import com.crec.shield.entity.project.progress.ProgressEntity;
import com.crec.shield.model.ProgressManagementModel;

import java.util.List;

import javax.inject.Inject;


public class ProgressManagementPresenter extends BasePresenter<ProgressManagementContract.View> implements ProgressManagementContract.Presenter{

    private ProgressManagementContract.Model model;

    @Inject
    public ProgressManagementPresenter(){
        model = new ProgressManagementModel();
    }

    /**
     * @company vinelinx
     * @author WangQi
     * @date 2019.1.25
     * @description 处理进度环比图数据
     */
    @Override
    public void handleProgressRatioData() {

        if(!isViewAttached()){
            //如果没有View引用就不加载数据
            return;
        }
        mView.showLoading();
        model.getProgressRatioData(new Callback<String>() {
            @Override
            public void onSuccess(String data) {
                //调用view接口显示数据
                if(isViewAttached()){
                    mView.showProgressRatio(data);
                }
            }

            @Override
            public void onFailure(String msg) {
                //调用view接口提示失败信息
                if(isViewAttached()){
                    mView.showToast(msg);
                }
            }

            @Override
            public void onError() {
                //调用view接口提示请求异常
                if(isViewAttached()){
                    mView.showError();
                }
            }
            @Override
            public void onComplete() {
                // 隐藏正在加载进度条
                if(isViewAttached()){
                    mView.hideLoading();
                }
            }
        });
    }

    /**
     * @company vinelinx
     * @author WangQi
     * @date 2019.1.26
     * @description 处理总体概览数据
     */
    @Override
    public void handleOverallOverviewData() {

        if(!isViewAttached()){
            //如果没有View引用就不加载数据
            return;
        }
        mView.showLoading();
        model.getOverallOverviewData(new Callback<ProjectSystemData>() {
            @Override
            public void onSuccess(ProjectSystemData data) {
                if(isViewAttached()){
                    mView.showOverallOverview(data);
                }
            }

            @Override
            public void onFailure(String msg) {
                //调用view接口提示失败信息
                if(isViewAttached()){
                    mView.showToast(msg);
                }
            }

            @Override
            public void onError() {
                //调用view接口提示请求异常
                if(isViewAttached()){
                    mView.showError();
                }
            }
            @Override
            public void onComplete() {
                // 隐藏正在加载进度条
                if(isViewAttached()){
                    mView.hideLoading();
                }
            }
        });
    }


    /**
     * @company vinelinx
     * @author WangQi
     * @date 2019.1.26
     * @description 处理白班夜班工效统计图数据
     */
    @Override
    public void handleWorkEfficiencyChartData() {
        if(!isViewAttached()){
            return;
        }
        mView.showLoading();
        model.getWorkEfficiencyChartData(new Callback<List<DayAndNightEntity>>() {
            @Override
            public void onSuccess(List<DayAndNightEntity> data) {
                if(isViewAttached()){
                    mView.showWorkEfficiencyChart(data);
                }
            }

            @Override
            public void onFailure(String msg) {
                //调用view接口提示失败信息
                if(isViewAttached()){
                    mView.showToast(msg);
                }
            }

            @Override
            public void onError() {
                //调用view接口提示请求异常
                if(isViewAttached()){
                    mView.showError();
                }
            }
            @Override
            public void onComplete() {
                // 隐藏正在加载进度条
                if(isViewAttached()){
                    mView.hideLoading();
                }
            }
        });
    }

    /**
     * @company vinelinx
     * @author WangQi
     * @date 2019.1.24
     * @description 处理盾构机运行状态数据
     */
    @Override
    public void handleShieldGanttChartData() {
        if (!isViewAttached()){
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getShieldGanttChartData(new Callback<String>(){
            @Override
            public void onSuccess(String data) {
                //调用view接口显示数据
                if(isViewAttached()){
                    mView.showShieldGanttChart(data);
                }
            }
            @Override
            public void onFailure(String msg) {
                //调用view接口提示失败信息
                if(isViewAttached()){
                    mView.showToast(msg);
                }
            }
            @Override
            public void onError() {
                //调用view接口提示请求异常
                if(isViewAttached()){
                    mView.showError();
                }
            }
            @Override
            public void onComplete() {
                // 隐藏正在加载进度条
                if(isViewAttached()){
                    mView.hideLoading();
                }
            }
        });
    }
}
