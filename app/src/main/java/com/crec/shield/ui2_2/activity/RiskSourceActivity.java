package com.crec.shield.ui2_2.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.ProDetailQualityAdapter;
import com.crec.shield.adapter.project.ProjectRiskHeadAdpter;
import com.crec.shield.contract.RiskSourceContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.project.risk.RiskHeadCurrentRisks;
import com.crec.shield.entity.project.risk.RiskHeadData;
import com.crec.shield.entity.project.risk.RiskHeadReachRisks;
import com.crec.shield.entity.project.risk.RiskListItemEntity;
import com.crec.shield.global.HtmlConstant;
import com.crec.shield.presenter.RiskSourcePresenter;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.utils.WebViewUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.crec.shield.global.StaticConstant.WITHOUT_DATA;

public class RiskSourceActivity extends BaseActivity<RiskSourcePresenter> implements RiskSourceContract.View {


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.risk_list_view)
    RecyclerView Risklist;
    @BindView(R.id.recyclerViewTable)   //  风险源列表
    RecyclerView recyclerViewTable;
    @BindView(R.id.tableNULL)
    TextView tableNull;
    @BindView(R.id.riskRecycleNULL)
    TextView riskRecycleNull;
    @BindView(R.id.RiskChart)
    WebView RiskChart;
//    @BindView(R.id.GroundSettingChart)
//    WebView GroundSettingChart;

    private ProDetailQualityAdapter qualityAdapter;
    private String GroundSettingData;
    private String RiskCountChartData;
    private List<RiskListItemEntity> riskListData = new ArrayList<>();

    private List<RiskHeadCurrentRisks> riskHeadCurrencyRisks = new ArrayList<>(); // 当前风险点的数据
    private List<RiskHeadReachRisks> riskHeadReachRisks = new ArrayList<>(); // 临近风险点的数据
    private ProjectRiskHeadAdpter projectRiskHeadAdpter;  //  当前风险点和临近风险点的适配器
    private Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.activity_risk_details;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTable.setLayoutManager(layoutManager);

        unbinder = ButterKnife.bind(this);
        mPresenter.getRiskSourceData();
        mPresenter.getRiskGanttData();
        mPresenter.getRiskListData();
//        initData();  //  初始化地面沉降统计图

    }

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }


    /**
     * @company vinelinx
     * @author wangqi
     * @date 2019.1.24
     * @description 初始化当前风险点和临近风险点
     */
    @Override
    public void showRiskSourceData(RiskHeadData riskHeadData) {


        riskHeadCurrencyRisks = riskHeadData.getCurrentRisks();
        riskHeadReachRisks = riskHeadData.getReachRisks();
        projectRiskHeadAdpter = new ProjectRiskHeadAdpter(getApplicationContext(), riskHeadCurrencyRisks, riskHeadReachRisks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //设置布局管理器
        Risklist.setLayoutManager(layoutManager);
        android.support.v7.widget.DividerItemDecoration itemDecoration = new android.support.v7.widget.DividerItemDecoration(getApplicationContext(), android.support.v7.widget.DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider_line_com));
        Risklist.addItemDecoration(itemDecoration);
        //设置Adapter
        Risklist.setAdapter(projectRiskHeadAdpter);
        //设置增加或删除条目的动画
        Risklist.setItemAnimator(new DefaultItemAnimator());
        riskRecycleNull.setVisibility(View.GONE);

    }

    /**
     * @company vinelinx
     * @author wangqi
     * @date 2019.1.24
     * @description 初始化风险源统计数据
     */
    @Override
    public void showRiskGanttData(String s) {

        RiskCountChartData = s;
        initRiskCount();
    }

    /**
     * @company vinelinx
     * @author AnnieYoung
     * @date 2019.1.24
     * @description 获得RiskList SmartTable数据
     */
    @Override
    public void showRiskListData(List<RiskListItemEntity> data) {

        riskListData = data;
        if (data != null && data.size() > 0) {
            if (data.get(0).getStart_num() != null) {
                initTable();
            }
            tableNull.setVisibility(View.GONE);
        } else if (data == null || data.size() == 0) {
            tableNull.setText(WITHOUT_DATA);
            tableNull.setTextSize(15);
            tableNull.setTextColor(getResources().getColor(R.color.color_Aluminum));
            tableNull.setVisibility(View.VISIBLE);
            if (qualityAdapter != null) {
                qualityAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void showWithoutData(String type) {

        // type ( 1： 风险源    2：风险源甘特图      3：风险源列表)
        switch (type){
            case "1":
                riskRecycleNull.setText(WITHOUT_DATA);
                riskRecycleNull.setTextSize(15);
                riskRecycleNull.setTextColor(getResources().getColor(R.color.color_Aluminum));
                riskRecycleNull.setVisibility(View.VISIBLE);
                break;
            case "2":
                break;
            case "3":
                tableNull.setVisibility(View.VISIBLE);
                riskListData = null;
                if (qualityAdapter != null) {

                    qualityAdapter.notifyDataSetChanged();
                }
                break;
        }

    }


    /**
     * @company vinelinx
     * @author buji
     * @date 2018.9.12
     * @description 初始化地面沉降统计图数据
     */
//    private void initData() {
//        String mtoken = (String) SPUtils.get(AppConstant.LOGINSTATUS.token, ""); // 登录的token值
//        String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
//        if(mtoken != null && mtoken.length() != 0 && lineId != null && lineId.length() != 0){
//            OkGo.post(Url.BASE_URL + Url.PROJECT_RISK_GROUND)
//                    .params("token", mtoken)
//                    .params("lineId", lineId)
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onSuccess(String s, Call call, Response response) {
////                        refreshLayout.finishRefresh();
//                            if (null != s) {
//                                GroundSettingData = s;
//                                initGroundSetting();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Call call, Response response, Exception e) {
//                            super.onError(call, response, e);
////                        refreshLayout.finishRefresh();
//                        }
//                    });
//        }
//    }

    /**
     * @company vinelinx
     * @author buji
     * @date 2018.9.12
     * @description 初始化地面沉降统计图
     */
//    @SuppressLint("JavascriptInterface")
//    private void initGroundSetting() {
//
//        GroundSettingChart.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                if (!GroundSettingChart.getSettings().getLoadsImagesAutomatically()) {
//                    GroundSettingChart.getSettings().setLoadsImagesAutomatically(true);
//                }
//
//                GroundSettingChart.loadUrl("javascript:initData(" + GroundSettingData + ")");
//            }
//        });
//        GroundSettingChart.setWebChromeClient(new WebChromeClient());
//        WebViewUtil.webSetting(GroundSettingChart.getSettings());
//        GroundSettingChart.loadUrl(HtmlConstant.GROUND_SETTING_CHART);
//        GroundSettingChart.addJavascriptInterface(getContext(), "android");
//        GroundSettingChart.setWebContentsDebuggingEnabled(true);
//
//    }



    /**
     * @company vinelinx
     * @author wangqi
     * @date 2018.9.12
     * @description 初始化风险源统计
     */
    @SuppressLint("JavascriptInterface")
    private void initRiskCount() {
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
        RiskChart.addJavascriptInterface(getContext(), "android");
        RiskChart.setWebContentsDebuggingEnabled(true);
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
                if (risk_content != null && bean.getRisk_content() != null) {
                    if (risk_content.length() < bean.getRisk_content().length()) {
                        risk_content = bean.getRisk_content();
                    }
                }
                if (cross_building != null && bean.getCross_building() != null) {
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
        maxDatas.add("详情查看");
        maxDatas.add("开始中");
        maxDatas.add("结束中");
        maxDatas.add("Ⅲ级风险源");
        maxDatas.add(risk_content);
        maxDatas.add(cross_building);
        maxDatas.add("已解决中");
        qualityAdapter = new ProDetailQualityAdapter(getApplicationContext(), riskListData, maxDatas);
        recyclerViewTable.setAdapter(qualityAdapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}