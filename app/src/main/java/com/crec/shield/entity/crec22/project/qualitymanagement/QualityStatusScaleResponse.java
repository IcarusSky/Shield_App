package com.crec.shield.entity.crec22.project.qualitymanagement;

public class QualityStatusScaleResponse {
    private Integer code;
    private QualityStatusScaleEntity data;
    private String errorDescription;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public QualityStatusScaleEntity getData() {
        return data;
    }

    public void setData(QualityStatusScaleEntity data) {
        this.data = data;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
