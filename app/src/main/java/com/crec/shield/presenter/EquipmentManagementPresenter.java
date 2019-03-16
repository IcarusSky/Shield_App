package com.crec.shield.presenter;

import com.crec.shield.contract.EquipmentManagementContract;
import com.crec.shield.model.EquipmentManagementModel;
import com.crec.shield.base.BasePresenter;
import com.crec.shield.base.Callback;
import com.crec.shield.entity.crec22.project.management.EquipmentManagerData;
import com.crec.shield.entity.crec22.project.management.ShieldDanttData;
import com.github.mikephil.charting.charts.BarChart;

import javax.inject.Inject;

public class EquipmentManagementPresenter extends BasePresenter<EquipmentManagementContract.View> implements EquipmentManagementContract.Presenter{
    private EquipmentManagementContract.Model model;

    @Inject
    public EquipmentManagementPresenter(){
        model = new EquipmentManagementModel();
    }
    @Override
    public void getData() {

        if (!isViewAttached()){
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getNetData(new Callback<EquipmentManagerData>(){
            @Override
            public void onSuccess(EquipmentManagerData data) {
                //调用view接口显示数据
                if(isViewAttached()){
                    mView.showData(data);
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
    @Override
    public void getShieldData(){
        if (!isViewAttached()){
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        model.getShieldNetData(new Callback<ShieldDanttData>(){
            @Override
            public void onSuccess(ShieldDanttData data) {
                //调用view接口显示数据
                if(isViewAttached()){
                    mView.showShieldDanttData(data);
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
