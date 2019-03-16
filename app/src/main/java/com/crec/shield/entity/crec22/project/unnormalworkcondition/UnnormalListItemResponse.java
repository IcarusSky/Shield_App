package com.crec.shield.entity.crec22.project.unnormalworkcondition;

import java.util.List;
public class UnnormalListItemResponse {
    private Integer code ;
    private List<UnnormalListItemEntity> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<UnnormalListItemEntity> getData() {
        return data;
    }

    public void setData(List<UnnormalListItemEntity> data) {
        this.data = data;
    }
}
