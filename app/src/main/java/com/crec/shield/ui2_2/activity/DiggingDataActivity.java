package com.crec.shield.ui2_2.activity;

import com.crec.shield.R;
import com.crec.shield.adapter.ProDetailQualityAdapter;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.contract.DiggingDataContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.crec22.project.companydiggingdata.EquipmentData;
import com.crec.shield.entity.crec22.project.companydiggingdata.ParameterListEntity;
import com.crec.shield.entity.crec22.project.companydiggingdata.PointParameter;
import com.crec.shield.presenter.DiggingDataPresenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import okhttp3.Call;
import okhttp3.Response;
import static com.crec.shield.global.StaticConstant.WITHOUT_DATA;

public class DiggingDataActivity extends BaseActivity<DiggingDataPresenter> implements DiggingDataContract.View{
    //设备信息
    @BindView(R.id.tbmphoto)
    ImageView tbmphoto;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tbmname)
    TextView tbmname;
    @BindView(R.id.presentrings)
    TextView presentrings;
    @BindView(R.id.schedule)
    TextView schedule;
    @BindView(R.id.tbmstatus)
    TextView tbmstatus;
    @BindView(R.id.tbmstatusbig)
    TextView tbmstatusbig;
    @BindView(R.id.time)
    TextView time;
    //点位参数
    @BindView(R.id.table)
    RecyclerView table;
    @BindView(R.id.tableNULL)
    TextView tableNULL;
    //飞机图显示
    @BindView(R.id.ShieldStatusChart)
    WebView ShieldStatusChart;
    //当前姿态
    @BindView(R.id.horizontal_forward_point)
    TextView horizontalforward;
    @BindView(R.id.horizontal_back_point)
    TextView horizontalback;
    @BindView(R.id.horizontal_trend)
    TextView horizontaltrend;
    @BindView(R.id.vertical_forward_point)
    TextView verticalforward;
    @BindView(R.id.vertical_back_point)
    TextView verticalback;
    @BindView(R.id.vertical_trend)
    TextView verticaltrend;
    //跳转按键
    @BindView(R.id.equipment_management)
    RelativeLayout equipmentmanagement;
    @BindView(R.id.parameter_list)
    RelativeLayout parameterlist;
    @BindView(R.id.search)
    RelativeLayout search;
    @BindView(R.id.btn_left)
    ImageView back;

    String mToken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
    String lineId = SPUtils.get(AppConstant.PROJECT.lineId, "").toString();
    private String ShieldStatusAngleData ;
    private List<PointParameter> pointParameters = new ArrayList<>();
    private List<PointParameter> riskListData = new ArrayList<>();               //异常列表数据
    private ProDetailQualityAdapter qualityAdapter;
    private Unbinder unbinder;
    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_company_diggingdata;
    }


    @OnClick(R.id.equipment_management)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setClass(getContext(), EquipmentManagementActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.search)
    public void onViewClicked1() {
        Intent intent = new Intent();
        intent.setClass(getContext(), ProjectListForCityActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.parameter_list)
    public void onViewClicked2() {
        Intent intent = new Intent();
        intent.setClass(getContext(), ParameterListActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.btn_left)//返回键
    public void onViewClicked3() {
        super.onBackPressed();
        finish();
    }
//    @OnClick(R.id.equipment_management)
//    public void onViewClicked() {
//        Intent intent = new Intent();
//        intent.setClass(getContext(), EquipmentManagementActivity.class);
//        startActivity(intent);
//    }

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
        table.setLayoutManager(layoutManager);

        unbinder = ButterKnife.bind(this);
        mPresenter.getEquipmentData(mToken, lineId);
        mPresenter.getPointData(mToken, lineId);
        mPresenter.getPlaneData(mToken, lineId);
//        initData();  //  初始化地面沉降统计图

    }
    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.22
     * @description 显示设备信息
     */
    @Override
    public void showEquipmentData(EquipmentData data) {
        tbmname.setText(data.getShield_name());
        tbmname.setVisibility(View.VISIBLE);

        tbmphoto.setImageResource(R.mipmap.crec_logo);
        tbmphoto.setVisibility(View.VISIBLE);

        presentrings.setText("第"+data.getDriving_circle_num()+"环");
        presentrings.setVisibility(View.VISIBLE);

        schedule.setText(data.getSchedule());
        schedule.setVisibility(View.VISIBLE);

        tbmstatus.setText(data.getShield_status());
        tbmstatus.setVisibility(View.VISIBLE);

        tbmstatusbig.setText(data.getShield_status());
        tbmstatus.setVisibility(View.VISIBLE);

        time.setText(data.getShield_data_time());
        time.setVisibility(View.VISIBLE);

        horizontalforward.setText(data.getHorizontal_forward_point()+"mm");
        time.setVisibility(View.VISIBLE);

        horizontalback.setText(data.getHorizontal_back_point()+"mm");
        time.setVisibility(View.VISIBLE);

        horizontaltrend.setText(data.getHorizontal_trend()+"mm/m");
        time.setVisibility(View.VISIBLE);

        verticalforward.setText(data.getVertical_forward_point()+"mm");
        time.setVisibility(View.VISIBLE);

        verticalback.setText(data.getVertical_back_point()+"mm");
        time.setVisibility(View.VISIBLE);

        verticaltrend.setText(data.getVertical_trend()+"mm/m");
        time.setVisibility(View.VISIBLE);
    }

    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.22
     * @description 显示点位信息
     */

    @Override
    public void showPointData(List<PointParameter> data) {
        pointParameters = data;
        if (data != null && data.size() > 0) {
            initTable();
            tableNULL.setVisibility(View.GONE);
        } else if (data == null || data.size() == 0) {
            tableNULL.setText(WITHOUT_DATA);
            tableNULL.setTextSize(15);
            tableNULL.setTextColor(getResources().getColor(R.color.color_Aluminum));
            tableNULL.setVisibility(View.VISIBLE);
            if (qualityAdapter != null) {
                qualityAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.22
     * @description 显示飞机图
     */

    @Override
    public void showPlaneData(String data) {
        ShieldStatusAngleData=data;
        initPlane();
    }


    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.22
     * @description 初始化点位信息表
     */

    private void initTable() {
        String point_name = "";
        String point_unit = "";
        int i = 0;
        for (PointParameter bean : pointParameters) {
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
        qualityAdapter = new ProDetailQualityAdapter(getApplicationContext(), pointParameters, maxDatas);
        table.setAdapter(qualityAdapter);
    }

    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.22
     * @description 初始化飞机图
     */

    @SuppressLint("JavascriptInterface")
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
        ShieldStatusChart.addJavascriptInterface(getContext(), "android");
        ShieldStatusChart.setWebContentsDebuggingEnabled(true);

    }
}
