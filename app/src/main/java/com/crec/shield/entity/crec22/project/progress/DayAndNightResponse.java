package com.crec.shield.entity.crec22.project.progress;

import java.util.List;

public class DayAndNightResponse {

    private Integer code;
    private List<DayAndNightEntity> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<DayAndNightEntity> getData() {
        return data;
    }

    public void setData(List<DayAndNightEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{ code:" + code + ", data:" + data + "}";
    }
}
