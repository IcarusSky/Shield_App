package com.crec.shield.presenter;

import com.crec.shield.model.SafetyManagementModel;
import com.crec.shield.base.BasePresenter;
import com.crec.shield.contract.SafetyManagementContract;

import javax.inject.Inject;


public class SafetyManagementPresenter extends BasePresenter<SafetyManagementContract.View> implements SafetyManagementContract.Presenter{

    private SafetyManagementContract.Model model;

    @Inject
    public SafetyManagementPresenter() {
        model = new SafetyManagementModel();
    }

}