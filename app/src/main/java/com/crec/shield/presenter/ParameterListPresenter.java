package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.ParameterListContract;
import com.crec.shield.entity.crec22.project.parameterlist.ParameterListEntity;
import com.crec.shield.model.ParameterListModel;
import java.util.List;



import javax.inject.Inject;

public class ParameterListPresenter extends BasePresenter<ParameterListContract.View> implements ParameterListContract.Presenter{

    private ParameterListContract.Model model;

    @Inject
    public ParameterListPresenter(){
        model = new ParameterListModel();
    }
    @Override
    public void getParameterListData() {

        if (!isViewAttached()) {
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getParameterListNetData(new Callback<List<ParameterListEntity>>() {

            @Override
            public void onSuccess(List<ParameterListEntity> data) {
                //调用view接口显示数据
                if (isViewAttached()) {
                    mView.showParameterListData(data);
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





