package com.crec.shield.contract;

import com.crec.shield.base.BaseView;
import com.crec.shield.base.Callback;


import java.util.List;
public interface CompanySafeManagementContract {
    interface Model {
        /**
         * 发送网络请求
         */
        void getRankNetData(String mToken,String companyId,Callback callback);
    }
    interface View extends BaseView {
        /**
         * 当数据请求成功后，调用此接口显示数据
         * @param data 数据源
         */

        void showRankData(String data);

    }

    interface Presenter {
        /**
         * 项目管理界面
         */

        void getRankData(String mToken, String companyId);

    }
}
