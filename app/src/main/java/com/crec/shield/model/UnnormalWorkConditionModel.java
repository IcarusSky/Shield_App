package com.crec.shield.model;

import com.alibaba.fastjson.JSON;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.UnnormalWorkConditionContract;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalHeadData;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalHeadDataResponse;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalListItemEntity;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalListItemResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.utils.AppUtils;
import com.crec.shield.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import static com.lzy.okgo.OkGo.getContext;
public class UnnormalWorkConditionModel implements UnnormalWorkConditionContract.Model{
    private UnnormalHeadData unnormalHeadData;    //当前异常与邻近异常
    private UnnormalHeadDataResponse unnormalHeadDataResponse;
    private List<UnnormalListItemEntity> unnormalListItem = new ArrayList<>();  //异常工况列表数据
    private UnnormalListItemResponse unnormalListItemResponse;

    @Override
    public void getUnnormalNetData(final Callback callback) {


        String json = AppUtils.getJson("json/crec22/project/management/UnnormalWorkCondition/CurrencyUnusual.json", getContext());
        unnormalHeadDataResponse = JSON.parseObject(json, UnnormalHeadDataResponse.class);
        unnormalHeadData = unnormalHeadDataResponse.getData();
        callback.onSuccess(unnormalHeadData);
        callback.onComplete();
    }
    @Override
    public void getUnnormalGanttNetData(final Callback callback) {

        String json = AppUtils.getJson("json/crec22/project/management/UnnormalWorkCondition/UnusualResGannttData.json", getContext());
        callback.onSuccess(json);
        callback.onComplete();
    }

    @Override
    public void getUnnormalListNetData(final Callback callback) {


        String json = AppUtils.getJson("json/crec22/project/management/UnnormalWorkCondition/UnusualList.json", getContext());
        unnormalListItemResponse = JSON.parseObject(json, UnnormalListItemResponse.class);
        unnormalListItem = unnormalListItemResponse.getData();
        callback.onSuccess(unnormalListItem);
        callback.onComplete();
    }

}
