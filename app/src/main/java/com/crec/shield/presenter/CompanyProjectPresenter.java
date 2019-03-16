package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.contract.CompanyOverviewContract;
import com.crec.shield.contract.CompanyProjectContract;

import javax.inject.Inject;

public class CompanyProjectPresenter extends BasePresenter<CompanyProjectContract.View> implements CompanyProjectContract.Presenter {

    @Inject
    public CompanyProjectPresenter(){

    }


}
