package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.DiggingDataContract;
import com.crec.shield.model.DiggingDataModel;
import com.crec.shield.entity.crec22.project.companydiggingdata.EquipmentData;
import com.crec.shield.entity.crec22.project.companydiggingdata.PointParameter;
import com.crec.shield.entity.crec22.project.companydiggingdata.ParameterListEntity;


import java.util.List;

import javax.inject.Inject;

public class DiggingDataPresenter extends BasePresenter<DiggingDataContract.View> implements DiggingDataContract.Presenter{
    private DiggingDataContract.Model model;

    @Inject
    public DiggingDataPresenter(){
        model = new DiggingDataModel();
    }
    @Override
    public void getEquipmentData(String mToken, String lineId) {

        if (!isViewAttached()){
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getEquipmentNetData(mToken, lineId,new Callback<EquipmentData>(){
            @Override
            public void onSuccess(EquipmentData data) {
                //调用view接口显示数据
                if(isViewAttached()){
                    mView.showEquipmentData(data);
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
    public void getPointData(String mToken, String lineId) {

        if (!isViewAttached()){
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getPointNetData(mToken,lineId,new Callback<List<PointParameter>>(){
            @Override
            public void onSuccess(List<PointParameter> data) {
                //调用view接口显示数据
                if(isViewAttached()){
                    mView.showPointData(data);
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
    public void getPlaneData(String mToken, String lineId) {

        if (!isViewAttached()){
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getPlaneNetData(mToken,lineId,new Callback<String>(){
            @Override
            public void onSuccess(String data) {
                //调用view接口显示数据
                if(isViewAttached()){
                    mView.showPlaneData(data);
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
