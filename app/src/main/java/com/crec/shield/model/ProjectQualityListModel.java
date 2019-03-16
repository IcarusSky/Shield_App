package com.crec.shield.model;

import com.crec.shield.base.Callback;
import com.crec.shield.entity.project.quality.Quality;
import com.crec.shield.entity.project.quality.QualityResponse;
import com.crec.shield.contract.ProjectQualityListContract;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.utils.Base64Util;
import com.crec.shield.utils.SPUtils;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
public class ProjectQualityListModel implements ProjectQualityListContract.Model {
    private static List<Quality> quality = new ArrayList<Quality>();

    @Override
    public void getNetData(final String mToken, final String lineId, final String status, final Callback callback){
//        final String userUtil = Base64Util.encodeData(user);
//        final String passwordUtil = Base64Util.encodeData(password);
        OkGo.post(Url.BASE_URL + Url.PROJECT_QUALITY)
                .params("token", mToken)
                .params("lineId", lineId)
                .params("status", status)
                .execute(new JsonCallback<QualityResponse>() {
                    @Override
                    public void onSuccess(QualityResponse qualityResponse, Call call, Response response) {
                        if (qualityResponse.getCode()== 1) {
                            quality = qualityResponse.getData();

                            callback.onSuccess(quality);
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
