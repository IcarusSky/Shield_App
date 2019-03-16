package com.crec.shield.entity.project.status;

import java.util.List;

public class PointParameterResponse {
    private Integer code;
    private List<PointParameterEntity> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<PointParameterEntity> getData() {
        return data;
    }

    public void setData(List<PointParameterEntity> data) {
        this.data = data;
    }
}
