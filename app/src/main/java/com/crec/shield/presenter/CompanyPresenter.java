package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.contract.CompanyContract;

import javax.inject.Inject;

public class CompanyPresenter extends BasePresenter<CompanyContract.View> implements CompanyContract.Presenter{

    @Inject
    public CompanyPresenter(){

    }

}
