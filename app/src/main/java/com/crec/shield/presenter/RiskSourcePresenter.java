package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.RiskSourceContract;
import com.crec.shield.demo.LoginModel;
import com.crec.shield.entity.project.risk.RiskHeadData;
import com.crec.shield.entity.project.risk.RiskListItemEntity;
import com.crec.shield.model.RiskSourceModel;

import java.util.List;

import javax.inject.Inject;

public class RiskSourcePresenter extends BasePresenter<RiskSourceContract.View> implements RiskSourceContract.Presenter{

    private RiskSourceContract.Model model;

    @Inject
    public RiskSourcePresenter() {
        model = new RiskSourceModel();
    }

    @Override
    public void getRiskSourceData() {

        if (!isViewAttached()) {
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getRiskSourceNetData(new Callback<RiskHeadData>() {

            @Override
            public void onSuccess(RiskHeadData data) {
                //调用view接口显示数据
                if (isViewAttached()) {
                    mView.showRiskSourceData(data);
                }
            }

            @Override
            public void onFailure(String type) {
                //调用view接口提示失败信息
                if (isViewAttached()) {
                    mView.showWithoutData(type);
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

    @Override
    public void getRiskGanttData() {
        if (!isViewAttached()) {
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getRiskGanttNetData(new Callback<String>() {

            @Override
            public void onSuccess(String s) {
                //调用view接口显示数据
                if (isViewAttached()) {
                    mView.showRiskGanttData(s);
                }
            }

            @Override
            public void onFailure(String type) {
                //调用view接口提示失败信息
                if (isViewAttached()) {
                    mView.showWithoutData(type);
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

    @Override
    public void getRiskListData() {

        if (!isViewAttached()) {
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getRiskListNetData(new Callback<List<RiskListItemEntity>>() {

            @Override
            public void onSuccess(List<RiskListItemEntity> data) {
                //调用view接口显示数据
                if (isViewAttached()) {
                    mView.showRiskListData(data);
                }
            }

            @Override
            public void onFailure(String type) {
                //调用view接口提示失败信息
                if (isViewAttached()) {
                    mView.showWithoutData(type);
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
