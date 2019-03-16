package com.crec.shield.contract;
import android.view.View;

import com.crec.shield.base.BaseView;
import com.crec.shield.base.Callback;
import com.crec.shield.entity.project.quality.Quality;

import java.util.List;

public interface ProjectQualityListContract {
    interface Model {
        /**
         * 发送网络请求
         */
        void getNetData(String mToken, String lineId,String status,Callback callback);
    }

    interface View extends BaseView {
        /**
         * 当数据请求成功后，调用此接口显示数据
         * @param data 数据源
         */
        void showData(List<Quality> data);

    }

    interface Presenter {
        /**
         * 项目管理界面
         */
        void getData(String mToken, String lineId, String status);
    }
}
