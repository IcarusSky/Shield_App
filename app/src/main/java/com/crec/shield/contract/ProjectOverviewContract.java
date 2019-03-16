package com.crec.shield.contract;

import com.crec.shield.base.BaseView;
import com.crec.shield.base.Callback;
import com.crec.shield.entity.crec22.project.projectoverview.ProjectOverviewData;


public interface ProjectOverviewContract {
    interface Model{
        void getNetData(Callback callback);
    }
    interface View extends BaseView {
        void showData(ProjectOverviewData data);
    }
    interface Presenter {
        void getData();
    }
}
