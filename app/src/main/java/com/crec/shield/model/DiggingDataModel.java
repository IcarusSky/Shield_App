package com.crec.shield.model;

import com.alibaba.fastjson.JSON;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.DiggingDataContract;
import com.crec.shield.entity.crec22.project.companydiggingdata.PointParameter;
import com.crec.shield.entity.crec22.project.companydiggingdata.PointParameterResponse;
import com.crec.shield.entity.crec22.project.companydiggingdata.EquipmentData;
import com.crec.shield.entity.crec22.project.companydiggingdata.EquipmentDataResponse;
import com.crec.shield.entity.crec22.project.companydiggingdata.ParameterListEntity;
import com.crec.shield.entity.crec22.project.companydiggingdata.ParameterListResponse;
import com.crec.shield.entity.project.quality.QualityResponse;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.utils.AppUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.IstuaryGlobal.getContext;

public class DiggingDataModel implements DiggingDataContract.Model {
    private static EquipmentData equipmentData;
    private static EquipmentDataResponse equipmentDataResponse;
    private static List<PointParameter> pointParameter=new ArrayList<PointParameter>();
    private static PointParameterResponse pointParameterResponse;
    private static List<ParameterListEntity> parameterListEntity=new ArrayList<ParameterListEntity>();
    private static ParameterListResponse parameterListResponse;
//    @Override
//    public void getEquipmentNetData(final Callback callback) {
//
//        String json = AppUtils.getJson("json/crec22/project/management/CompanyTunnelData/RealStatus.json", getContext());
//        equipmentDataResponse = JSON.parseObject(json, EquipmentDataResponse.class);
//        equipmentData = equipmentDataResponse.getData();
//        callback.onSuccess(equipmentData);
//        callback.onComplete();
//
//    }
    @Override
    public void getEquipmentNetData(final String mToken, final String lineId, final Callback callback){
//        final String userUtil = Base64Util.encodeData(user);
//        final String passwordUtil = Base64Util.encodeData(password);
        OkGo.post(Url.BASE_URL + Url.PROJECT_STATUS_REAL)
                .params("token", mToken)
                .params("lineId", lineId)
                .execute(new JsonCallback<EquipmentDataResponse>() {
                    @Override
                    public void onSuccess(EquipmentDataResponse equipmentDataResponse, Call call, Response response) {
                        if (equipmentDataResponse.getCode()== 1) {
                            equipmentData = equipmentDataResponse.getData();

                            callback.onSuccess(equipmentData);
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
//    @Override
//    public void getPointNetData(final Callback callback) {
//
//        String json = AppUtils.getJson("json/crec22/project/management/CompanyTunnelData/PointItem.json", getContext());
//        pointParameterResponse = JSON.parseObject(json, PointParameterResponse.class);
//        pointParameter = pointParameterResponse.getData();
//        callback.onSuccess(pointParameter);
//        callback.onComplete();
//
//    }
    @Override
    public void getPointNetData(final String mToken, final String lineId, final Callback callback){
//        final String userUtil = Base64Util.encodeData(user);
//        final String passwordUtil = Base64Util.encodeData(password);
    OkGo.post(Url.BASE_URL + Url.PROJECT_STATUS_POINTITEM)
            .params("token", mToken)
            .params("lineId", lineId)
            .execute(new JsonCallback<PointParameterResponse>() {
                @Override
                public void onSuccess(PointParameterResponse pointParameterResponse, Call call, Response response) {
                    if (pointParameterResponse.getCode()== 1) {
                        pointParameter = pointParameterResponse.getData();

                        callback.onSuccess(pointParameter);
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
//    @Override
//    public void getPlaneNetData(final Callback callback) {
//
//        String json = AppUtils.getJson("json/crec22/project/management/CompanyTunnelData/GroupPointParameter.json", getContext());
////        parameterListResponse = JSON.parseObject(json, ParameterListResponse.class);
////        parameterListEntity = parameterListResponse.getData();
//        callback.onSuccess(json);
//        callback.onComplete();
//
//    }

    @Override
    public void getPlaneNetData(final String mToken, final String lineId,  final Callback callback){
//        final String userUtil = Base64Util.encodeData(user);
//        final String passwordUtil = Base64Util.encodeData(password);
        OkGo.post(Url.BASE_URL + Url.COMPANY_GROUP_POINT_PARAMETER)
                .params("token", mToken)
                .params("lineId", lineId)
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
