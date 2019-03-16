package com.crec.shield.entity.crec22.project.parameterlist;

import java.util.List;

public class ParameterListResponse {
    private Integer code;
    private List<ParameterListEntity>  data;
    private String errorDescription;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<ParameterListEntity> getData() {
        return data;
    }

    public void setData(List<ParameterListEntity> data) {
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
