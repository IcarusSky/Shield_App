package com.crec.shield.entity.crec22.project.projectlistforcity;

import java.util.List;

public class ProjectListForCityResponse {
    private Integer code;
    private List<ProjectListForCityData> data;
    private String errorDescription;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<ProjectListForCityData> getData() {
        return data;
    }

    public void setData(List<ProjectListForCityData> data) {
        this.data = data;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
