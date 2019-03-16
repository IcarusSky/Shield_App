package com.crec.shield.entity.crec22.project.subsidencedatamonitor;

public class GroundSettingResponse {
    private Integer code;
    private GroundSettingData data;
    private String errorDescription;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public GroundSettingData getData() {
        return data;
    }

    public void setData(GroundSettingData data) {
        this.data = data;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
