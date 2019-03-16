package com.crec.shield.model;

import com.alibaba.fastjson.JSON;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.CompanyOverviewContract;
import com.crec.shield.entity.crec22.project.projectdevice.Data;
import com.crec.shield.entity.crec22.project.projectdevice.ProjectDeviceResponse;
import com.crec.shield.utils.AppUtils;
import static com.lzy.okgo.OkGo.getContext;
public class CompanyOverviewModel implements CompanyOverviewContract.Model {
    private static ProjectDeviceResponse projectDeviceResponse;
    private static Data data;
    @Override
    public void getProjectDeviceNetData(Callback callback) {
        String json=AppUtils.getJson("json/crec22/project/management/CompanyHome/HorseRaceIamp.json",getContext());
        projectDeviceResponse=JSON.parseObject(json, ProjectDeviceResponse.class);
        data=projectDeviceResponse.getData();
        callback.onSuccess(data);
        callback.onComplete();
    }
}
