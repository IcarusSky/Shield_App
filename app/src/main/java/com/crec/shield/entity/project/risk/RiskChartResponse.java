package com.crec.shield.entity.project.risk;

import com.crec.shield.entity.project.risk.RiskChartEntity;

public class RiskChartResponse {

    public boolean success;
    public RiskChartEntity data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public RiskChartEntity getData() {
        return data;
    }

    public void setData(RiskChartEntity data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "{ success:" + success + ", data:" + data + "}";
    }

}
