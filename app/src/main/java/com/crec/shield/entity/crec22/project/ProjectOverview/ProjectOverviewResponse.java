package com.crec.shield.entity.crec22.project.projectoverview;

public class ProjectOverviewResponse {
    private Integer code;
    private ProjectOverviewData data;
    private String errorDescription;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ProjectOverviewData getData() {
        return data;
    }

    public void setData(ProjectOverviewData data) {
        this.data = data;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    @Override
    public String toString() {
        return "{ code:" + code + ", data:" + data + "}";
    }

}
