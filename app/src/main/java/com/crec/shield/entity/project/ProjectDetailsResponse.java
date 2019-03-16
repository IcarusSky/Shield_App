package com.crec.shield.entity.project;

import java.util.List;

public class ProjectDetailsResponse {
    private Integer code;
    private List<ProjectDetailsEntity> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<ProjectDetailsEntity> getData() {
        return data;
    }

    public void setData(List<ProjectDetailsEntity> data) {
        this.data = data;
    }
}
