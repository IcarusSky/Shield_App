package com.crec.shield.entity.crec22.project.management;


public class RiskDetailsResponse {
    private Integer code;
    private RiskDetailsData data;
    private String errorDescription;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public RiskDetailsData getData() {
        return data;
    }

    public void setData(RiskDetailsData data) {
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
