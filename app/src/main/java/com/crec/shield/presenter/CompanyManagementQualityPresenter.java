package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.CompanyManagementQualityContract;
import com.crec.shield.entity.crec22.project.companymanagementquality.LineQualityEntity;
import com.crec.shield.model.CompanyManagementQualityModel;

import java.util.List;

import javax.inject.Inject;

public class CompanyManagementQualityPresenter extends BasePresenter<CompanyManagementQualityContract .View> implements CompanyManagementQualityContract.Presenter{
    private CompanyManagementQualityContract.Model model;

    @Inject
    public CompanyManagementQualityPresenter(){
        model = new CompanyManagementQualityModel();
    }


    @Override
    public void getCompanyManagementQualityData() {


        if (!isViewAttached()) {
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getCompanyManagementQualityNetData(new Callback<List<LineQualityEntity>>() {

            @Override
            public void onSuccess(List<LineQualityEntity> data) {
                //调用view接口显示数据
                if (isViewAttached()) {
                    mView.showCompanyManagementQualityData(data);
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
