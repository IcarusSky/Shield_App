package com.crec.shield.ui2_2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crec.shield.R;
import com.crec.shield.base.BaseFragment;
import com.crec.shield.contract.CompanyPersonalContract;
import com.crec.shield.demo.LoginActivity;
import com.crec.shield.di.FragmentComponent;
import com.crec.shield.entity.login.LoginResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.JsonCallback;
import com.crec.shield.network.Url;
import com.crec.shield.presenter.CompanyPersonalPresenter;
import com.crec.shield.ui2_2.activity.SettingAboutActivity;
import com.crec.shield.ui2_2.activity.SettingFeedBackActivity;
import com.crec.shield.ui2_2.activity.SettingHelpActivity;
import com.crec.shield.utils.SPUtils;
import com.crec.shield.utils.SysApplication;
import com.lzy.okgo.OkGo;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

import static com.crec.shield.global.StaticConstant.ACCOUNT_NUMBER;

public class CompanyPersonalFragment extends BaseFragment<CompanyPersonalPresenter> implements CompanyPersonalContract.View {

    @BindView(R.id.toolbar_title)
    TextView textTitle;
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
    private Unbinder unbinder;

    @Override
    protected void initInject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_company_personal;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(this.getLayoutId(), container, false);
        SysApplication.getInstance().addActivity(getActivity());
        unbinder = ButterKnife.bind(this, view);

        init();
        String switchstatus = (String) SPUtils.get(AppConstant.ALARM.switchset,"");
        if ("on".equals(switchstatus)){
            mSwitchMessage.setChecked(true);

        }else {
            mSwitchMessage.setChecked(false);

        }

        return view;
    }

    public void init(){
        mTvUsername.setText(SPUtils.get(AppConstant.LOGINSTATUS.username,"").toString());
        mTvUsercode.setText(ACCOUNT_NUMBER+SPUtils.get(AppConstant.LOGINSTATUS.code,"").toString());
        textTitle.setText(SPUtils.get(AppConstant.LOGINSTATUS.company_name,"").toString());
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
                            Intent it = new Intent(getActivity(), LoginActivity.class);
                            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(it);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Intent it = new Intent(getActivity(), LoginActivity.class);
                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(it);
                    }
                });

    }

    @OnClick({R.id.btn_exit, R.id.r2, R.id.r3, R.id.r4})
    public void onViewClicked(View v){
        switch (v.getId()){
            case R.id.r2:
                Intent intent_feedback = new Intent(getActivity(),SettingFeedBackActivity.class);
                intent_feedback.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent_feedback);
                break;
            case R.id.r3:
                Intent intent_help = new Intent(getActivity(),SettingHelpActivity.class);
                intent_help.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent_help);
                break;
            case R.id.r4:
                Intent intent_about = new Intent(getActivity(),SettingAboutActivity.class);
                intent_about.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent_about);
                break;
            case R.id.btn_exit:
                logout();
                break;
        }

    }



    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //断开View引用
        mPresenter.detachView();
        unbinder.unbind();
    }
}
