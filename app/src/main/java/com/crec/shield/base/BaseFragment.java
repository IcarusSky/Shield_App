package com.crec.shield.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crec.shield.di.DaggerFragmentComponent;
import com.crec.shield.di.FragmentComponent;
import com.crec.shield.global.IstuaryGlobal;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment <T extends BasePresenter>extends Fragment implements BaseView {

    private Unbinder unBinder;

    @Inject
    protected T mPresenter;

    @Override
    public void onAttach(Activity activity) {
        initInject(DaggerFragmentComponent.builder()
                .appComponent(IstuaryGlobal.getInst().getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build());
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(this.getLayoutId(), container, false);
        unBinder = ButterKnife.bind(this, view);
        mPresenter.attachView(this);
        return view;
    }

    protected abstract void initInject(FragmentComponent fragmentComponent);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
//        unBinder.unbind();
    }

    protected abstract int getLayoutId();
}
