package com.crec.shield.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.support.annotation.Nullable;
import com.crec.shield.R;
import com.crec.shield.di.ActivityComponent;
import com.crec.shield.di.DaggerActivityComponent;
import com.crec.shield.global.IstuaryGlobal;


import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity <T extends BasePresenter>extends AppCompatActivity implements BaseView {

    private ProgressDialog mProgressDialog;
    private Unbinder unbinder;

    @Inject//与Presenter 构造的方法的inject注解对应。自动注入
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getLayoutId());

        initInject(DaggerActivityComponent.builder()
                .appComponent(IstuaryGlobal.getInst().getAppComponent())
                .activityModule(new ActivityModule(this))
                .build());

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        unbinder = ButterKnife.bind(this);
        mPresenter.attachView(this);
    }

    protected abstract void initInject(ActivityComponent activityComponent);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 在生命周期结束时，将 presenter 与 view 之间的联系断开，防止出现内存泄露
         */
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        unbinder.unbind();
    }

    @Override
    public void showLoading() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        showToast(getResources().getString(R.string.error));
    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int getLayoutId();
}
