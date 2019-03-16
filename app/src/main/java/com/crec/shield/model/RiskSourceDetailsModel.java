package com.crec.shield.model;

import com.alibaba.fastjson.JSON;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.RiskSourceDetailsContract;
import com.crec.shield.entity.crec22.project.management.RiskDetailsData;
import com.crec.shield.entity.crec22.project.management.RiskDetailsResponse;
import com.crec.shield.utils.AppUtils;

import static com.lzy.okgo.OkGo.getContext;

public class RiskSourceDetailsModel implements RiskSourceDetailsContract.Model{
    private static RiskDetailsResponse riskDetailsResponse;
    private static RiskDetailsData riskDetailsData;

    @Override
    public void getNetData(final Callback callback) {

        String json = AppUtils.getJson("json/crec22/project/management/RiskSourceDetails/RiskDetails.json", getContext());
        riskDetailsResponse = JSON.parseObject(json, RiskDetailsResponse.class);
        riskDetailsData = riskDetailsResponse.getData();
        callback.onSuccess(riskDetailsData);
        callback.onComplete();

    }
}
