package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.SubsidenceDataMonitorContract;
import com.crec.shield.entity.crec22.project.subsidencedatamonitor.GroundSettingData;
import com.crec.shield.model.SubsidenceDataMonitorModel;

import javax.inject.Inject;

public class SubsidenceDataMonitorPresenter extends BasePresenter<SubsidenceDataMonitorContract.View> implements SubsidenceDataMonitorContract.Presenter {

    private SubsidenceDataMonitorContract.Model model;

    @Inject
    public SubsidenceDataMonitorPresenter(){
        model = new SubsidenceDataMonitorModel();
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
        model.getNetData(new Callback<GroundSettingData>(){
            @Override
            public void onSuccess(GroundSettingData data) {
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
    public void getSelectData(String mMin,String mMax) {
        final String a = mMin;
        final String b = mMax;
        if (!isViewAttached()){
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getNetData(new Callback<GroundSettingData>(){
            @Override
            public void onSuccess(GroundSettingData data) {
                //调用view接口显示数据
                if(isViewAttached()){
                    mView.showSelectData(data,a,b);
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
