package com.crec.shield.entity.crec22.project.companymanagementquality;

import java.util.List;

public class CompanyManagementQualityResponse {
    private int code;
    private List<LineQualityEntity> data;
    private String errorDescription;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<LineQualityEntity> getData() {
        return data;
    }

    public void setData(List<LineQualityEntity> data) {
        this.data = data;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
