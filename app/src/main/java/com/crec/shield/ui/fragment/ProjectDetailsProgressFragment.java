package com.crec.shield.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.crec.shield.R;

import com.crec.shield.entity.project.progress.ProgressEntity;
import com.crec.shield.entity.project.progress.ProgressGanTTResponse;
import com.crec.shield.entity.project.progress.ProgressResponse;
import com.crec.shield.entity.project.progress.RingChartEntity;
import com.crec.shield.entity.project.progress.RingChartResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.global.HtmlConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.service.JsInteration;
import com.crec.shield.utils.SPUtils;
import com.crec.shield.utils.WebViewUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ProjectDetailsProgressFragment extends Fragment implements View.OnClickListener {

    private static final String ACTIVITY_TAG = "ProjectDetailsProgressFragment";

    private SmartRefreshLayout refreshLayout;
    public static ProjectDetailsProgressFragment fragment = null;
    private WebView WorkEfficiencyChart;
    private WebView ShieldWorkProgressChart;
    private WebView ShieldGanttChart;

    private String WorkEfficiencyResultData;
    private String ShieldWorkProgressData;  // 当前进度环形图
    private String ShieldGanttData;
    private ProgressEntity progressEntity;

    private List<RingChartEntity> ringChartEntity;

    private View v;
    public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    public static Fragment newInstance() {
        fragment = new ProjectDetailsProgressFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_projectdetails_progress, container, false);
        refreshLayout = (SmartRefreshLayout) v.findViewById(R.id.refreshLayout);

        String keyArg = getActivity().getIntent().getStringExtra("key");
        String keyRiskArg = getActivity().getIntent().getStringExtra("keyRisk");
        /*无后台测试用 请勿删除*/
        if (!(keyArg == null)) {
            Log.d("从MainActivity来的工效排名", keyArg);
        }
        if (!(keyRiskArg == null)) {
            Log.d("从MainActivity来的风险排名", keyRiskArg);
        }
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();  // 环形进度圈
                getWorkEfficiencyChartData();  // 获取白班夜班工效统计数据
            }
        });
        refreshLayout.autoRefresh();

        return v;
    }


    @Override
    public void onClick(View v) {
        Logger.d(v.getId());
        switch (v.getId()) {
        }

    }

    /**
     * @company vinelinx
     * @author wangqi
     * @date 2018.9.5
     * @description 项目--进度：环形进度圈：当前进度比率和环比；盾构机今日完成环数、当前完成环数、总长、预计出洞日期
     */
    @SuppressLint("LongLogTag")
    private void initData() {
        String mtoken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
        String projectId = SPUtils.get(AppConstant.PROJECT.projectId, "").toString();
        String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
        //进度
        OkGo.post(Url.BASE_URL + Url.PROJECT_PROGRESS_TODAYRING)
                .params("token", mtoken)
                .params("projectId", SPUtils.get(AppConstant.PROJECT.projectId, "").toString())
                .params("lineId", SPUtils.get(AppConstant.PROJECT.lineId, "").toString())
                .execute(new JsonCallback<RingChartResponse>() {
                    @Override
                    public void onSuccess(RingChartResponse ringChartResponse, Call call, Response response) {
                        refreshLayout.finishRefresh();
                        if (ringChartResponse.getCode() == 1) {
                            ringChartEntity = ringChartResponse.getData();
                            ShieldWorkProgressData = ringChartEntity.toString();
                            initProgressRatio();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        refreshLayout.finishRefresh();
                    }
                });

        //总体概览
        OkGo.post(Url.BASE_URL + Url.PROJECT_PROGRESS_OVERVIEW)
                .params("token", mtoken)
                .params("projectId", projectId)
                .params("lineId", lineId)
                .execute(new JsonCallback<ProgressResponse>() {
                    @Override
                    public void onSuccess(ProgressResponse progressResponse, Call call, Response response) {
                        if (progressResponse.getCode() == 1) {
                            progressEntity = progressResponse.getData();
                            String Expect_data = progressEntity.getExpect_date() == null ? "" : progressEntity.getExpect_date();
                            TextView ProgressDesc = (TextView) v.findViewById(R.id.ProgressDesc);
                            ProgressDesc.setText(progressEntity.getCurrency_circle_num() + "/" + progressEntity.getTotal_length());

                            TextView currency_circle_num = (TextView) v.findViewById(R.id.currency_circle_num);
                            currency_circle_num.setText("" + progressEntity.getCurrency_circle_num());

                            TextView today_finished = (TextView) v.findViewById(R.id.today_finished);
                            today_finished.setText("" + progressEntity.getToday_finished());

                            TextView expect_date = (TextView) v.findViewById(R.id.expect_date);
                            expect_date.setText(Expect_data);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

        // 盾构机运行状态
        OkGo.post(Url.BASE_URL + Url.PROJECT_PROGRESS_SHIELD_GANTT)
                .params("token", mtoken)
                .params("projectId", projectId)
                .params("lineId", lineId)
                .execute(new JsonCallback<ProgressGanTTResponse>() {
                    @Override
                    public void onSuccess(ProgressGanTTResponse progressGanTTResponse, Call call, Response response) {
                        if (progressGanTTResponse.getCode() == 1) {
                            ShieldGanttData = JSON.toJSONString(progressGanTTResponse.getData());
                            initWorkingStatus();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    /**
     * @company vinelinx
     * @author AnnieYoung
     * @date 2018.9.17
     * @description 初始化白班夜班工效统计
     */
    private void WorkEfficiencyChart() {
        /*白夜班工效统计 初始化 开始*/
        WorkEfficiencyChart = (WebView) v.findViewById(R.id.WorkEfficiencyChart);
        WorkEfficiencyChart.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!WorkEfficiencyChart.getSettings().getLoadsImagesAutomatically()) {
                    WorkEfficiencyChart.getSettings().setLoadsImagesAutomatically(true);
                }
                WorkEfficiencyChart.loadUrl("javascript:callJS(" + WorkEfficiencyResultData + ")");
            }
        });
        WorkEfficiencyChart.setWebChromeClient(new WebChromeClient());
        WebViewUtil.webSetting(WorkEfficiencyChart.getSettings());
        WorkEfficiencyChart.loadUrl(HtmlConstant.WORK_EFFICIENCY_CHART);
        WorkEfficiencyChart.addJavascriptInterface(new JsInteration(fragment), "android");
        WorkEfficiencyChart.setWebContentsDebuggingEnabled(true);
        /*白夜班工效统计 初始化 结束*/
    }

    /**
     * @company vinelinx
     * @author wangqi
     * @date 2018.9.5
     * @description 获取白班夜班工效统计数据
     */
    private void getWorkEfficiencyChartData() {

        String mtoken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
        String projectId = SPUtils.get(AppConstant.PROJECT.projectId, "").toString();
        String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();

        //根据项目id和线路id得到某一条线路的白夜班工效统计图数据
        OkGo.post(Url.BASE_URL + Url.PROJECT_PROGRESS_WORK_EFFICIENCY)
                .params("token", mtoken)
                .params("projectId", projectId)
                .params("lineId", lineId)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        refreshLayout.finishRefresh();
                        WorkEfficiencyResultData = s;
                        WorkEfficiencyChart();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        refreshLayout.finishRefresh();
                    }
                });
    }


    /**
     * @company vinelinx
     * @author AnnieYoung
     * @date 2018.9.17
     * @description 初始化盾构机运行状态
     */
    private void initWorkingStatus() {
        //盾构机运行状态
        ShieldGanttChart = (WebView) v.findViewById(R.id.ShieldGanttChart);
        ShieldGanttChart.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!ShieldGanttChart.getSettings().getLoadsImagesAutomatically()) {
                    ShieldGanttChart.getSettings().setLoadsImagesAutomatically(true);
                }
                ShieldGanttChart.loadUrl("javascript:initData(" + ShieldGanttData + ")");
            }
        });
        ShieldGanttChart.setWebChromeClient(new WebChromeClient());
        WebViewUtil.webSetting(ShieldGanttChart.getSettings());
        ShieldGanttChart.loadUrl(HtmlConstant.SHIELD_GANTT_CHART);
        ShieldGanttChart.addJavascriptInterface(new JsInteration(fragment), "android");
        ShieldGanttChart.setWebContentsDebuggingEnabled(true);
    }

    /**
     * @company vinelinx
     * @author AnnieYoung
     * @date 2018.9.17
     * @description 初始化进度环比图
     */
    @SuppressLint("LongLogTag")
    private void initProgressRatio() {
        ShieldWorkProgressChart = (WebView) v.findViewById(R.id.ShieldWorkProgressChart);
        ShieldWorkProgressChart.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!ShieldWorkProgressChart.getSettings().getLoadsImagesAutomatically()) {
                    ShieldWorkProgressChart.getSettings().setLoadsImagesAutomatically(true);
                }
                ShieldWorkProgressChart.loadUrl("javascript:initData(" + ShieldWorkProgressData + ")");
            }
        });
        ShieldWorkProgressChart.setWebChromeClient(new WebChromeClient());
        WebViewUtil.webSetting(ShieldWorkProgressChart.getSettings());
        ShieldWorkProgressChart.loadUrl(HtmlConstant.SHIELD_WORK_PROGRESS_CHART);
        ShieldWorkProgressChart.addJavascriptInterface(new JsInteration(fragment), "android");
        ShieldWorkProgressChart.setWebContentsDebuggingEnabled(true);

    }

}
