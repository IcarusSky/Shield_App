package com.crec.shield.model;

import com.alibaba.fastjson.JSON;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.ProjectListForCityContract;
import com.crec.shield.entity.crec22.project.projectlistforcity.ProjectListForCityData;
import com.crec.shield.entity.crec22.project.projectlistforcity.ProjectListForCityResponse;
import com.crec.shield.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import static com.lzy.okgo.OkGo.getContext;


public class ProjectListForCityModel implements ProjectListForCityContract.Model{
    private ProjectListForCityResponse projectListForCityResponse;
    private List<ProjectListForCityData> projectListForCityData = new ArrayList<>();

    @Override
    public void getNetData(final Callback callback) {
        String json = AppUtils.getJson("json/crec22/project/management/ProjectListForCity/ProjectListForCity.json", getContext());
        projectListForCityResponse = JSON.parseObject(json,ProjectListForCityResponse.class);
        projectListForCityData = projectListForCityResponse.getData();
        callback.onSuccess(projectListForCityData);
        callback.onComplete();
    }
}
