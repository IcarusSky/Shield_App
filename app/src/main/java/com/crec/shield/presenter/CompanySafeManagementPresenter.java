package com.crec.shield.presenter;
import com.crec.shield.base.BasePresenter;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.CompanySafeManagementContract;
import com.crec.shield.model.CompanySafeManagementModel;

import javax.inject.Inject;

public class CompanySafeManagementPresenter extends BasePresenter<CompanySafeManagementContract.View> implements CompanySafeManagementContract.Presenter{
    private CompanySafeManagementContract.Model model;
    @Inject
    public CompanySafeManagementPresenter(){
        model = new CompanySafeManagementModel();
    }

    @Override
    public void getRankData(String mToken, String orgId) {

        if (!isViewAttached()){
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getRankNetData(mToken,orgId,new Callback<String>(){
            @Override
            public void onSuccess(String data) {
                //调用view接口显示数据
                if(isViewAttached()){
                    mView.showRankData(data);
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
