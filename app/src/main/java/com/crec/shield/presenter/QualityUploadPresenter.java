package com.crec.shield.presenter;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.contract.QualityUploadContract;
import com.crec.shield.model.QualityUploadModel;


import javax.inject.Inject;

public class QualityUploadPresenter extends BasePresenter<QualityUploadContract.View> implements QualityUploadContract.Presenter {

    private QualityUploadContract.Model model;

    @Inject
    public  QualityUploadPresenter(){
        model = new QualityUploadModel();
    }




    @Override
    public void getData() {

    }
}
