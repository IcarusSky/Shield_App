package com.crec.shield.contract;

import com.crec.shield.base.BaseView;
import com.crec.shield.base.Callback;
import com.crec.shield.entity.project.risk.RiskHeadData;
import com.crec.shield.entity.project.risk.RiskListItemEntity;

import java.util.List;

public interface RiskSourceContract {

    interface Model {
        /**
         * 发送网络请求
         * @param callback
         */
        void getRiskSourceNetData(Callback callback);  //  风险源数据数据
        void getRiskGanttNetData(Callback callback);   //  风险源统计图数据
        void getRiskListNetData(Callback callback);    //  风险源列表数据
    }

    interface View extends BaseView {
        /**
         * 当数据请求成功后，调用此接口显示数据
         * @param data 数据源
         */
        void showRiskSourceData(RiskHeadData data);  //  风险源数据
        void showRiskGanttData(String s);   //  风险源统计图
        void showRiskListData(List<RiskListItemEntity> data);    //  风险源列表
        void showWithoutData(String type);            //  显示暂无数据
    }

    interface Presenter {
        /**
         * 项目管理界面
         */
        void getRiskSourceData();        //  风险源数据
        void getRiskGanttData();        //  风险源统计图
        void getRiskListData();         //  风险源列表
    }

}
