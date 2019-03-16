package com.crec.shield.ui2_2.activity;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.crec.shield.R;
import com.crec.shield.global.AppConstant;
import com.crec.shield.ui.activity.BaseActivity;
import com.crec.shield.utils.DBManager;
import com.crec.shield.utils.SPUtils;
import com.crec.shield.utils.SqliteHelper;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MessageSettingActivity extends BaseActivity implements View.OnClickListener {

    private static final String ACTIVITY_TAG = "MessageSettingActivity";

    @BindView(R.id.btn_left)
    ImageView mBtnBack;
    @BindView(R.id.move_size)
    EditText mEtMoveSize;
    @BindView(R.id.risk_level)
    Spinner mEtRiskLevel;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;

    private String AlarmMoveize = (String) SPUtils.get(AppConstant.MESSAGE.AlarmMoveize, "");
    private String AlarmRiskLevel = (String) SPUtils.get(AppConstant.MESSAGE.AlarmRiskLevel, "");
    private Unbinder unbinder;
    private SqliteHelper sqlHelper;
    private SQLiteDatabase db;
    private int userType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_messagesetting);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.radio));
        }
        unbinder = ButterKnife.bind(this);

        sqlHelper = DBManager.getInstance(this);
        db = sqlHelper.getWritableDatabase();
        userType = sqlHelper.queryUserType(db);

        initSelect();
        mBtnBack.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String AlarmRiskLevel = mEtRiskLevel.getSelectedItem().toString().equals("") ? "1":mEtRiskLevel.getSelectedItem().toString();
                String AlarmMoveize = mEtMoveSize.getText().toString().equals("") ? "80":mEtMoveSize.getText().toString();
                if (AlarmRiskLevel.equals("Ⅰ")) {
                    AlarmRiskLevel = "1";
                    SPUtils.put(AppConstant.MESSAGE.AlarmRiskLevel, AlarmRiskLevel);
                }
                if (AlarmRiskLevel.equals("Ⅱ")) {
                    AlarmRiskLevel = "2";
                    SPUtils.put(AppConstant.MESSAGE.AlarmRiskLevel, AlarmRiskLevel);
                }
                if (AlarmRiskLevel.equals("Ⅲ")) {
                    AlarmRiskLevel = "3";
                    SPUtils.put(AppConstant.MESSAGE.AlarmRiskLevel, AlarmRiskLevel);
                }
                if (AlarmRiskLevel.equals("Ⅳ")) {
                    AlarmRiskLevel = "4";
                    SPUtils.put(AppConstant.MESSAGE.AlarmRiskLevel, AlarmRiskLevel);
                }
                SPUtils.put(AppConstant.MESSAGE.AlarmMoveize, AlarmMoveize);

                if(userType == 0 ||userType == 1 || userType == 3) {
                    Intent intent = new Intent(MessageSettingActivity.this, CompanyActivity.class);
                    intent.putExtra("setting", 1);
                    startActivity(intent);
                } else if(userType == 2 ||userType == 4){
                    Intent intent = new Intent(MessageSettingActivity.this, ProjectActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initSelect() {
        if (null == AlarmMoveize || AlarmMoveize == "") {
            mEtMoveSize.setText("80");
        } else {
            mEtMoveSize.setText(AlarmMoveize);
        }
        if (null == AlarmRiskLevel || AlarmMoveize == "") {

        } else {
            int position = 0;
            if (AlarmRiskLevel.equals("1")) {
                position = 0;
            } else if (AlarmRiskLevel.equals("2")) {
                position = 1;
            } else if (AlarmRiskLevel.equals("3")) {
                position = 2;
            } else if (AlarmRiskLevel.equals("4")) {
                position = 3;
            }
            mEtRiskLevel.setSelection(position);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        Logger.d(v.getId());
        switch (v.getId()) {
            case R.id.btn_left:
                finish();
                break;
        }
    }
}