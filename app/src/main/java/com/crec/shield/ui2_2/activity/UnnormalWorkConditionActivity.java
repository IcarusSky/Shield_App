package com.crec.shield.ui2_2.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.ProjectUnnormalDetailAdapter;
import com.crec.shield.adapter.project.ProjectUnnormalHeadAdpter;
import com.crec.shield.contract.UnnormalWorkConditionContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalHeadData;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalHeadCurrentUnnormal;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalHeadReachUnnormal;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalListItemEntity;
import com.crec.shield.global.HtmlConstant;
import com.crec.shield.presenter.UnnormalWorkConditionPresenter;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.utils.WebViewUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.OnClick;

import static com.crec.shield.global.StaticConstant.WITHOUT_DATA;
public class UnnormalWorkConditionActivity extends BaseActivity<UnnormalWorkConditionPresenter> implements UnnormalWorkConditionContract.View {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.risk_list_view)        //  异常头文件
    RecyclerView Risklist;
    @BindView(R.id.recyclerViewTable)   //  异常列表
     RecyclerView recyclerViewTable;
    @BindView(R.id.tableNULL)
    TextView tableNull;
    @BindView(R.id.riskRecycleNULL)
    TextView riskRecycleNull;
    @BindView(R.id.RiskChart)         //异常统计图
    WebView RiskChart;
    @BindView(R.id.btn_left)
    ImageView button;
    @BindView(R.id.back)
    TextView back;

    private ProjectUnnormalDetailAdapter qualityAdapter;
    private String GroundSettingData;
    private String RiskCountChartData;
    private List<UnnormalListItemEntity> riskListData = new ArrayList<>();               //异常列表数据

    private List<UnnormalHeadCurrentUnnormal> riskHeadCurrencyRisks = new ArrayList<>(); // 当前异常的数据
    private List<UnnormalHeadReachUnnormal> riskHeadReachRisks = new ArrayList<>(); // 临近异常的数据
    private ProjectUnnormalHeadAdpter projectRiskHeadAdpter;  //  当前异常和临近异常的适配器
    private Unbinder unbinder;
    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_unnormal_work_condition;
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
        mPresenter.getUnnormalData();
        mPresenter.getUnnormalGanttData();
        mPresenter.getUnnormalListData();
//        initData();  //  初始化地面沉降统计图

    }
    @OnClick(R.id.btn_left)//返回键
    public void onViewClicked() {
        super.onBackPressed();
        finish();
    }
    @OnClick(R.id.back)//返回公司首页按键
    public void onViewClicked1() {
        Intent intent = new Intent();
        intent.setClass(getContext(), CompanyActivity.class);
        startActivity(intent);
    }

    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.16
     * @description 获取当前异常和临近异常
     */
    @Override
    public void showUnnormalData(UnnormalHeadData data) {
      //Recycle view 中获得数据与布局
        riskHeadCurrencyRisks = data.getCurrentunusuals();      //获得当前异常数据
        riskHeadReachRisks = data.getReachunusuals();           //获得临近异常数据
        projectRiskHeadAdpter = new ProjectUnnormalHeadAdpter(getApplicationContext(), riskHeadCurrencyRisks, riskHeadReachRisks);
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
     * @author shenbo
     * @date 2019.2.16
     * @description 获取异常统计数据
     */
    @Override
    public void showUnnormalGanttData(String s) {

        RiskCountChartData = s;            //获得异常甘特图数据
        initRiskCount();
    }
    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.17
     * @description 获取异常列表数据
     */
    @Override
    public void showUnnormalListData(List<UnnormalListItemEntity> data) {

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
     * @author shenbo
     * @date 2019.2.16
     * @description 初始化异常统计数据
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
        RiskChart.loadUrl(HtmlConstant.UNNORMAL_CHART);
        RiskChart.addJavascriptInterface(getContext(), "android");
        RiskChart.setWebContentsDebuggingEnabled(true);
    }
    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.17
     * @description 初始化异常列表
     */
    private void initTable() {
        String risk_content = "";
        String cross_building = "";

        int i = 0;
        for (UnnormalListItemEntity bean : riskListData) {

            if (i == 0) {
                risk_content = bean.getUnusual_content();
                cross_building = bean.getCross_building();
            } else {
                if (risk_content != null && bean.getUnusual_content() != null) {
                    if (risk_content.length() < bean.getUnusual_content().length()) {
                        risk_content = bean.getUnusual_content();
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
        maxDatas.add("Ⅲ级异常");
        maxDatas.add(risk_content);
        maxDatas.add(cross_building);
        maxDatas.add("已解决中");
        qualityAdapter = new ProjectUnnormalDetailAdapter(getApplicationContext(), riskListData, maxDatas);
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
