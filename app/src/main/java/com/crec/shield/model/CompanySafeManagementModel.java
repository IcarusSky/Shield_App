package com.crec.shield.model;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.CompanySafeManagementContract;
import com.crec.shield.entity.project.quality.QualityResponse;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.utils.AppUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.IstuaryGlobal.getContext;

public class CompanySafeManagementModel implements CompanySafeManagementContract.Model {
//    @Override
//    public void getRankNetData(final Callback callback) {
//
//        String json = AppUtils.getJson("json/crec22/project/management/CompanySafeManagement/RiskFuture.json", getContext());
////        parameterListResponse = JSON.parseObject(json, ParameterListResponse.class);
////        parameterListEntity = parameterListResponse.getData();
//        callback.onSuccess(json);
//        callback.onComplete();
//
//    }
    @Override
    public void getRankNetData(final String mToken, final String orgId,  final Callback callback){
//        final String userUtil = Base64Util.encodeData(user);
//        final String passwordUtil = Base64Util.encodeData(password);
        OkGo.post(Url.BASE_URL + Url.OVERVIEW_FUTURE_AWEEK_RISKRANKING)
                .params("token", mToken)
                .params("orgId", orgId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (null != s) {

                            callback.onSuccess(s);
                            callback.onComplete();
                        }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        callback.onFailure("请求失败：参数有误");
                        callback.onComplete();
                    }
                });
    }
}
