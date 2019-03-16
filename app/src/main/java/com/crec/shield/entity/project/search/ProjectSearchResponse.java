package com.crec.shield.entity.project.search;

import java.util.List;

public class ProjectSearchResponse {

    public boolean success;
    public List<ProjectSearchEntity> data;

    public List<ProjectSearchEntity> getData() {
        return data;
    }

    public void setData(List<ProjectSearchEntity> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }



    @Override
    public String toString() {
        return "{ success:" + success + ", data:" + data + "}";
    }

}
