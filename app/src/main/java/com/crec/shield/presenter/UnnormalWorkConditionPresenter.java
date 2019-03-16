package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.UnnormalWorkConditionContract;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalHeadData;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalHeadDataResponse;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalListItemEntity;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalListItemResponse;
import com.crec.shield.model.UnnormalWorkConditionModel;

import java.util.List;

import javax.inject.Inject;

public class UnnormalWorkConditionPresenter extends BasePresenter<UnnormalWorkConditionContract.View> implements UnnormalWorkConditionContract.Presenter {
    private UnnormalWorkConditionContract.Model model;

    @Inject
    public UnnormalWorkConditionPresenter() {
        model = new UnnormalWorkConditionModel();
    }
    @Override
    public void getUnnormalData() {

        if (!isViewAttached()) {
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getUnnormalNetData(new Callback<UnnormalHeadData>() {

            @Override
            public void onSuccess(UnnormalHeadData data) {
                //调用view接口显示数据
                if (isViewAttached()) {
                    mView.showUnnormalData(data);
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
    public void getUnnormalGanttData() {
        if (!isViewAttached()) {
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getUnnormalGanttNetData(new Callback<String>() {

            @Override
            public void onSuccess(String s) {
                //调用view接口显示数据
                if (isViewAttached()) {
                    mView.showUnnormalGanttData(s);
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
    public void getUnnormalListData() {

        if (!isViewAttached()) {
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getUnnormalListNetData(new Callback<List<UnnormalListItemEntity>>() {

            @Override
            public void onSuccess(List<UnnormalListItemEntity> data) {
                //调用view接口显示数据
                if (isViewAttached()) {
                    mView.showUnnormalListData(data);
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
