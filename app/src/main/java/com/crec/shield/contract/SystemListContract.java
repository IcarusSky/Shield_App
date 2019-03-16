package com.crec.shield.contract;

import com.crec.shield.base.BaseView;
import com.crec.shield.base.Callback;
import com.crec.shield.entity.crec22.project.parameterlist.ParameterListEntity;

import java.util.List;

public interface SystemListContract {
    interface Model{
        void getSystemListNetData(Callback callback);
    }
    interface View extends BaseView {
        void showSystemListData(List<ParameterListEntity> data);

    }
    interface Presenter {
        void getSystemListData();
    }
}
