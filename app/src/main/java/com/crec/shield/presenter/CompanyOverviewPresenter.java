package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.CompanyOverviewContract;
import com.crec.shield.entity.crec22.project.projectdevice.Data;
import com.crec.shield.model.CompanyOverviewModel;

import javax.inject.Inject;

public class CompanyOverviewPresenter extends BasePresenter<CompanyOverviewContract.View> implements CompanyOverviewContract.Presenter{
    private CompanyOverviewContract.Model model;
    @Inject
    public CompanyOverviewPresenter(){
        model = new CompanyOverviewModel();
    }

    @Override
    public void getProjectDeviceData() {
        if (!isViewAttached()) {
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getProjectDeviceNetData(new Callback<Data>() {

            @Override
            public void onSuccess(Data data) {
                //调用view接口显示数据
                if (isViewAttached()) {
                    mView.showProjectDeviceData(data);
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
                if (isViewAttached()) {
                    mView.showError();
                }
            }

            @Override
            public void onComplete() {
                // 隐藏正在加载进度条
                if (isViewAttached()) {
                    mView.hideLoading();
                }
            }
        });

    }
}


