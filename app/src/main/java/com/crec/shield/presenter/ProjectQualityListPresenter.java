package com.crec.shield.presenter;

import android.database.sqlite.SQLiteDatabase;

import com.crec.shield.base.BasePresenter;
import com.crec.shield.base.Callback;
import com.crec.shield.contract.ProjectQualityListContract;
import com.crec.shield.entity.login.DataLogin;
import com.crec.shield.model.ProjectQualityListModel;
import com.crec.shield.entity.project.quality.Quality;
import com.crec.shield.utils.SqliteHelper;

import java.util.List;

import javax.inject.Inject;

public class ProjectQualityListPresenter extends BasePresenter<ProjectQualityListContract.View> implements ProjectQualityListContract.Presenter  {
    //注意，提供Presenter 的实例化对象
    private ProjectQualityListContract.Model model;
    @Inject
    public ProjectQualityListPresenter() {
        model = new ProjectQualityListModel();
    }

    @Override
    public void getData(String mToken, String lineId, String status) {
        if (!isViewAttached()) {
            //如果没有View引用就不加载数据
            return;
        }
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        model.getNetData(mToken, lineId, status, new Callback<List<Quality>>() {

            @Override
            public void onSuccess(List<Quality> data) {
                //调用view接口显示数据
                if (isViewAttached()) {
                    mView.showData(data);

                }
            }

            @Override
            public void onFailure(String msg) {
                //调用view接口提示失败信息
                if (isViewAttached()) {
                    mView.showToast(msg);
                }
            }

            @Override
            public void onError() {
                //调用view接口提示请求异常
                if (isViewAttached()) {
                    mView.showError();
                }
            }

            @Override
            public void onComplete() {
                // 隐藏正在加载进度条
                if (isViewAttached()) {
                    mView.hideLoading();
                }
            }
        });
    }
}
