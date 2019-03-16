package com.crec.shield.model;

import com.alibaba.fastjson.JSON;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.ProgressManagementContract;
import com.crec.shield.entity.crec22.project.management.ProjectSystemData;
import com.crec.shield.entity.crec22.project.management.ProjectSystemResponse;
import com.crec.shield.entity.crec22.project.progress.DayAndNightEntity;
import com.crec.shield.entity.crec22.project.progress.DayAndNightResponse;
import com.crec.shield.entity.project.progress.ProgressEntity;
import com.crec.shield.entity.project.progress.ProgressGanTTResponse;
import com.crec.shield.entity.project.progress.ProgressResponse;
import com.crec.shield.entity.project.progress.RingChartEntity;
import com.crec.shield.entity.project.progress.RingChartResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.utils.AppUtils;
import com.crec.shield.utils.SPUtils;
import com.lzy.okgo.OkGo;


import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.OkGo.getContext;

public class ProgressManagementModel implements ProgressManagementContract.Model{


    private List<RingChartEntity> ringChartEntity;
    private String ShieldWorkProgressData;  // 当前进度环形图
    private ProgressEntity progressEntity;
    private DayAndNightResponse dayAndNightResponse;
    private List<DayAndNightEntity> dayAndNightEntity = new ArrayList<DayAndNightEntity>();
    private String ShieldGanttData;
    private static ProjectSystemResponse projectSystemResponse;
    private static ProjectSystemData projectSystemData;
    String mToken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
//    String projectId = SPUtils.get(AppConstant.PROJECT.projectId, "").toString();
//    String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
    String projectId = "9e65a556087141489c3de3dd50c2c170";
    String lineId = "8fc7d0e5ed2649188377bd381fdd3c7f";

    /**
     * @company vinelinx
     * @author WangQi
     * @date 2019.1.25
     * @description 项目--进度：环形进度圈：当前进度比率和环比；
     */
    public void getProgressRatioData(final Callback callback) {
        //进度
        if(mToken != null && mToken.length() != 0 && projectId != null && projectId.length() != 0 && lineId != null && lineId.length() != 0){
            OkGo.post(Url.BASE_URL + Url.PROJECT_PROGRESS_TODAYRING)
                    .params("token", mToken)
                    .params("projectId", projectId)
                    .params("lineId", lineId)
                    .execute(new JsonCallback<RingChartResponse>() {
                        @Override
                        public void onSuccess(RingChartResponse ringChartResponse, Call call, Response response) {
                            if (ringChartResponse.getCode() == 1) {
                                ringChartEntity = ringChartResponse.getData();
                                ShieldWorkProgressData = ringChartEntity.toString();
                                callback.onSuccess(ShieldWorkProgressData);
                                callback.onComplete();
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            callback.onFailure("");
                            callback.onComplete();
                        }
                    });
        }
        else {
            callback.onFailure("Parameter error");
            callback.onComplete();
        }



    }


    /**
     * @company vinelinx
     * @author WangQi
     * @date 2019.1.25
     * @description 项目--进度：盾构机今日完成环数、当前完成环数、总长、预计出洞日期
     */
    @Override
    public void getOverallOverviewData(final Callback callback) {


        String json = AppUtils.getJson("json/crec22/project/management/ProjectSystem.json", getContext());
        projectSystemResponse = JSON.parseObject(json, ProjectSystemResponse.class);
        projectSystemData = projectSystemResponse.getData();
        callback.onSuccess(projectSystemData);
        callback.onComplete();

//        if(mToken != null && mToken.length() != 0 && projectId != null && projectId.length() != 0 && lineId != null && lineId.length() != 0){
//            OkGo.post(Url.BASE_URL + Url.PROJECT_PROGRESS_OVERVIEW)
//                    .params("token", mToken)
//                    .params("projectId", projectId)
//                    .params("lineId", lineId)
//                    .execute(new JsonCallback<ProgressResponse>() {
//                        @Override
//                        public void onSuccess(ProgressResponse progressResponse, Call call, Response response) {
//                            if (progressResponse.getCode() == 1) {
//                                progressEntity = progressResponse.getData();
//                                callback.onSuccess(progressEntity);
//                                callback.onComplete();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Call call, Response response, Exception e) {
//                            super.onError(call, response, e);
//                            callback.onFailure("");
//                            callback.onComplete();
//                        }
//                    });
//        }
//        else {
//            callback.onFailure("Parameter error");
//            callback.onComplete();
//        }
    }


    /**
     * @company vinelinx
     * @author WangQi
     * @date 2019.1.26
     * @description 白班夜班工效统计图
     */
    @Override
    public void getWorkEfficiencyChartData(Callback callback) {
        String json = AppUtils.getJson("json/crec22/project/management/ProgressManagement/WorkEfficiencyAndTotal.json",getContext());
        dayAndNightResponse = JSON.parseObject(json,DayAndNightResponse.class);
        dayAndNightEntity = dayAndNightResponse.getData();
        callback.onSuccess(dayAndNightEntity);
        callback.onComplete();


    }


    /**
     * @company vinelinx
     * @author WangQi
     * @date 2019.1.24
     * @description 获取盾构机运行状态数据
     */
    @Override
    public void getShieldGanttChartData(final Callback callback) {
        if(mToken != null && mToken.length() != 0 && lineId != null && lineId.length() != 0){
            OkGo.post(Url.BASE_URL + Url.PROJECT_PROGRESS_SHIELD_GANTT)
                    .params("token", mToken)
                    .params("projectId", projectId)
                    .params("lineId", lineId)
                    .execute(new JsonCallback<ProgressGanTTResponse>() {

                        @Override
                        public void onSuccess(ProgressGanTTResponse progressGanTTResponse, Call call, Response response) {
                            if (progressGanTTResponse.getCode() == 1) {
                                ShieldGanttData = JSON.toJSONString(progressGanTTResponse.getData());
                                callback.onSuccess(ShieldGanttData);
                                callback.onComplete();
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            callback.onFailure("");
                            callback.onComplete();
                        }
                    });
        }
        else {
            callback.onFailure("Parameter error");
            callback.onComplete();
        }

    }
}
