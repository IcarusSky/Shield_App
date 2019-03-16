package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.contract.ProjectContract;

import javax.inject.Inject;

public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter{

    @Inject
    public ProjectPresenter(){

    }

}
