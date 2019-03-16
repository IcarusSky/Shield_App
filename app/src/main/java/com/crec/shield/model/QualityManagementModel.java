package com.crec.shield.model;

import com.alibaba.fastjson.JSON;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.QualityManagementContract;
import com.crec.shield.entity.crec22.project.qualitymanagement.QualityBiasnEntity;
import com.crec.shield.entity.crec22.project.qualitymanagement.QualityBiasnResponse;
import com.crec.shield.utils.AppUtils;

import static com.lzy.okgo.OkGo.getContext;


public class QualityManagementModel implements QualityManagementContract.Model{
    private QualityBiasnResponse qualityBiasnResponse;
    private QualityBiasnEntity qualityBiasnEntity = new QualityBiasnEntity();

    @Override
    public void getNetData(final Callback callback) {
        String json = AppUtils.getJson("json/crec22/project/management/QualityManagement/QualityBiasn.json", getContext());
        qualityBiasnResponse = JSON.parseObject(json,QualityBiasnResponse.class);
        qualityBiasnEntity = qualityBiasnResponse.getData();
        callback.onSuccess(qualityBiasnEntity);
        callback.onComplete();
    }
}
