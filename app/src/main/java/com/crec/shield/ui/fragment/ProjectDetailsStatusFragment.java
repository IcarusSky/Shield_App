package com.crec.shield.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.ProDetailQualityAdapter;
import com.crec.shield.entity.project.status.PointParameterEntity;
import com.crec.shield.entity.project.status.PointParameterResponse;
import com.crec.shield.entity.project.status.ShieldStatusEntity;
import com.crec.shield.entity.project.status.ShieldStatusResponse;
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

public class ProjectDetailsStatusFragment extends Fragment implements View.OnClickListener {


    private WebView DuctPieceHorizontalDeviationChart;    // 导向水平偏差
    private WebView DuctPieceVerticalDeviationChart;      // 导向垂直偏差

    private static final String ACTIVITY_TAG = "ProjectDetailsStatusFragment";
    public static ProjectDetailsStatusFragment fragment = null;

    private SmartRefreshLayout refreshLayout;
    private WebView ShieldStatusChart;  // 飞机图
    private RecyclerView table;             // 点位参数图
    private ProDetailQualityAdapter qualityAdapter;
    private TextView tableNull;

    private View v;

    private String ShieldStatusAngleData;
    private String DPHorizontalResultData;
    private String DPVerticalResultData;

    private ShieldStatusEntity shieldStatusEntity;
    private List<PointParameterEntity> pointParameters;

    public Context context;

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    public static Fragment newInstance() {
        fragment = new ProjectDetailsStatusFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_projectdetails_status, container, false);
        table = (RecyclerView) v.findViewById(R.id.table);
        tableNull = (TextView) v.findViewById(R.id.tableNULL);
        refreshLayout = (SmartRefreshLayout) v.findViewById(R.id.refreshLayout);

        table.setLayoutManager(new LinearLayoutManager(context));

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
                    initData();     // 状态概览
                    init();         //初始化飞机图
                    intTableData(v);//点位参数
                    getDPHorizontalData();
                    getDPVerticalData();
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

    //  点位参数
    private void intTableData(final View v) {
        String mtoken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
        String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();

        if (mtoken != null && lineId != null) {
            OkGo.post(Url.BASE_URL + Url.PROJECT_STATUS_POINTITEM)
                    .params("token", mtoken)
                    .params("lineId", lineId)
                    .execute(new JsonCallback<PointParameterResponse>() {


                        @Override
                        public void onSuccess(PointParameterResponse pointParameterResponse, Call call, Response response) {
                            if (pointParameterResponse.getCode() == 1) {
                                pointParameters = pointParameterResponse.getData();
                                if (pointParameters != null && pointParameters.size() > 0) {
                                    if (pointParameters.get(0).getName() != null) {
                                        initTable();
                                        tableNull.setVisibility(View.GONE);
                                    } else {
                                        tableNull.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    tableNull.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            tableNull.setVisibility(View.VISIBLE);
                            pointParameters = null;
                            if (qualityAdapter != null) {
                                qualityAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
    }

    private void initTable() {
        String point_name = "";
        String point_unit = "";
        int i = 0;
        for (PointParameterEntity bean : pointParameters) {
            if (i == 0) {
                point_name = bean.getName();
                point_unit = bean.getUnit();
            } else {

                if (point_name.length() < bean.getName().length()) {
                    point_name = bean.getName();
                }

                if (point_unit.length() < bean.getUnit().length()) {
                    point_unit = bean.getUnit();
                }
            }
            i++;
        }
        /**
         * 占位字符集合
         */
        List<String> maxDatas = new ArrayList<>();
        maxDatas.add(point_name);   //  name
        maxDatas.add("实时值占位"); //  value
        maxDatas.add(point_unit);   //  unit
        maxDatas.add("不正常");    //  status
        qualityAdapter = new ProDetailQualityAdapter(context, pointParameters, maxDatas);
        table.setAdapter(qualityAdapter);
    }

    /**
     * @company vinelinx
     * @author buji
     * @date 2018.9.3
     * @description 飞机图数据
     */
    @SuppressLint("LongLogTag")

    private void init() {
        Log.v(ProjectDetailsStatusFragment.ACTIVITY_TAG, "Enter init()");

        String mtoken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
        String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
        ShieldStatusChart = (WebView) v.findViewById(R.id.ShieldStatusChart);
        OkGo.post(Url.BASE_URL + Url.PROJECT_STATUS_POINT_PARAMETER)
                .params("token", mtoken)
                .params("lineId", lineId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        refreshLayout.finishRefresh();
                        if (null != s) {
                            ShieldStatusAngleData = s;
                            initPlane();
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
     * @date 2018.9.3
     * @description 初始化飞机图
     */
    private void initPlane() {
        ShieldStatusChart.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!ShieldStatusChart.getSettings().getLoadsImagesAutomatically()) {
                    ShieldStatusChart.getSettings().setLoadsImagesAutomatically(true);
                }
                ShieldStatusChart.loadUrl("javascript:initData(" + ShieldStatusAngleData + ")");
            }
        });
        ShieldStatusChart.setWebChromeClient(new WebChromeClient());
        WebViewUtil.webSetting(ShieldStatusChart.getSettings());
        ShieldStatusChart.loadUrl(HtmlConstant.SHIELD_STATUS_CHART);
        ShieldStatusChart.addJavascriptInterface(new JsInteration(fragment), "android");
        ShieldStatusChart.setWebContentsDebuggingEnabled(true);

    }

    /*
     *状态概览
     */
    @SuppressLint("LongLogTag")
    private void initData() {
        Log.v(ProjectDetailsStatusFragment.ACTIVITY_TAG, "Enter initData()");

        String mtoken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
        String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
        //状态概览
        OkGo.post(Url.BASE_URL + Url.PROJECT_STATUS_REAL)
                .params("token", mtoken)
                .params("lineId", lineId)
                .execute(new JsonCallback<ShieldStatusResponse>() {
                    @Override
                    public void onSuccess(ShieldStatusResponse shieldStatusResponse, Call call, Response response) {
                        refreshLayout.finishRefresh();
                        if (shieldStatusResponse.getCode() == 1) {
                            shieldStatusEntity = shieldStatusResponse.getData();

                            TextView ShieldName = (TextView) v.findViewById(R.id.ShieldName);
                            ShieldName.setText(shieldStatusEntity.getShield_name() == null ? "" : shieldStatusEntity.getShield_name());

                            TextView ShieldStatus = (TextView) v.findViewById(R.id.ShieldStatus);
                            ShieldStatus.setText(shieldStatusEntity.getShield_status() == null ? "" : shieldStatusEntity.getShield_status());

                            TextView ShieldDataStatus = (TextView) v.findViewById(R.id.ShieldDataStatus);
                            ShieldDataStatus.setText(shieldStatusEntity.getShield_data_status() == null ? "" : shieldStatusEntity.getShield_data_status());

                            TextView ShieldDataTime = (TextView) v.findViewById(R.id.ShieldDataTime);
                            ShieldDataTime.setText(shieldStatusEntity.getShield_data_time() == null ? "" : shieldStatusEntity.getShield_data_time());

                            TextView driving_circle_num = (TextView) v.findViewById(R.id.driving_circle_num);
                            driving_circle_num.setText(shieldStatusEntity.getDriving_circle_num() == null ? "" : shieldStatusEntity.getDriving_circle_num());

                            TextView guide_progress = (TextView) v.findViewById(R.id.guide_progress);
                            guide_progress.setText(shieldStatusEntity.getGuide_progress() == null ? "" : shieldStatusEntity.getGuide_progress());

                            TextView horizontal_forward_point = (TextView) v.findViewById(R.id.horizontal_forward_point);
                            horizontal_forward_point.setText(shieldStatusEntity.getHorizontal_forward_point() == null ? "" : shieldStatusEntity.getHorizontal_forward_point() + "mm");

                            TextView vertical_forward_point = (TextView) v.findViewById(R.id.vertical_forward_point);
                            vertical_forward_point.setText(shieldStatusEntity.getVertical_forward_point() == null ? "" : shieldStatusEntity.getVertical_forward_point() + "mm");

                            TextView horizontal_back_point = (TextView) v.findViewById(R.id.horizontal_back_point);
                            horizontal_back_point.setText(shieldStatusEntity.getHorizontal_back_point() == null ? "" : shieldStatusEntity.getHorizontal_back_point() + "mm");

                            TextView vertical_back_point = (TextView) v.findViewById(R.id.vertical_back_point);
                            vertical_back_point.setText(shieldStatusEntity.getVertical_back_point() == null ? "" : shieldStatusEntity.getVertical_back_point() + "mm");

                            TextView horizontal_trend = (TextView) v.findViewById(R.id.horizontal_trend);
                            horizontal_trend.setText(shieldStatusEntity.getHorizontal_trend() == null ? "" : shieldStatusEntity.getHorizontal_trend() + "mm/m");

                            TextView vertical_trend = (TextView) v.findViewById(R.id.vertical_trend);
                            vertical_trend.setText(shieldStatusEntity.getVertical_trend() == null ? "" : shieldStatusEntity.getVertical_trend() + "mm/m");
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
     * @author AnnieYoung
     * @description 管片水平偏差WebView初始化
     * @modifiedBy AnnieYoung
     */
    private void initDuctPieceHorizontalChart() {
        DuctPieceHorizontalDeviationChart = (WebView) v.findViewById(R.id.DuctPieceHorizontal);

        DuctPieceHorizontalDeviationChart.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!DuctPieceHorizontalDeviationChart.getSettings().getLoadsImagesAutomatically()) {
                    DuctPieceHorizontalDeviationChart.getSettings().setLoadsImagesAutomatically(true);
                }
                DuctPieceHorizontalDeviationChart.loadUrl("javascript:callJS(" + DPHorizontalResultData + ")");
            }
        });
        DuctPieceHorizontalDeviationChart.setWebChromeClient(new WebChromeClient());
        WebViewUtil.webSetting(DuctPieceHorizontalDeviationChart.getSettings());
        DuctPieceHorizontalDeviationChart.loadUrl(HtmlConstant.DUCT_PIECE_HORIZONTAL_DEVIATION);
        DuctPieceHorizontalDeviationChart.addJavascriptInterface(new JsInteration(fragment), "android");
        DuctPieceHorizontalDeviationChart.setWebContentsDebuggingEnabled(true);

    }

    /**
     * @company vinelinx
     * @author AnnieYoung
     * @description 管片水平偏差 获取数据
     * @modifiedBy AnnieYoung
     */
    private void getDPHorizontalData() {
        String mtoken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
        String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
        OkGo.post(Url.BASE_URL + Url.PROJECT_STATUS_DUCTPIECE_H)
                .params("token", mtoken)
                .params("lineId", lineId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (null != s) {
                            DPHorizontalResultData = s;
                            initDuctPieceHorizontalChart();
                        }
                    }
                });


    }

    /**
     * @company vinelinx
     * @author AnnieYoung
     * @description 管片垂直偏差WebView初始化
     * @modifiedBy AnnieYoung
     */
    private void initDuctPieceVerticalChart() {

        DuctPieceVerticalDeviationChart = (WebView) v.findViewById(R.id.DuctPieceVertical);

        DuctPieceVerticalDeviationChart.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!DuctPieceVerticalDeviationChart.getSettings().getLoadsImagesAutomatically()) {
                    DuctPieceVerticalDeviationChart.getSettings().setLoadsImagesAutomatically(true);
                }
                DuctPieceVerticalDeviationChart.loadUrl("javascript:callJS(" + DPVerticalResultData + ")");
            }
        });
        DuctPieceVerticalDeviationChart.setWebChromeClient(new WebChromeClient());
        WebViewUtil.webSetting(DuctPieceVerticalDeviationChart.getSettings());
        DuctPieceVerticalDeviationChart.loadUrl(HtmlConstant.DUCT_PIECE_VERTICAL_DEVIATION);
        DuctPieceVerticalDeviationChart.addJavascriptInterface(new JsInteration(fragment), "android");
        DuctPieceVerticalDeviationChart.setWebContentsDebuggingEnabled(true);

    }


    /**
     * @company vinelinx
     * @author AnnieYoung
     * @description 管片垂直偏差 获取数据
     * @modifiedBy AnnieYoung
     */
    private void getDPVerticalData() {
        String mtoken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
        String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
        OkGo.post(Url.BASE_URL + Url.PROJECT_STATUS_DUCTPIECE_V)
                .params("token", mtoken)
                .params("lineId", lineId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (null != s) {
                            DPVerticalResultData = s;
                            initDuctPieceVerticalChart();
                        }
                    }
                });
    }
}
