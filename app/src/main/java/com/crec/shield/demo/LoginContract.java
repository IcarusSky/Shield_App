package com.crec.shield.demo;

import com.crec.shield.base.BaseView;
import com.crec.shield.base.Callback;
import com.crec.shield.entity.login.DataLogin;

public interface LoginContract {
    interface Model {
        /**
         * 发送网络请求
         *
         * @param user
         * @param password
         * @param cid
         * @param callback
         */
        void getNetData(String user,String password,String cid,Callback callback);
    }

    interface View extends BaseView {
        /**
         * 当数据请求成功后，调用此接口显示数据
         * @param data 数据源
         */
        void showData(DataLogin data);
    }

    interface Presenter {
        /**
         * 登陆
         *
         * @param user
         * @param password
         * @param cid
         */
        void getData(String user,String password,String cid);
    }
}
