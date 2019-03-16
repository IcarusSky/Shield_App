package com.crec.shield.entity.project.progress;

/**
 * Created by annie on 2018/8/23.
 */

public class WorkEfficiencyResponse {
    public boolean success;
    public WorkEfficiencyEntity data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public WorkEfficiencyEntity getData() {
        return data;
    }

    public void setData(WorkEfficiencyEntity data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "{ success:" + success + ", data:" + data + "}";
    }
}
