package com.crec.shield.model;

import com.alibaba.fastjson.JSON;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.RiskSourceContract;
import com.crec.shield.entity.project.risk.RiskHeadData;
import com.crec.shield.entity.project.risk.RiskHeadDataResponse;
import com.crec.shield.entity.project.risk.RiskListItemEntity;
import com.crec.shield.entity.project.risk.RiskListItemResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.utils.AppUtils;
import com.crec.shield.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import static com.lzy.okgo.OkGo.getContext;


public class RiskSourceModel implements RiskSourceContract.Model{

    private RiskHeadDataResponse riskHeadDataResponse;
    private RiskHeadData riskHeadData;       // 当前风险点和临近风险点的数据
    private List<RiskListItemEntity> riskListData = new ArrayList<>();   //  风险源甘特图的数据
    private RiskListItemResponse riskListItemResponse; // 风险源列表的数据

    String mToken = (String) SPUtils.get(AppConstant.LOGINSTATUS.token, ""); // 登录的token值
//    String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
    String lineId = "8fc7d0e5ed2649188377bd381fdd3c7f";

    // 当前风险源和临近风险源数据
    @Override
    public void getRiskSourceNetData(final Callback callback) {


        String json = AppUtils.getJson("json/crec22/project/management/SafetyManagement/CurrentRisk.json" , getContext());
        riskHeadDataResponse = JSON.parseObject(json, RiskHeadDataResponse.class);
        riskHeadData = riskHeadDataResponse.getData();
        callback.onSuccess(riskHeadData);
        callback.onComplete();

//        if(mToken != null && mToken.length() != 0 && lineId != null && lineId.length() != 0){
//            OkGo.post(Url.BASE_URL + Url.PROJECT_RISK_CURRENCY)
//                    .params("token", mToken)
//                    .params("lineId", lineId)
//                    .execute(new JsonCallback<RiskHeadDataResponse>() {
//                        @Override
//                        public void onSuccess(RiskHeadDataResponse riskHeadDataResponse, Call call, Response response) {
//                            if (riskHeadDataResponse.code == 1) {
//                                if (riskHeadDataResponse.getData().getCurrencyRisks().size() == 0) {
//                                    callback.onFailure("1");
//                                    callback.onComplete();
//                                } else {
//                                    riskHeadData = riskHeadDataResponse.getData();
//                                    callback.onSuccess(riskHeadData);
//                                    callback.onComplete();
//                                }
//                            } else {
//                                callback.onFailure("1");
//                                callback.onComplete();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Call call, Response response, Exception e) {
//                            super.onError(call, response, e);
//                            callback.onError();
//                            callback.onComplete();
//                        }
//                    });
//        }

    }

    @Override
    public void getRiskGanttNetData(final Callback callback) {

        String json = AppUtils.getJson("json/crec22/project/management/SafetyManagement/RiskResGannttData.json", getContext());
        callback.onSuccess(json);
        callback.onComplete();

//        if(mToken != null && mToken.length() != 0 && lineId != null && lineId.length() != 0){
//            OkGo.post(Url.BASE_URL + Url.PROJECT_RISK_GANTT)
//                    .params("token", mToken)
//                    .params("lineId", lineId)
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onSuccess(String s, Call call, Response response) {
//                            if (null != s) {
//                                callback.onSuccess(s);
//                                callback.onComplete();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Call call, Response response, Exception e) {
//                            super.onError(call, response, e);
//                        }
//                    });
//        }

    }

    @Override
    public void getRiskListNetData(final Callback callback) {

        String json = AppUtils.getJson("json/crec22/project/management/SafetyManagement/RiskList.json", getContext());
        riskListItemResponse = JSON.parseObject(json, RiskListItemResponse.class);
        riskListData = riskListItemResponse.getData();
        callback.onSuccess(riskListData);
        callback.onComplete();
//        if(mToken != null && mToken.length() != 0 && lineId != null && lineId.length() != 0){
//            OkGo.post(Url.BASE_URL + Url.PROJECT_RISK_LIST)
//                    .params("token", mToken)
//                    .params("lineId", lineId)
//                    .execute(new JsonCallback<RiskListItemResponse>() {
//                        @Override
//                        public void onSuccess(RiskListItemResponse riskListItemResponse, Call call, Response response) {
//                            if (riskListItemResponse.getCode() == 1) {
//                                riskListData = riskListItemResponse.getData();
//                                callback.onSuccess(riskListData);
//                                callback.onComplete();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Call call, Response response, Exception e) {
//                            super.onError(call, response, e);
//                            callback.onFailure("3");
//                            callback.onComplete();
//                        }
//                    });
//        }

    }
}
