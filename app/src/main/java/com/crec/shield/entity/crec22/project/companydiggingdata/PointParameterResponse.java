package com.crec.shield.entity.crec22.project.companydiggingdata;

import java.util.List;

public class PointParameterResponse {
    private Integer code;
    private List<PointParameter> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<PointParameter> getData() {
        return data;
    }

    public void setData(List<PointParameter> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{ code:" + code + ", data:" + data + "}";
    }
}
