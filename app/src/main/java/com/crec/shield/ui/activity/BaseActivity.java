package com.crec.shield.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crec.shield.R;
import com.crec.shield.global.IstuaryGlobal;
import com.crec.shield.utils.ActivityCollector;
import com.jaeger.library.StatusBarUtil;

import static com.speedata.postest.PosC.home;
import static com.speedata.postest.PosC.recent;


public abstract class BaseActivity extends AppCompatActivity {

    private static final String ACTIVITY_TAG="BaseActivity";

    protected Context mContext;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);

        initForib();

    }

    void initForib() {

        Log.v(BaseActivity.ACTIVITY_TAG, "Enter initForib()");

        home(false, IstuaryGlobal.context);
        recent(false,IstuaryGlobal.context);
//         禁用下拉
//        upmenu(true,IstuaryGlobal.context);



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.gray1));
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();

    }
}
