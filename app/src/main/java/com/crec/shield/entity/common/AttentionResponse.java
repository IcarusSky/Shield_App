package com.crec.shield.entity.common;

import java.util.List;

public class AttentionResponse {

    private Integer code;
    private List<AttentionParentEntity> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer  code) {
        this.code = code;
    }

    public List<AttentionParentEntity> getData() {
        return data;
    }

    public void setData(List<AttentionParentEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{ code:" + code + ", data:" + data  + "}";
    }
}
