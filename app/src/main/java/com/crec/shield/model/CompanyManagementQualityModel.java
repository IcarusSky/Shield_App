package com.crec.shield.model;

import com.alibaba.fastjson.JSON;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.CompanyManagementQualityContract;
import com.crec.shield.entity.crec22.project.companymanagementquality.LineQualityEntity;
import com.crec.shield.entity.crec22.project.companymanagementquality.CompanyManagementQualityResponse;
import com.crec.shield.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import static com.lzy.okgo.OkGo.getContext;

public class CompanyManagementQualityModel implements CompanyManagementQualityContract.Model {
    private static CompanyManagementQualityResponse companyManagementQualityResponse;
    private static List<LineQualityEntity> lineQualityEntity=new ArrayList<>();

    @Override
    public void getCompanyManagementQualityNetData(final Callback callback){
        String json=AppUtils.getJson("json/crec22/project/management/companymanagementquality/ProjectQualit.json",getContext());
        companyManagementQualityResponse =JSON.parseObject(json, CompanyManagementQualityResponse.class);
        lineQualityEntity= companyManagementQualityResponse.getData();
        callback.onSuccess(lineQualityEntity);
        callback.onComplete();
    }

}

