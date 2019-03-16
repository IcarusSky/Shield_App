package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.contract.CompanyMessageContract;
import com.crec.shield.contract.CompanyOverviewContract;

import javax.inject.Inject;

public class CompanyMessagePresenter extends BasePresenter<CompanyMessageContract.View> implements CompanyMessageContract.Presenter{

    @Inject
    public CompanyMessagePresenter(){

    }


}
