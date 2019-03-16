package com.crec.shield.entity.project.progress;

import java.util.List;

public class RingChartResponse {

    private Integer code ;
    private List<RingChartEntity> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<RingChartEntity> getData() {
        return data;
    }

    public void setData(List<RingChartEntity> data) {
        this.data = data;
    }
}
