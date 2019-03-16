package com.crec.shield.ui2_2.activity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.demo.LoginActivity;
import com.crec.shield.entity.login.LoginResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.ui.activity.BaseActivity;
import com.crec.shield.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.ACCOUNT_NUMBER;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private static final String ACTIVITY_TAG="SettingActivity";

    @BindView(R.id.btn_left)
    ImageView mBtnBack;
    @BindView(R.id.r3)
    RelativeLayout mBtnFeedBack;
    @BindView(R.id.btn_exit)
    Button mBtnExit;
    @BindView(R.id.r2)
    RelativeLayout mBtnHelp;
    @BindView(R.id.r4)
    RelativeLayout mBtnAbout;
    @BindView(R.id.relative_usersetting)
    RelativeLayout RelativeUserSetting;
    @BindView(R.id.messageSwitch)
    SwitchButton mSwitchMessage;
    @BindView(R.id.username)
    TextView mTvUsername;
    @BindView(R.id.usercode)
    TextView mTvUsercode;
    @BindView(R.id.toolbar_title)
    TextView textTitle;
    private Unbinder mUnbinder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.radio));
        }
        ButterKnife.bind(this);
        mBtnBack.setOnClickListener(this);
        mBtnExit.setOnClickListener(this);
        mBtnFeedBack.setOnClickListener(this);
        mBtnHelp.setOnClickListener(this);
        mBtnAbout.setOnClickListener(this);
        RelativeUserSetting.setOnClickListener(this);

        init();
        String switchstatus = (String)SPUtils.get(AppConstant.ALARM.switchset,"");
        if ("on".equals(switchstatus)){
            mSwitchMessage.setChecked(true);

        }else {
            mSwitchMessage.setChecked(false);

        }

        mUnbinder=ButterKnife.bind(this);  //保存引用
    }
    public void init(){
        mTvUsername.setText(SPUtils.get(AppConstant.LOGINSTATUS.username,"").toString());
        mTvUsercode.setText(ACCOUNT_NUMBER+SPUtils.get(AppConstant.LOGINSTATUS.code,"").toString());
        textTitle.setText(SPUtils.get(AppConstant.LOGINSTATUS.company_name,"").toString());
    }



    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();  //释放所有绑定的view
    }

    private void logout() {
        String mToken = (String) SPUtils.get(AppConstant.LOGINSTATUS.token, "");

        OkGo.post(Url.BASE_URL + Url.LOGOUT)
                .params("token", mToken)
                .execute(new JsonCallback<LoginResponse>() {
                    @Override
                    public void onSuccess(LoginResponse response, Call call, Response response2) {
                        if(response == null){
                            return;
                        }
                        if (response.getCode() == 1) {
                            Intent it = new Intent(SettingActivity.this, LoginActivity.class);
                            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(it);
                            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            manager.cancelAll();
                            finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Intent it = new Intent(SettingActivity.this, LoginActivity.class);
                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(it);
                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        manager.cancelAll();
                        finish();
                    }
                });

    }

    @Override
    public void onClick(View v) {
        Logger.d(v.getId());
        switch (v.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.btn_exit:
                logout();
                break;
            case R.id.r3:
                Intent intent_feedback = new Intent(SettingActivity.this,SettingFeedBackActivity.class);
                intent_feedback.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent_feedback);
                break;
            case R.id.r2:
                Intent intent_help = new Intent(SettingActivity.this,SettingHelpActivity.class);
                intent_help.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent_help);
                break;
            case R.id.r4:
                Intent intent_about = new Intent(SettingActivity.this,SettingAboutActivity.class);
                intent_about.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent_about);
                break;
        }
    }
}