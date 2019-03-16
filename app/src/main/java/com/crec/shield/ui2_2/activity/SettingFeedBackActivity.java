package com.crec.shield.ui2_2.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.crec.shield.R;
import com.crec.shield.entity.BaseResponse;
import com.crec.shield.global.AppConstant;
import com.crec.shield.network.DialogCallback;
import com.crec.shield.network.Url;
import com.crec.shield.ui.activity.BaseActivity;
import com.crec.shield.utils.SPUtils;
import com.crec.shield.utils.T;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

public class SettingFeedBackActivity extends BaseActivity implements View.OnClickListener{

    private static final String ACTIVITY_TAG="SettingFeedBackActivity";
    private String mToken = SPUtils.get(AppConstant.LOGINSTATUS.token,"").toString();
    private Unbinder unbinder;

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.btn_commit)
    Button mBtnCommit;
    @BindView(R.id.et_title)
    EditText mEtTitle;
    @BindView(R.id.et_tel)
    EditText mEtTel;
    @BindView(R.id.et_content)
    EditText mEtContent;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_feedback);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.radio));
        }
        unbinder = ButterKnife.bind(this);
        init();

    }
    private void commitData() {

        Log.v(SettingFeedBackActivity.ACTIVITY_TAG, "Enter commitData()");

        String tel = mEtTel.getText().toString().trim();
        String title = mEtTitle.getText().toString().trim();
        String content = mEtContent.getText().toString().trim();

        if (tel == null || tel.length()== 0 || title == null  || title.length() == 0 || content == null || content.length() ==0) {
            T.showShort(this, getString(R.string.tv_feedback_notice));
            return;
        }

        OkGo.post(Url.BASE_URL + Url.FEED_BACK)
                .params("token",mToken)
                .params("title", title)
                .params("message",content)

                .execute(new DialogCallback<BaseResponse>(SettingFeedBackActivity.this) {
                    @Override
                    public void onSuccess(BaseResponse response, Call call, Response response2) {

                            T.showShort(SettingFeedBackActivity.this, getString(R.string.tv_feedback_commit));
                            finish();
                    }
                });
    }
    private void init() {
        Log.v(SettingFeedBackActivity.ACTIVITY_TAG, "Enter init()");
        mBtnCommit.setOnClickListener(this);
        mIvBack.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColor(SettingFeedBackActivity.this, getResources().getColor(R.color.AppBluePri));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_commit:
                commitData();
                break;
        }
    }
}
