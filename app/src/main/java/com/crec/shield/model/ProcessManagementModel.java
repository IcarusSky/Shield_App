package com.crec.shield.model;

import com.alibaba.fastjson.JSON;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.ProcessManagementContract;
import com.crec.shield.entity.crec22.project.processmanagement.ProcessManagementEntity;
import com.crec.shield.entity.crec22.project.processmanagement.ProcessManagementResponse;
import com.crec.shield.utils.AppUtils;

import static com.lzy.okgo.OkGo.getContext;

public class ProcessManagementModel implements ProcessManagementContract.Model {

    private static ProcessManagementResponse processManagementResponse;
    private static ProcessManagementEntity processManagementData;

    @Override
    public void getNetData(final Callback callback) {

        String json = AppUtils.getJson("json/crec22/project/management/ProcessManagement/DrivingEfficirncy.json", getContext());
        processManagementResponse = JSON.parseObject(json, ProcessManagementResponse.class);
        processManagementData = processManagementResponse.getData();
        callback.onSuccess(processManagementData);
        callback.onComplete();

    }
}
