package com.crec.shield.demo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.crec.shield.R;
import com.crec.shield.base.BaseActivity;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.entity.login.DataLogin;
import com.crec.shield.global.AppConstant;
import com.crec.shield.service.DemoIntentService;
import com.crec.shield.service.DemoPushService;
import com.crec.shield.ui2_2.activity.CompanyActivity;
import com.crec.shield.ui2_2.activity.ProjectActivity;
import com.crec.shield.utils.SPUtils;
import com.crec.shield.utils.SysApplication;
import com.crec.shield.utils.T;
import com.igexin.sdk.PushManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    private static final String ACTIVITY_TAG="LoginActivity";
    private static final Integer SUPER=0;
    private static final Integer ADMIN=1;
    private static final Integer EDITOR=2;
    private static final Integer COMPANY_EDITOR=3;
    private static final Integer PROJECT_EDITOR=4;

    private DataLogin dataLogin;
    private String cid;
    private Unbinder unbinder;
    private boolean isUser =false;
    private boolean isPwd =false;

    @BindView(R.id.et_user)
    EditText mEtUser;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        // 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);
        cid = PushManager.getInstance().getClientid(getApplicationContext());
        // 设置全屏颜色为蓝色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.login_blue));
        }
        String switchStatus = (String) SPUtils.get(AppConstant.ALARM.switchset,"");
        if ("".equals(switchStatus)){
            SPUtils.put(AppConstant.ALARM.switchset, "on");
        }
        SysApplication.getInstance().addActivity(this);
        unbinder = ButterKnife.bind(this);
        addTextChangedListener();
    }

    @Override
    protected void initInject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        if (getUsername().isEmpty() || getPassword().isEmpty()) {
            T.showLong(this, "帐号密码不能为空");
            return;
        }
        mPresenter.getData(getUsername(), getPassword(),cid);
    }

    @Override
    public void showData(DataLogin data) {
        Log.v(LoginActivity.ACTIVITY_TAG, "Enter showData()");
        dataLogin = data;
        Integer type= dataLogin.getType();
        Intent intent = new Intent();
        if (type == SUPER || type == ADMIN || type == COMPANY_EDITOR) {
            intent.setClass(LoginActivity.this, CompanyActivity.class);
//            intent.setClass(LoginActivity.this, ProjectActivity.class);
        }else if(type == EDITOR || type == PROJECT_EDITOR) {
            //intent.setClass(LoginActivity.this, ProjectUserDetailsActivity.class);
            intent.setClass(LoginActivity.this, ProjectActivity.class);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //断开View引用
        mPresenter.detachView();
        unbinder.unbind();
    }

    @Override
    public void showToast(String msg) {
        T.showLong(this, msg);
    }

    private String getUsername() {
        return mEtUser.getText().toString().trim();
    }

    private String getPassword() {
        return mEtPassword.getText().toString().trim();
    }

    private void addTextChangedListener(){
        mEtUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isUser = !TextUtils.isEmpty(editable.toString());
                checkLoginState();
            }
        });
        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isPwd = !TextUtils.isEmpty(editable.toString());
                checkLoginState();
            }
        });
    }

    private void checkLoginState() {
        if (isPwd && isUser){
            mBtnLogin.setBackgroundResource(R.mipmap.img_login);
        }else {
            mBtnLogin.setBackgroundResource(R.mipmap.img_login_none);
        }
    }
}

