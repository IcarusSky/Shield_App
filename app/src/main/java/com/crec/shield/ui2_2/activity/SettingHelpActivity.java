package com.crec.shield.ui2_2.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.crec.shield.R;
import com.crec.shield.ui.activity.BaseActivity;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingHelpActivity extends BaseActivity implements View.OnClickListener {

    private static final String ACTIVITY_TAG="SettingHelpActivity";

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_help);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.radio));
        }
        ButterKnife.bind(this);

        mIvBack.setOnClickListener(this);
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColor(SettingHelpActivity.this, getResources().getColor(R.color.AppBluePri));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back:
                finish();
                break;
        }
    }
}
