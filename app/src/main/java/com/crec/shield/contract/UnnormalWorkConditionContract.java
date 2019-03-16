package com.crec.shield.contract;

import com.crec.shield.base.BaseView;
import com.crec.shield.base.Callback;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalHeadData;
import com.crec.shield.entity.crec22.project.unnormalworkcondition.UnnormalListItemEntity;

import java.util.List;
public interface UnnormalWorkConditionContract {
    interface Model {
        /**
         * 发送网络请求
         * @param callback
         */
        void getUnnormalNetData(Callback callback);  //  异常数据
        void getUnnormalGanttNetData(Callback callback);   //  异常统计图数据
        void getUnnormalListNetData(Callback callback);    //  异常列表数据
    }
    interface View extends BaseView {
        /**
         * 当数据请求成功后，调用此接口显示数据
         * @param data 数据源
         */
        void showUnnormalData(UnnormalHeadData data);  //  异常数据
        void showUnnormalGanttData(String s);   //  异常统计图
        void showUnnormalListData(List<UnnormalListItemEntity> data);    //  异常列表
        void showWithoutData(String type);            //  显示暂无数据
    }
    interface Presenter {
        /**
         * 项目管理界面
         */
        void getUnnormalData();        //  异常数据
        void getUnnormalGanttData();        //  异常统计图
        void getUnnormalListData();         //  异常列表
    }
}
