package com.crec.shield.entity.project.quality;

import java.util.List;

public class QualityResponse {
    private Integer code;
    private List<Quality> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<Quality> getData() {
        return data;
    }

    public void setData(List<Quality> data) {
        this.data = data;
    }
}
