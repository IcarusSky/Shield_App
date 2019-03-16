package com.crec.shield.contract;

import com.crec.shield.base.BaseView;
import com.crec.shield.base.Callback;
import com.crec.shield.entity.crec22.project.companydiggingdata.EquipmentData;
import com.crec.shield.entity.crec22.project.companydiggingdata.PointParameter;
import com.crec.shield.entity.crec22.project.companydiggingdata.ParameterListEntity;

import java.util.List;


public interface DiggingDataContract {

    interface Model {
        /**
         * 发送网络请求
         */
        void getEquipmentNetData(String mToken, String lineId,Callback callback);
        void getPointNetData(String mToken, String lineId,Callback callback);
        void getPlaneNetData(String mToken, String lineId,Callback callback);
    }
    interface View extends BaseView {
        /**
         * 当数据请求成功后，调用此接口显示数据
         * @param data 数据源
         */
        void showEquipmentData(EquipmentData data);
        void showPointData(List<PointParameter> data);
        void showPlaneData(String data);

    }

    interface Presenter {
        /**
         * 项目管理界面
         */
        void getEquipmentData(String mToken, String lineId);
        void getPointData(String mToken, String lineId);
        void getPlaneData(String mToken, String lineId);

    }
}
