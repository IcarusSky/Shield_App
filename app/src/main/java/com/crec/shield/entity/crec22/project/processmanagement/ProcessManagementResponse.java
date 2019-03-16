package com.crec.shield.entity.crec22.project.processmanagement;

public class ProcessManagementResponse {
    private Integer code;
    private ProcessManagementEntity data;
    private String errorDescription;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ProcessManagementEntity getData() {
        return data;
    }

    public void setData(ProcessManagementEntity data) {
        this.data = data;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
