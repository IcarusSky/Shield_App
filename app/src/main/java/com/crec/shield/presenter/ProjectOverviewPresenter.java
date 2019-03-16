package com.crec.shield.presenter;
import com.crec.shield.base.BasePresenter;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.ProjectOverviewContract;
import com.crec.shield.entity.crec22.project.projectoverview.ProjectOverviewData;
import com.crec.shield.model.ProjectOverviewModel;
import javax.inject.Inject;

public class ProjectOverviewPresenter extends BasePresenter<ProjectOverviewContract.View> implements ProjectOverviewContract.Presenter{

    private ProjectOverviewContract.Model model;

        @Inject
        public ProjectOverviewPresenter(){
            model = new ProjectOverviewModel();
        }
        @Override
        public void getData() {
            // 调用Model请求数据
            model.getNetData(new Callback<ProjectOverviewData>(){
                @Override
                public void onSuccess(ProjectOverviewData data) {
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


    }


