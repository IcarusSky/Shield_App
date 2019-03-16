package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.contract.ProjectCameraDataContract;
import com.crec.shield.model.ProjectCameraDataModel;

import javax.inject.Inject;

public class ProjectCameraDataPresenter extends BasePresenter<ProjectCameraDataContract.View> implements ProjectCameraDataContract.Presenter{

    private ProjectCameraDataContract.Model model;

    @Inject
    public ProjectCameraDataPresenter(){
        model = new ProjectCameraDataModel();
    }

}
