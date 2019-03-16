package com.crec.shield.contract;

import com.crec.shield.base.BaseView;
import com.crec.shield.base.Callback;
import com.crec.shield.entity.crec22.project.companymanagementdata.ManagementData;

public interface ManagementDataContract {

    interface Model {
        /**
         * 发送网络请求
         */
        void getNetData(String mToken,String companyId, Callback callback);
    }
    interface View extends BaseView {

        /**
         * 当数据请求成功后，调用此接口显示数据
         * @param data 数据源
         */
        void showData(ManagementData data);

    }

    interface Presenter {
        /**
         * 管理数据界面
         */
        void getData(String mToken,String companyId);

    }
}
