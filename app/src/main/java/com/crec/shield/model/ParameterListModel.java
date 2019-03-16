package com.crec.shield.model;
import com.alibaba.fastjson.JSON;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.ParameterListContract;
import com.crec.shield.entity.crec22.project.parameterlist.ParameterListEntity;
import com.crec.shield.entity.crec22.project.parameterlist.ParameterListResponse;
import com.crec.shield.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import static com.lzy.okgo.OkGo.getContext;

public class ParameterListModel implements ParameterListContract.Model{
    private static ParameterListResponse parameterListResponse;
    private static List<ParameterListEntity> parameterListEntity=new ArrayList<>();
    @Override
    public void getParameterListNetData(final Callback callback) {
        String json=AppUtils.getJson("json/crec22/project/management/ParameterList/GroupPointParameter.json",getContext());
        parameterListResponse = JSON.parseObject(json, ParameterListResponse.class);
        parameterListEntity =  parameterListResponse.getData();
        callback.onSuccess(parameterListEntity);
        callback.onComplete();
    }
}
