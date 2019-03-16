package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.SystemListContract;
import com.crec.shield.entity.crec22.project.parameterlist.ParameterListEntity;
import com.crec.shield.model.SystemListModel;

import java.util.List;

import javax.inject.Inject;

public class SystemListPresenter extends BasePresenter<SystemListContract.View> implements SystemListContract.Presenter {
    private SystemListContract.Model model;

    @Inject
    public SystemListPresenter(){
        model = new SystemListModel();
    }

    @Override
    public void getSystemListData() {
        if(!isViewAttached()){
            return ;
        }
        mView.showLoading();
        model.getSystemListNetData(new Callback<List<ParameterListEntity>>() {

            @Override
            public void onSuccess(List<ParameterListEntity> data) {
                if(isViewAttached()){
                    mView.showSystemListData(data);
                }
            }

            @Override
            public void onFailure(String msg) {
                if(isViewAttached()){
                    mView.showToast(msg);
                }
            }

            @Override
            public void onError() {
                if(isViewAttached()){
                    mView.showError();
                }

            }

            @Override
            public void onComplete() {
                if(isViewAttached()){
                    mView.hideLoading();
                }

            }
        } );

    }
}
