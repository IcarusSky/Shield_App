package com.crec.shield.contract;

import com.crec.shield.base.BaseView;
import com.crec.shield.base.Callback;
import com.crec.shield.entity.crec22.project.companymanagementquality.LineQualityEntity;
import com.crec.shield.entity.crec22.project.management.ProjectSystemData;
import com.crec.shield.entity.crec22.project.qualitymanagement.QualityBiasnEntity;

import java.util.List;


public interface CompanyManagementQualityContract {
    interface Model {
        /**
         * 发送网络请求
         */
        void getCompanyManagementQualityNetData(Callback callback);
    }

    interface View extends BaseView {
        /**
         * 当数据请求成功后，调用此接口显示数据
         * @param data 数据源
         */
        void showCompanyManagementQualityData(List<LineQualityEntity> data);
    }

    interface Presenter {
        /**
         * 项目管理界面
         */
        void getCompanyManagementQualityData();
    }
}
