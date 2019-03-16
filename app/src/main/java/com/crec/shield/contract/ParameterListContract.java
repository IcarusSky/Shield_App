package com.crec.shield.contract;

import com.crec.shield.base.BaseView;
import com.crec.shield.base.Callback;
import com.crec.shield.entity.crec22.project.parameterlist.ParameterListEntity;
import com.crec.shield.entity.crec22.project.parameterlist.StatusData;

import java.util.List;

public interface ParameterListContract {
    interface Model{
        void getParameterListNetData(Callback callback);
    }
    interface View extends BaseView {
        void showParameterListData(List<ParameterListEntity> data);
    }
    interface Presenter {
        void getParameterListData();
    }
}
