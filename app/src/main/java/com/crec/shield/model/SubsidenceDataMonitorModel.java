package com.crec.shield.model;

import com.alibaba.fastjson.JSON;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.SubsidenceDataMonitorContract;
import com.crec.shield.entity.crec22.project.subsidencedatamonitor.GroundSettingData;
import com.crec.shield.entity.crec22.project.subsidencedatamonitor.GroundSettingResponse;
import com.crec.shield.utils.AppUtils;

import static com.lzy.okgo.OkGo.getContext;


public class SubsidenceDataMonitorModel implements SubsidenceDataMonitorContract.Model{
    private GroundSettingResponse groundSettingResponse;
    private GroundSettingData groundSettingData = new GroundSettingData();

    @Override
    public void getNetData(final Callback callback) {
        String json = AppUtils.getJson("json/crec22/project/management/SubsidenceDataMonitor/GroundSetting.json", getContext());
        groundSettingResponse = JSON.parseObject(json,GroundSettingResponse.class);
        groundSettingData = groundSettingResponse.getData();
        callback.onSuccess(groundSettingData);
        callback.onComplete();
    }
}
