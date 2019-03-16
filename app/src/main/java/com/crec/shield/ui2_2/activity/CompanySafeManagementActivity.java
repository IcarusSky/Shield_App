package com.crec.shield.ui2_2.activity;

import android.annotation.SuppressLint;
import android.util.Log;
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


import com.crec.shield.R;
import com.crec.shield.adapter.ProDetailQualityAdapter;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.contract.DiggingDataContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.R;
import com.crec.shield.contract.CompanySafeManagementContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.global.AppConstant;
import com.crec.shield.presenter.CompanySafeManagementPresenter;

import com.crec.shield.global.HtmlConstant;
import com.crec.shield.ui.fragment.MainFragment;
import com.crec.shield.utils.SPUtils;
import com.crec.shield.utils.WebViewUtil;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.OnClick;

public class CompanySafeManagementActivity extends BaseActivity<CompanySafeManagementPresenter> implements CompanySafeManagementContract.View {

    //风险统计图
    @BindView(R.id.risk_rank)
    WebView riskRank;
    @BindView(R.id.btn_left)
    ImageView button;
    private Unbinder unbinder;
    private String riskRankResultData;
    String mToken = SPUtils.get(AppConstant.LOGINSTATUS.token, "").toString();
    String orgId = SPUtils.get(AppConstant.LOGINSTATUS.company_id, "").toString();
    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_company_safe_management;
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
//        table.setLayoutManager(layoutManager);

        unbinder = ButterKnife.bind(this);
        mPresenter.getRankData(mToken,orgId);
//        initData();  //  初始化地面沉降统计图

    }
    @OnClick(R.id.btn_left)//返回键
    public void onViewClicked() {
        super.onBackPressed();
        finish();
    }
    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.27
     * @description 显示风险统计图
     */
    @Override
    public void showRankData(String data) {
        riskRankResultData=data;
        initRiskRank();
    }
    /**
     * @company vinelinx
     * @author shenbo
     * @date 2019.2.27
     * @description 初始化风险统计图
     */
    @SuppressLint("JavascriptInterface")
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
        riskRank.addJavascriptInterface(getContext(), "android");
        riskRank.setWebContentsDebuggingEnabled(true);
    }

}
