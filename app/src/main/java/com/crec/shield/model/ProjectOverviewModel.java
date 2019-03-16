package com.crec.shield.model;

import com.alibaba.fastjson.JSON;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.ProjectOverviewContract;
import com.crec.shield.entity.crec22.project.projectoverview.ProjectOverviewData;
import com.crec.shield.entity.crec22.project.projectoverview.ProjectOverviewResponse;
import com.crec.shield.utils.AppUtils;

import static com.lzy.okgo.OkGo.getContext;

public class ProjectOverviewModel implements ProjectOverviewContract.Model {
    private static ProjectOverviewResponse projectOverviewResponse;
    private static ProjectOverviewData projectOverviewData;


    @Override
    public void getNetData(final Callback callback) {
        String json=AppUtils.getJson("json/crec22/project/management/ProjectOverview/ProjectOverview.json",getContext());
        projectOverviewResponse = JSON.parseObject(json, ProjectOverviewResponse.class);
        projectOverviewData = projectOverviewResponse.getData();
        callback.onSuccess(projectOverviewData);
        callback.onComplete();
    }

}
