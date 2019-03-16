package com.crec.shield.model;

import com.alibaba.fastjson.JSON;
import com.crec.shield.contract.ProjectManagementContract;
import com.crec.shield.base.Callback;
import com.crec.shield.entity.crec22.project.management.ProjectSystemData;
import com.crec.shield.entity.crec22.project.management.ProjectSystemResponse;
import com.crec.shield.utils.AppUtils;

import static com.lzy.okgo.OkGo.getContext;

public class ProjectManagementModel implements ProjectManagementContract.Model {

    private static ProjectSystemResponse projectSystemResponse;
    private static ProjectSystemData projectSystemData;

    @Override
    public void getNetData(final Callback callback) {

        String json = AppUtils.getJson("json/crec22/project/management/ProjectSystem.json", getContext());
        projectSystemResponse = JSON.parseObject(json, ProjectSystemResponse.class);
        projectSystemData = projectSystemResponse.getData();
        callback.onSuccess(projectSystemData);
        callback.onComplete();

    }
}
