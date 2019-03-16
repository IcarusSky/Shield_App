package com.crec.shield.ui2_2.activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


import com.crec.shield.R;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.contract.ProcessManagementContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.crec22.project.processmanagement.ProcessManagementEntity;
import com.crec.shield.entity.crec22.project.processmanagement.ProcessManagementParamter;
import com.crec.shield.global.AppConstant;
import com.crec.shield.global.HtmlConstant;
import com.crec.shield.global.StaticConstant;
import com.crec.shield.network.Url;
import com.crec.shield.presenter.ProcessManagementPresenter;
import com.crec.shield.ui.activity.ProjectDetailsActivity;
import com.crec.shield.utils.AppUtils;
import com.crec.shield.utils.ProgressDialogUtils;
import com.crec.shield.utils.SPUtils;
import com.crec.shield.utils.WebViewUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;


public class ProcessManagementActivity extends BaseActivity<ProcessManagementPresenter> implements ProcessManagementContract.View {
    private ProcessManagementEntity processManagementEntity = new ProcessManagementEntity();
    private String mtoken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
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
    private String propelRankResultData;
    @BindView(R.id.propel_yestoday)
    Button mbtnPropelYestoday;
    @BindView(R.id.propel_sevenday)
    Button mbtnPropelSevenday;
    @BindView(R.id.propel_day)
    Button mbtnPropelDay;
    @BindView(R.id.propel_month)
    Button mbtnPropelMonth;
    @BindView(R.id.propel_rank)
    WebView propelRank;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @Override
    public int getLayoutId() {
        return R.layout.activity_process_management;
    }
    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getData();
        // 日排名按钮监听事件
        mbtnPropelDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = ProgressDialogUtils.createLoadingDialog(getContext(), "加载中...");
                mbtnPropelDay.setTextColor(Color.parseColor("#FFFFFF"));
                mbtnPropelDay.setBackgroundColor(Color.parseColor("#5A7BEF"));
                mbtnPropelMonth.setTextColor(getResources().getColor(R.color.font_black));
                mbtnPropelMonth.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                mbtnPropelYestoday.setTextColor(getResources().getColor(R.color.font_black));
                mbtnPropelYestoday.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                mbtnPropelSevenday.setTextColor(getResources().getColor(R.color.font_black));
                mbtnPropelSevenday.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                PropelDataType = StaticConstant.PROPEL_RANK_DATA_DAY;
                getPropelRankData(PropelDataType);
            }
        });
        // 月排名按钮监听事件
        mbtnPropelMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = ProgressDialogUtils.createLoadingDialog(getContext(), "加载中...");
                PropelDataType = StaticConstant.PROPEL_RANK_DATA_MONTH;
                mbtnPropelMonth.setTextColor(Color.parseColor("#FFFFFF"));
                mbtnPropelMonth.setBackgroundColor(Color.parseColor("#5A7BEF"));
                mbtnPropelDay.setTextColor(getResources().getColor(R.color.font_black));
                mbtnPropelDay.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                mbtnPropelYestoday.setTextColor(getResources().getColor(R.color.font_black));
                mbtnPropelYestoday.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                mbtnPropelSevenday.setTextColor(getResources().getColor(R.color.font_black));
                mbtnPropelSevenday.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                getPropelRankData(PropelDataType);
            }
        });

        // 昨日排名按钮监听事件
        mbtnPropelYestoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = ProgressDialogUtils.createLoadingDialog(getContext(), "加载中...");
                mbtnPropelYestoday.setTextColor(Color.parseColor("#FFFFFF"));
                mbtnPropelYestoday.setBackgroundColor(Color.parseColor("#5A7BEF"));
                mbtnPropelDay.setTextColor(getResources().getColor(R.color.font_black));
                mbtnPropelDay.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                mbtnPropelMonth.setTextColor(getResources().getColor(R.color.font_black));
                mbtnPropelMonth.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                mbtnPropelSevenday.setTextColor(getResources().getColor(R.color.font_black));
                mbtnPropelSevenday.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                PropelDataType = StaticConstant.PROPEL_RANK_DATA_YESTODAY;
                getPropelRankData(PropelDataType);
            }
        });

        // 七日排名按钮监听事件
        mbtnPropelSevenday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = ProgressDialogUtils.createLoadingDialog(getContext(), "加载中...");
                mbtnPropelSevenday.setTextColor(Color.parseColor("#FFFFFF"));
                mbtnPropelSevenday.setBackgroundColor(Color.parseColor("#5A7BEF"));
                mbtnPropelMonth.setTextColor(getResources().getColor(R.color.font_black));
                mbtnPropelMonth.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                mbtnPropelDay.setTextColor(getResources().getColor(R.color.font_black));
                mbtnPropelDay.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                mbtnPropelYestoday.setTextColor(getResources().getColor(R.color.font_black));
                mbtnPropelYestoday.setBackgroundResource(R.drawable.rankbtn_shape_unselected);
                PropelDataType = StaticConstant.PROPEL_RANK_DATA_SEVENDAY;
                getPropelRankData(PropelDataType);
            }
        });

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getPropelRankData(PropelDataType);    // 掘进工效排名数据获取
            }
        });
        refreshLayout.autoRefresh();
        //return view;
    }

    /**
     * @company vinelinx
     * @author AnnieYoung
     * @description 掘进数据获取
     * @modifiedBy AnnieYoung
     */
    private void getPropelRankData(String PropelDataType) {
        if (PropelDataType.equals(StaticConstant.PROPEL_RANK_DATA_DAY)) {
            initJson(processManagementEntity.getTodayRanking());
            mHandler.sendEmptyMessageDelayed(1, 100);
            initPropelRank();
        } else if (PropelDataType.equals(StaticConstant.PROPEL_RANK_DATA_MONTH)) {
            initJson(processManagementEntity.getThirtyRanking());
            mHandler.sendEmptyMessageDelayed(1, 100);
            initPropelRank();
        }else if (PropelDataType.equals(StaticConstant.PROPEL_RANK_DATA_SEVENDAY)) {
            initJson(processManagementEntity.getSevenRanking());
            mHandler.sendEmptyMessageDelayed(1, 100);
            initPropelRank();
        }else if (PropelDataType.equals(StaticConstant.PROPEL_RANK_DATA_YESTODAY)) {
            initJson(processManagementEntity.getYesterdayRanking());
            mHandler.sendEmptyMessageDelayed(1, 100);
            initPropelRank();
        }
    }

    /**
     *
     */
    private void initJson(List<ProcessManagementParamter> aList){
        String[] arr = new String[aList.size()];
        String midArray;
        String buildArray="";
        for(int i=0;i<aList.size();i++){
            arr[i]="\""+aList.get(i).getProjectId()+"\""+","+"\""+aList.get(i).getProjectName()+"\""+","+"\""+aList.get(i).getFinishNumber()+"\""+","+"\""+aList.get(i).getDayNumber()+"\""+","+"\""+aList.get(i).getNightNumber()+"\"";
            midArray=(aList.size()-1 == i)?"":",";
            buildArray = buildArray +"["+arr[i]+"]"+midArray;
        }
        String totalArray = "{\"code\":1,\"data\":{\"data\":["+buildArray+"]}"+",\"errorDescription\":null}";
        propelRankResultData = totalArray;
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
        propelRank.loadUrl(HtmlConstant.PROPEL_RANK_NEW);
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
            Intent intent = new Intent(getContext(), ProjectDetailsActivity.class);
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
            Intent intent = new Intent(getContext(), ProjectDetailsActivity.class);
            intent.putExtra("keyRisk", _selectprojectRisk);
            //启动意图
            startActivity(intent);

            return "选择了: testbackRisk: " + _selectprojectRisk;
        }
    }

    @Override
    public void showData(ProcessManagementEntity data) {
        processManagementEntity = data;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //断开View引用
        mPresenter.detachView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
