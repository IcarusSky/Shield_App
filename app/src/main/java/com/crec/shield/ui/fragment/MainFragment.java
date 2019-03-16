package com.crec.shield.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.adapter.PromptAdapter;
import com.crec.shield.entity.overview.overview.ApproachingPromptEntity;
import com.crec.shield.entity.overview.overview.ApproachingPromptResponse;
import com.crec.shield.entity.overview.overview.RiskPieChartEntity;
import com.crec.shield.entity.overview.overview.RiskPieChartResponse;
import com.crec.shield.entity.overview.overview.RiskRankEntity;
import com.crec.shield.entity.overview.overview.ScheduleChartEntity;
import com.crec.shield.entity.overview.overview.ScheduleChartResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.global.HtmlConstant;
import com.crec.shield.global.StaticConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.ui.activity.CameraActivity;
import com.crec.shield.ui.activity.FollowActivity;
import com.crec.shield.ui.activity.ProjectDetailsActivity;
import com.crec.shield.ui2_2.activity.SettingActivity;
import com.crec.shield.utils.ProgressDialogUtils;
import com.crec.shield.utils.SPUtils;
import com.crec.shield.utils.WebViewUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.APPROACHING_ARRIVAL;
import static com.crec.shield.global.StaticConstant.APPROACHING_ARRIVED;
import static com.crec.shield.global.StaticConstant.APPROACHING_HAVING;
import static com.crec.shield.global.StaticConstant.APPROACHING_START;
import static com.crec.shield.global.StaticConstant.ARRIVAL;
import static com.crec.shield.global.StaticConstant.DAY;
import static com.crec.shield.global.StaticConstant.DISTANCE_ARRIVAL;
import static com.crec.shield.global.StaticConstant.ESTIMMATE;
import static com.crec.shield.global.StaticConstant.REMAINING_RING;
import static com.crec.shield.global.StaticConstant.RING;
import static com.crec.shield.global.StaticConstant.START_DATE;
import static com.crec.shield.global.StaticConstant.TOTAL_RING;
import static com.crec.shield.global.StaticConstant.WITHOUT_DATA;

/**
 * Created by hahaliu on 2018/10/1.
 */

public class MainFragment extends Fragment implements View.OnClickListener {

    private String mtoken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
    private Integer type = (Integer) SPUtils.get(AppConstant.LOGINSTATUS.type, 0);

    @BindView(R.id.iv_left)
    ImageView mBtnLeft;
    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.btn_camera)
    LinearLayout mBtnCamera;
    @BindView(R.id.btn_follow)
    LinearLayout mBtnFollow;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.today_rings)
    TextView today_rings;
    @BindView(R.id.cumulative_rings)
    TextView cumulative_rings;
    @BindView(R.id.total_rings)
    TextView total_rings;
    @BindView(R.id.schedule_chart)
    WebView schedule_chart;
    @BindView(R.id.risk_piechart)
    WebView risk_piechart;
    @BindView(R.id.propel_rank)
    WebView propelRank;
    @BindView(R.id.risk_rank)
    WebView riskRank;
    @BindView(R.id.propel_day)
    Button mbtnPropelDay;
    @BindView(R.id.propel_month)
    Button mbtnPropelMonth;
    @BindView(R.id.firstlevel)
    TextView text_level1;
    @BindView(R.id.secondlevel)
    TextView text_level2;
    @BindView(R.id.thirdlevel)
    TextView text_level3;
    @BindView(R.id.fourthlevel)
    TextView text_level4;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private Handler handler = null;
    private TextView begin_endRecycleNULL;
    private String riskPieChartResultData;
    private String propelRankResultData;
    private String riskRankResultData;
    private String scheduleResultData;
    RiskPieChartEntity riskPieChartData;
    RiskRankEntity riskRankData;
    List<ApproachingPromptEntity> approachingPromptData;
    private List<String>  infoLine, infoLineStatus, infoRemain, infoRing, infoExpect, infoDate, infoArrive, infoDayOrTime, infoDay, infoStartStation, infoEndStation;
    private List<Integer> infoType;
    private PromptAdapter recycleAdapter;
    private String PropelDataType = StaticConstant.PROPEL_RANK_DATA_DAY;
    private Dialog mDialog;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ProgressDialogUtils.closeDialog(mDialog);
                    break;
            }
        }
    };

    private Context context;
    //    @BindView(R.id.today_rings)
    TextView text_today;
    //    @BindView(R.id.cumulative_rings)
    TextView text_cumulative;
    //    @BindView(R.id.total_rings)
    TextView text_total;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        handler = new Handler();
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        begin_endRecycleNULL = (TextView) view.findViewById(R.id.begin_endRecycleNULL);
        text_today = (TextView) view.findViewById(R.id.today_rings);
        text_cumulative = (TextView) view.findViewById(R.id.cumulative_rings);
        text_total = (TextView) view.findViewById(R.id.total_rings);

        if (type == 0 || type == 1 || type == 3) {
            String company_name = SPUtils.get(AppConstant.LOGINSTATUS.company_name, "").toString();
            title.setText(company_name);
        } else {
            String project_name = SPUtils.get(AppConstant.LOGINSTATUS.project_name, "").toString();
            title.setText(project_name);
        }

        mBtnLeft.setOnClickListener(this);
        mBtnCamera.setOnClickListener(this);
        mBtnFollow.setOnClickListener(this);

        // 日排名按钮监听事件
        mbtnPropelDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = ProgressDialogUtils.createLoadingDialog(getActivity(), "加载中...");
                mbtnPropelDay.setTextColor(Color.parseColor("#FFFFFF"));
                mbtnPropelDay.setBackgroundColor(Color.parseColor("#5A7BEF"));
                mbtnPropelMonth.setTextColor(getResources().getColor(R.color.font_black));
                mbtnPropelMonth.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                PropelDataType = StaticConstant.PROPEL_RANK_DATA_DAY;
                getPropelRankData(PropelDataType);
            }
        });
        // 月排名按钮监听事件
        mbtnPropelMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = ProgressDialogUtils.createLoadingDialog(getActivity(), "加载中...");
                PropelDataType = StaticConstant.PROPEL_RANK_DATA_MONTH;
                mbtnPropelMonth.setTextColor(Color.parseColor("#FFFFFF"));
                mbtnPropelMonth.setBackgroundColor(Color.parseColor("#5A7BEF"));
                mbtnPropelDay.setTextColor(getResources().getColor(R.color.font_black));
                mbtnPropelDay.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                getPropelRankData(PropelDataType);
            }
        });

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {

                initScheduleData();       // 进度统计数据
                initRiskPieChartData();   // 风险源统计数据
                getPropelRankData(PropelDataType);    // 掘进工效排名数据获取
                getRiskRankData();       //  风险排名数据获取
                initApproachingArrivalPrompt();        //始发到达提示
                initChart();            // 进度环形图
                initRiskPieChart();     // 风险环形图
            }
        });
        refreshLayout.autoRefresh();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                Intent intent4 = new Intent(getActivity(), SettingActivity.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent4);
                break;

            case R.id.btn_camera:
                Intent intent5 = new Intent(getActivity(), CameraActivity.class);
                intent5.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent5);
                break;

            case R.id.btn_follow:
                Intent intent6 = new Intent(getActivity(), FollowActivity.class);
                intent6.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent6);
                break;

            default:
                break;
        }
    }

    /**
     * @company vinelinx
     * @author AnnieYoung
     * @description 掘进数据获取
     * @modifiedBy AnnieYoung
     */
    private void getPropelRankData(String PropelDataType) {
        if (PropelDataType.equals(StaticConstant.PROPEL_RANK_DATA_DAY)) {
            OkGo.post(Url.BASE_URL + Url.OVERVIEW_PROGRESS_RANK_OF_DAY)
                    .params("token", mtoken)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            mHandler.sendEmptyMessageDelayed(1, 100);
                            propelRankResultData = s;
                            initPropelRank();
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            mHandler.sendEmptyMessageDelayed(1, 100);
                        }
                    });

        } else if (PropelDataType.equals(StaticConstant.PROPEL_RANK_DATA_MONTH)) {

            OkGo.post(Url.BASE_URL + Url.OVERVIEW_PROGRESS_RANK_OF_MONTH)
                    .params("token", mtoken)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            mHandler.sendEmptyMessageDelayed(1, 100);
                            propelRankResultData = s;
                            initPropelRank();
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            mHandler.sendEmptyMessageDelayed(1, 100);
                        }
                    });
        }
    }

    /**
     * @company vinelinx
     * @author AnnieYoung
     * @description 掘进排名初始化Webview
     * @modifiedBy AnnieYoung
     */
    private void initPropelRank() {
        if(propelRank == null){
            return;
        }
        propelRank.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(propelRank == null){
                    return;
                }
                if (!propelRank.getSettings().getLoadsImagesAutomatically()) {
                    propelRank.getSettings().setLoadsImagesAutomatically(true);
                }
                propelRank.loadUrl("javascript:callJS(" + propelRankResultData + ")");
                if (propelRankResultData != null) {
                    Log.d("propelRankResultData:", propelRankResultData);
                } else {
                    Log.d("propelRankResultData:", "propelRankResultData为空！！！");
                }

            }
        });
        propelRank.setWebChromeClient(new WebChromeClient());
        WebViewUtil.webSetting(propelRank.getSettings());
        propelRank.loadUrl(HtmlConstant.PROPEL_RANK);
        propelRank.addJavascriptInterface(new JsInteration(), "android");
        propelRank.setWebContentsDebuggingEnabled(true);
    }

    /**
     * @author AnnieYoung
     * @company vinelinx
     * @description 安卓获取js掘进排名点击后跳转/安卓获取js风险排名点击后跳转
     * @modifiedBy AnnieYoung
     */
    public class JsInteration {

        /**
         * @company vinelinx
         * @author AnnieYoung
         * @description 安卓获取js掘进排名点击后跳转
         * @modifiedBy AnnieYoung
         */
        @JavascriptInterface
        public String testback(String _selectproject) {
            Log.d("选择了", "testback" + _selectproject);
            SPUtils.put(AppConstant.PROJECT.projectId, _selectproject);
            Intent intent = new Intent(getActivity(), ProjectDetailsActivity.class);
            intent.putExtra("key", _selectproject);

            //启动意图
            startActivity(intent);

            return "选择了: testback: " + _selectproject;
        }

        /**
         * @company vinelinx
         * @author AnnieYoung
         * @description 安卓获取js风险排名点击后跳转
         * @modifiedBy AnnieYoung
         */
        @JavascriptInterface
        public String testbackRisk(String _selectprojectRisk) {
            Log.d("选择了", "testbackRisk" + _selectprojectRisk);
            SPUtils.put(AppConstant.PROJECT.projectId, _selectprojectRisk);
            Intent intent = new Intent(getActivity(), ProjectDetailsActivity.class);
            intent.putExtra("keyRisk", _selectprojectRisk);
            //启动意图
            startActivity(intent);

            return "选择了: testbackRisk: " + _selectprojectRisk;
        }
    }


    /**
     * @company vinelinx
     * @author wangqi
     * @description 进度统计数据
     * @modifiedBy AnnieYoung
     */
    private void initScheduleData() {
        OkGo.post(Url.BASE_URL + Url.OVERVIEW_PROGRESS)
                .params("token", mtoken)
                .execute(new JsonCallback<ScheduleChartResponse>() {
                    @Override
                    public void onSuccess(ScheduleChartResponse scheduleChartResponse, Call call, Response response) {
                        if (scheduleChartResponse.getCode() == 1) {
                            ScheduleChartEntity scheduleData = scheduleChartResponse.getData();

                            text_today.setText(scheduleData.getTodayRings() + "环");
                            text_cumulative.setText(scheduleData.getCumulativeRings() + "环");
                            text_total.setText(scheduleData.getTotalRings() + "环");
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
     * @author wangqi
     * @description 风险源统计数据
     * @modifiedBy AnnieYoung
     */
    private void initRiskPieChartData() {
        OkGo.post(Url.BASE_URL + Url.OVERVIEW_FUTURE_AWEEK_RISKSTATISTICS)
                .params("token", mtoken)
                .execute(new JsonCallback<RiskPieChartResponse>() {
                    @Override
                    public void onSuccess(RiskPieChartResponse riskPieChartResponse, Call call, Response response) {
                        if (riskPieChartResponse.getCode().equals("1")) {
                            riskPieChartData = riskPieChartResponse.getData();
                            text_level1.setText(riskPieChartData.getFirstLevel());
                            text_level2.setText(riskPieChartData.getSecondLevel());
                            text_level3.setText(riskPieChartData.getThirdLevel());
                            text_level4.setText(riskPieChartData.getFourthLevel());
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
     * @description 风险数据获取
     * @modifiedBy AnnieYoung
     */
    private RiskRankEntity getRiskRankData() {

        OkGo.post(Url.BASE_URL + Url.OVERVIEW_FUTURE_AWEEK_RISKRANKING)
                .params("token", mtoken)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        riskRankResultData = s;
                        initRiskRank();         //风险排名
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
        return riskRankData;
    }

    /*
    风险排名初始化Webview
    */
    private void initRiskRank() {
        riskRank.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(riskRank == null){
                    return;
                }
                if (!riskRank.getSettings().getLoadsImagesAutomatically()) {
                    riskRank.getSettings().setLoadsImagesAutomatically(true);
                }
                riskRank.loadUrl("javascript:callJS(" + riskRankResultData + ")");
                Log.d("riskRankResultData:", "OVERVIEW_FUTURE_AWEEK_RISKRANKING_Error");
            }
        });
        riskRank.setWebChromeClient(new WebChromeClient());
        WebViewUtil.webSetting(riskRank.getSettings());
        riskRank.loadUrl(HtmlConstant.RISK_RANK);
        riskRank.addJavascriptInterface(new JsInteration(), "android");
        riskRank.setWebContentsDebuggingEnabled(true);
    }

    /**
     * @company vinelinx
     * @author wangqi
     * @description 始发到达数据
     * @modifiedBy AnnieYoung
     */
    private void initApproachingArrivalPrompt() {
        infoType = new ArrayList<>();
        infoLine = new ArrayList<>();
        infoLineStatus = new ArrayList<>();
        infoRemain = new ArrayList<>();
        infoRing = new ArrayList<>();
        infoExpect = new ArrayList<>();
        infoDate = new ArrayList<>();
        infoArrive = new ArrayList<>();
        infoDayOrTime = new ArrayList<>();
        infoDay = new ArrayList<>();
        infoStartStation = new ArrayList<>();
        infoEndStation = new ArrayList<>();
        if (mtoken != null) {
            OkGo.post(Url.BASE_URL + Url.OVERVIEW_START_ARRIVALS)
                    .params("token", mtoken)
                    .execute(new JsonCallback<ApproachingPromptResponse>() {
                        @Override
                        public void onSuccess(ApproachingPromptResponse approachingPromptResponse, Call call, Response response) {
                            if(refreshLayout == null){
                                return;
                            }
                            refreshLayout.finishRefresh();
                            if(approachingPromptResponse == null){
                                return;
                            }
                            if (approachingPromptResponse.getCode() == 1) {
                                approachingPromptData = approachingPromptResponse.getData();
                                if (approachingPromptData.size() == 0) {
                                    begin_endRecycleNULL.setText(WITHOUT_DATA);
                                    begin_endRecycleNULL.setTextSize(15);
                                    begin_endRecycleNULL.setTextColor(getResources().getColor(R.color.color_Aluminum));
                                    begin_endRecycleNULL.setVisibility(View.VISIBLE);
                                } else {
                                    for (ApproachingPromptEntity i : approachingPromptData) {
                                        if (i.getRemainDays() == null) {
                                            i.setRemainDays("0");
                                        }
                                        if (i.getLineName() == null) {
                                            i.setLineName("");
                                        }
                                        if (i.getTotalRings() == null) {
                                            i.setTotalRings("0");
                                        }
                                        if (i.getRemainRings() == null) {
                                            i.setRemainRings("0");
                                        }
                                        if (i.getStartDate() == null) {
                                            i.setStartDate("");
                                        }
                                        if (i.getEndDate() == null) {
                                            i.setEndDate("");
                                        }
                                        if (i.getSectionStart() == null) {
                                            i.setSectionStart("");
                                        }
                                        if (i.getSectionEnd() == null) {
                                            i.setSectionEnd("");
                                        }
                                        //  type为0是始发提示，type为1是即将到达，type为2是已经到达
                                        if (i.getType() == 0) {
                                            infoType.add(i.getType()); //APPROACHING_START_PROMPT
                                            infoLine.add(i.getLineName());
                                            infoLineStatus.add(APPROACHING_START);
                                            infoRemain.add(TOTAL_RING);
                                            infoRing.add(i.getTotalRings() + RING);
                                            infoExpect.add("");
                                            infoDate.add("");
                                            infoArrive.add("");
                                            infoDayOrTime.add(START_DATE);
                                            infoDay.add(i.getStartDate());
                                            infoStartStation.add(i.getSectionStart());
                                            infoEndStation.add(i.getSectionEnd());
                                        } else if (i.getType() == 1) {
                                            infoType.add(i.getType()); // APPROACHING_ARRIVAL_PROMPT
                                            infoLine.add(i.getLineName());
                                            infoLineStatus.add(APPROACHING_ARRIVAL);
                                            infoRemain.add(REMAINING_RING);
                                            infoRing.add(i.getRemainRings() + RING);
                                            infoExpect.add(ESTIMMATE);
                                            infoDate.add(i.getEndDate());
                                            infoArrive.add(ARRIVAL);
                                            infoDayOrTime.add(DISTANCE_ARRIVAL);
                                            infoDay.add(i.getRemainDays() + DAY);
                                            infoStartStation.add(i.getSectionStart());
                                            infoEndStation.add(i.getSectionEnd());
                                        }else if(i.getType() == 2){
                                            infoType.add(i.getType()); // APPROACHING_ARRIVAL_PROMPT
                                            infoLine.add(i.getLineName());
                                            infoLineStatus.add("");
                                            infoRemain.add(TOTAL_RING);
                                            infoRing.add(i.getTotalRings() + RING);
                                            infoExpect.add("");
                                            infoDate.add("");
                                            infoArrive.add("");
                                            infoDayOrTime.add(APPROACHING_HAVING+i.getEndDate());
                                            infoDay.add(APPROACHING_ARRIVED);
                                            infoStartStation.add(i.getSectionStart());
                                            infoEndStation.add(i.getSectionEnd());
                                        }
                                    }
                                    recycleAdapter = new PromptAdapter(context, infoType, infoLine, infoLineStatus, infoRemain, infoRing, infoExpect, infoDate, infoArrive, infoDayOrTime, infoDay, infoStartStation, infoEndStation);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                                    //设置布局管理器
                                    recyclerView.setLayoutManager(layoutManager);
                                    DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
                                    itemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider_line_com));
                                    recyclerView.addItemDecoration(itemDecoration);
                                    //设置Adapter
                                    recyclerView.setAdapter(recycleAdapter);
                                    begin_endRecycleNULL.setVisibility(View.GONE);
                                }
                            } else {
                                begin_endRecycleNULL.setText(WITHOUT_DATA);
                                begin_endRecycleNULL.setTextSize(15);
                                begin_endRecycleNULL.setTextColor(getResources().getColor(R.color.color_Aluminum));
                                begin_endRecycleNULL.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            refreshLayout.finishRefresh();
                        }
                    });
        }
    }

    /**
     * @company vinelinx
     * @author wangqi
     * @description 进度统计圈，显示此时公司的进度百分比
     * @modifiedBy AnnieYoung
     */
    private void initChart() {
        schedule_chart.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(schedule_chart == null){
                    return;
                }
                if (!schedule_chart.getSettings().getLoadsImagesAutomatically()) {
                    schedule_chart.getSettings().setLoadsImagesAutomatically(true);
                }

                OkGo.post(Url.BASE_URL + Url.OVERVIEW_PROGRESS)
                        .params("token", mtoken)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                scheduleResultData = s;
                                if(schedule_chart == null){
                                    return;
                                }
                                schedule_chart.loadUrl("javascript:CallJS(" + scheduleResultData + ")");
                            }
                        });
            }
        });
        schedule_chart.setWebChromeClient(new WebChromeClient());
        WebSettings settings_shieldprogress = schedule_chart.getSettings();
        settings_shieldprogress.setJavaScriptEnabled(true);
        settings_shieldprogress.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings_shieldprogress.setDomStorageEnabled(true);
        settings_shieldprogress.setAppCacheMaxSize(8 * 1024 * 1024);
        settings_shieldprogress.setAllowFileAccess(true);
        settings_shieldprogress.setAppCacheEnabled(true);
        settings_shieldprogress.setDatabaseEnabled(true);
        settings_shieldprogress.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings_shieldprogress.setAllowUniversalAccessFromFileURLs(true);
        schedule_chart.loadUrl(HtmlConstant.SCHEDULE_CHART);
        schedule_chart.addJavascriptInterface(new JsInteration(), "android");
        schedule_chart.setWebContentsDebuggingEnabled(true);
    }

    /**
     * @company vinelinx
     * @author wangqi
     * @description 风险统计圈，显示此时公司的各个风险的百分比
     * @modifiedBy AnnieYoung
     */
    private void initRiskPieChart() {
        risk_piechart.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(risk_piechart == null){
                    return;
                }
                if (!risk_piechart.getSettings().getLoadsImagesAutomatically()) {
                    risk_piechart.getSettings().setLoadsImagesAutomatically(true);
                }

                OkGo.post(Url.BASE_URL + Url.OVERVIEW_FUTURE_AWEEK_RISKSTATISTICS)
                        .params("token", mtoken)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                riskPieChartResultData = s;
                                if(risk_piechart == null){
                                    return;
                                }
                                risk_piechart.loadUrl("javascript:CallJS(" + riskPieChartResultData + ")");
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                            }
                        });
            }
        });
        risk_piechart.setWebChromeClient(new WebChromeClient());
        WebSettings settings_shieldprogress = risk_piechart.getSettings();
        settings_shieldprogress.setJavaScriptEnabled(true);
        settings_shieldprogress.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings_shieldprogress.setDomStorageEnabled(true);
        settings_shieldprogress.setAppCacheMaxSize(8 * 1024 * 1024);
        settings_shieldprogress.setAllowFileAccess(true);
        settings_shieldprogress.setAppCacheEnabled(true);
        settings_shieldprogress.setDatabaseEnabled(true);
        settings_shieldprogress.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings_shieldprogress.setAllowUniversalAccessFromFileURLs(true);
        risk_piechart.loadUrl(HtmlConstant.RISK_PIE_CHART);
        risk_piechart.addJavascriptInterface(new JsInteration(), "android");
        risk_piechart.setWebContentsDebuggingEnabled(true);
    }
}
