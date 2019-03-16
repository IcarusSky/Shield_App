package com.crec.shield.model;

import com.alibaba.fastjson.JSON;
import com.crec.shield.contract.ManagementDataContract;
import com.crec.shield.base.Callback;
import com.crec.shield.entity.crec22.project.companymanagementdata.ManagementData;
import com.crec.shield.entity.crec22.project.companymanagementdata.ManagementDataResponse;
import com.crec.shield.entity.project.quality.QualityResponse;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.utils.AppUtils;
import com.lzy.okgo.OkGo;

import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.OkGo.getContext;
public class ManagementDataModel implements ManagementDataContract.Model  {
    private static ManagementDataResponse managementDataResponse;
    private static ManagementData managementData;

    @Override
    public void getNetData(final String mToken, final String companyId, final Callback callback){
//        final String userUtil = Base64Util.encodeData(user);
//        final String passwordUtil = Base64Util.encodeData(password);
        OkGo.post(Url.BASE_URL + Url.COMPANY_MANAGER_DATA)
                .params("token", mToken)
                .params("orgId", companyId)
                .execute(new JsonCallback<ManagementDataResponse>() {
                    @Override
                    public void onSuccess(ManagementDataResponse managementDataResponse, Call call, Response response) {
                        if (managementDataResponse.getCode()== 1) {
                            managementData = managementDataResponse.getData();

                            callback.onSuccess(managementData);
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
