package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.contract.CameraDataContract;

import javax.inject.Inject;

public class CameraDataPresenter extends BasePresenter<CameraDataContract.View> implements CameraDataContract.Presenter {

    @Inject
    public CameraDataPresenter(){

    }

}
