package com.crec.shield.entity.project.risk;

import java.util.List;

public class RiskListItemResponse {
    private Integer code ;
    private List<RiskListItemEntity> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<RiskListItemEntity> getData() {
        return data;
    }

    public void setData(List<RiskListItemEntity> data) {
        this.data = data;
    }
}
