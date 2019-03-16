package com.crec.shield.entity.crec22.project.qualitymanagement;

import java.util.List;

public class QualityBiasnResponse {
    private Integer code;
    private QualityBiasnEntity data;
    private String errorDescription;

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public QualityBiasnEntity getData() {
        return data;
    }

    public void setData(QualityBiasnEntity data) {
        this.data = data;
    }
}
