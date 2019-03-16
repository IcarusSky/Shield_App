package com.crec.shield.ui2_2.activity;

import android.content.Intent;
import android.widget.ImageView;

import com.crec.shield.R;
import com.crec.shield.contract.SafetyManagementContract;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.presenter.SafetyManagementPresenter;
import com.crec.shield.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SafetyManagementActivity extends BaseActivity<SafetyManagementPresenter> implements SafetyManagementContract.View {

    @BindView(R.id.risk_details)
    ImageView riskDetails;

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_safety_management;
    }

    @OnClick(R.id.risk_details)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setClass(getContext(), RiskSourceActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.unusual_details)
    public void onViewClicked1() {
        Intent intent = new Intent();
        intent.setClass(getContext(), UnnormalWorkConditionActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ground_setting)
    public void onGroundClicked() {
        Intent intent = new Intent();
        intent.setClass(getContext(), SubsidenceDataMonitorActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
