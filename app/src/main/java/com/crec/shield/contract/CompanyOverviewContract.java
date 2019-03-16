package com.crec.shield.contract;

import com.crec.shield.base.BaseView;
import com.crec.shield.base.Callback;
import com.crec.shield.entity.crec22.project.projectdevice.Data;

public interface CompanyOverviewContract {
    interface Model {
        /**
         * 发送网络请求
         */
        void getProjectDeviceNetData(Callback callback);
    }

    interface View extends BaseView {
        void showProjectDeviceData(Data data);
    }

    interface Presenter {
        void getProjectDeviceData();


    }

}
