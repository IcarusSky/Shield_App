package com.crec.shield.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.ProDetailQualityAdapter;
import com.crec.shield.adapter.project.ProjectRiskHeadAdpter;
import com.crec.shield.entity.project.risk.RiskHeadCurrentRisks;
import com.crec.shield.entity.project.risk.RiskHeadData;
import com.crec.shield.entity.project.risk.RiskHeadDataResponse;
import com.crec.shield.entity.project.risk.RiskHeadReachRisks;
import com.crec.shield.entity.project.risk.RiskListItemEntity;
import com.crec.shield.entity.project.risk.RiskListItemResponse;
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

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.WITHOUT_DATA;


public class ProjectDetailsRiskFragment extends Fragment implements View.OnClickListener {

    public static ProjectDetailsRiskFragment fragment = null;

    private SmartRefreshLayout refreshLayout;
    private WebView RiskChart;
    private WebView GroundSettingChart;
    private RecyclerView Risklist;
    private RecyclerView recyclerViewTable;
    private ProDetailQualityAdapter qualityAdapter;

    private String GroundSettingData;
    private String RiskCountChartData;
    private List<RiskListItemEntity> riskListData = new ArrayList<>();
    private View v;
    private TextView tableNull;
    private TextView riskRecycleNull;

    private RiskHeadData riskHeadData; // 当前风险点和临近风险点的数据data
    private List<RiskHeadCurrentRisks> riskHeadCurrencyRisks = new ArrayList<>(); // 当前风险点的数据
    private List<RiskHeadReachRisks> riskHeadReachRisks = new ArrayList<>(); // 临近风险点的数据
    private ProjectRiskHeadAdpter projectRiskHeadAdpter;  //  当前风险点和临近风险点的适配器

    public Context context;

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;

    public static Fragment newInstance() {
        fragment = new ProjectDetailsRiskFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_projectdetails_risk, container, false);

        refreshLayout = (SmartRefreshLayout) v.findViewById(R.id.refreshLayout);
        Risklist = (RecyclerView) v.findViewById(R.id.risk_list_view);
        recyclerViewTable = (RecyclerView) v.findViewById(R.id.recyclerViewTable);
        tableNull = (TextView) v.findViewById(R.id.tableNULL);
        riskRecycleNull = (TextView) v.findViewById(R.id.riskRecycleNULL);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTable.setLayoutManager(layoutManager);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            refreshLayout.setEnableLoadMore(false);
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    initData();  //  初始化地面沉降统计图
                    initRiskHead();
                    intTableData(v);
                    initRiskCountChartData();
                }
            });
            refreshLayout.autoRefresh();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
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
     * @date 2018.9.12
     * @description 初始化当前风险点和临近风险点
     */
    private void initRiskHead() {
        String mToken = (String) SPUtils.get(AppConstant.LOGINSTATUS.token, ""); // 登录的token值
        String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
        OkGo.post(Url.BASE_URL + Url.PROJECT_RISK_CURRENCY)
                .params("token", mToken)
                .params("lineId", lineId)
                .execute(new JsonCallback<RiskHeadDataResponse>() {
                    @Override
                    public void onSuccess(RiskHeadDataResponse riskHeadDataResponse, Call call, Response response) {
                        refreshLayout.finishRefresh();
                        if (riskHeadDataResponse.code == 1) {
                            if (riskHeadDataResponse.getData().getCurrentRisks().size() == 0) {
                                riskRecycleNull.setText(WITHOUT_DATA);
                                riskRecycleNull.setTextSize(15);
                                if (isAdded()) {
                                    riskRecycleNull.setTextColor(getResources().getColor(R.color.color_Aluminum));
                                }
                                riskRecycleNull.setVisibility(View.VISIBLE);
                            } else {
                                riskHeadData = riskHeadDataResponse.getData();
                                riskHeadCurrencyRisks = riskHeadData.getCurrentRisks();
                                riskHeadReachRisks = riskHeadData.getReachRisks();
                                projectRiskHeadAdpter = new ProjectRiskHeadAdpter(context, riskHeadCurrencyRisks, riskHeadReachRisks);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                                //设置布局管理器
                                Risklist.setLayoutManager(layoutManager);
                                android.support.v7.widget.DividerItemDecoration itemDecoration = new android.support.v7.widget.DividerItemDecoration(context, android.support.v7.widget.DividerItemDecoration.VERTICAL);
                                itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider_line_com));
                                Risklist.addItemDecoration(itemDecoration);
                                //设置Adapter
                                Risklist.setAdapter(projectRiskHeadAdpter);
                                //设置增加或删除条目的动画
                                Risklist.setItemAnimator(new DefaultItemAnimator());
                                riskRecycleNull.setVisibility(View.GONE);
                            }
                        } else {
                            riskRecycleNull.setText(WITHOUT_DATA);
                            riskRecycleNull.setTextSize(15);
                            if (isAdded()) {
                                riskRecycleNull.setTextColor(getResources().getColor(R.color.color_Aluminum));
                            }
                            riskRecycleNull.setVisibility(View.VISIBLE);
                        }
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
     * @author buji
     * @date 2018.9.12
     * @description 初始化地面沉降统计图数据
     */
    private void initData() {
        String mtoken = (String) SPUtils.get(AppConstant.LOGINSTATUS.token, ""); // 登录的token值
        String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
        OkGo.post(Url.BASE_URL + Url.PROJECT_RISK_GROUND)
                .params("token", mtoken)
                .params("lineId", lineId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        refreshLayout.finishRefresh();
                        if (null != s) {
                            GroundSettingData = s;
                            initGroundSetting();
                        }
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
     * @author buji
     * @date 2018.9.12
     * @description 初始化地面沉降统计图
     */
    private void initGroundSetting() {

        GroundSettingChart = (WebView) v.findViewById(R.id.GroundSettingChart);
        GroundSettingChart.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!GroundSettingChart.getSettings().getLoadsImagesAutomatically()) {
                    GroundSettingChart.getSettings().setLoadsImagesAutomatically(true);
                }

                GroundSettingChart.loadUrl("javascript:initData(" + GroundSettingData + ")");
            }
        });
        GroundSettingChart.setWebChromeClient(new WebChromeClient());
        WebViewUtil.webSetting(GroundSettingChart.getSettings());
        GroundSettingChart.loadUrl(HtmlConstant.GROUND_SETTING_CHART);
        GroundSettingChart.addJavascriptInterface(new JsInteration(fragment), "android");
        GroundSettingChart.setWebContentsDebuggingEnabled(true);

    }

    /**
     * @company vinelinx
     * @author wangqi
     * @date 2018.9.12
     * @description 初始化风险源统计数据
     */
    private void initRiskCountChartData() {
        String mtoken = (String) SPUtils.get(AppConstant.LOGINSTATUS.token, ""); // 登录的token值
        String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
        OkGo.post(Url.BASE_URL + Url.PROJECT_RISK_GANTT)
                .params("token", mtoken)
                .params("lineId", lineId)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        refreshLayout.finishRefresh();
                        if (null != s) {
                            RiskCountChartData = s;
                            initRiskCount();
                        }
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
     * @author wangqi
     * @date 2018.9.12
     * @description 初始化风险源统计
     */
    private void initRiskCount() {
        RiskChart = (WebView) v.findViewById(R.id.RiskChart);
        RiskChart.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!RiskChart.getSettings().getLoadsImagesAutomatically()) {
                    RiskChart.getSettings().setLoadsImagesAutomatically(true);
                }

                RiskChart.loadUrl("javascript:initData(" + RiskCountChartData + ")");
            }
        });
        RiskChart.setWebChromeClient(new WebChromeClient());
        WebViewUtil.webSetting(RiskChart.getSettings());
        RiskChart.loadUrl(HtmlConstant.RISK_CHART);
        RiskChart.addJavascriptInterface(new JsInteration(fragment), "android");
        RiskChart.setWebContentsDebuggingEnabled(true);
    }

    /**
     * @company vinelinx
     * @author AnnieYoung
     * @date 2018.9.14
     * @description 获得RiskList SmartTable数据
     */
    private void intTableData(View v) {
        String mtoken = (String) SPUtils.get(AppConstant.LOGINSTATUS.token, ""); // 登录的token值
        String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
        OkGo.post(Url.BASE_URL + Url.PROJECT_RISK_LIST)
                .params("token", mtoken)
                .params("lineId", lineId)
                .execute(new JsonCallback<RiskListItemResponse>() {
                    @Override
                    public void onSuccess(RiskListItemResponse riskListItemResponse, Call call, Response response) {
                        refreshLayout.finishRefresh();
                        if (riskListItemResponse.getCode() == 1) {
                            riskListData = riskListItemResponse.getData();
                            if (riskListData != null && riskListData.size() > 0) {
                                if (riskListData.get(0).getStart_num() != null) {
                                    initTable();
                                }
                                tableNull.setVisibility(View.GONE);
                            } else if (riskListData == null || riskListData.size() == 0) {
                                tableNull.setText(WITHOUT_DATA);
                                tableNull.setTextSize(15);
                                if (isAdded()) {
                                    tableNull.setTextColor(getResources().getColor(R.color.color_Aluminum));
                                }
                                tableNull.setVisibility(View.VISIBLE);
                                if (qualityAdapter != null) {
                                    qualityAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        refreshLayout.finishRefresh();
                        tableNull.setVisibility(View.VISIBLE);
                        riskListData = null;
                        if (qualityAdapter != null) {

                            qualityAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * @company vinelinx
     * @author AnnieYoung
     * @date 2018.9.14
     * @description 初始化RiskList SmartTable
     */
    private void initTable() {
        String risk_content = "";
        String cross_building = "";

        int i = 0;
        for (RiskListItemEntity bean : riskListData) {

            if (i == 0) {
                risk_content = bean.getRisk_content();
                cross_building = bean.getCross_building();
            } else {
                if(risk_content != null && bean.getRisk_content() != null){
                    if (risk_content.length() < bean.getRisk_content().length()) {
                        risk_content = bean.getRisk_content();
                    }
                }
                if(cross_building != null && bean.getCross_building() != null){
                    if (cross_building.length() < bean.getCross_building().length()) {
                        cross_building = bean.getCross_building();
                    }
                }
            }
            i++;
        }
        /**
         * 占位字符集合
         */
        List<String> maxDatas = new ArrayList<>();
        maxDatas.add("开始中");
        maxDatas.add("结束中");
        maxDatas.add("Ⅲ级风险源");
        maxDatas.add(risk_content);
        maxDatas.add(cross_building);
        maxDatas.add("已解决中");
        qualityAdapter = new ProDetailQualityAdapter(context, riskListData, maxDatas);
        recyclerViewTable.setAdapter(qualityAdapter);
    }
}