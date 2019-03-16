package com.crec.shield.entity.common;

import java.util.List;

public class AttentionSelectResponse {

    public String code;

    public List<AttentionParentEntity> data;

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public List<AttentionParentEntity> getData() {
        return data;
    }

    public void setData(List<AttentionParentEntity> data) {
        this.data = data;
    }



    @Override
    public String toString() {
        return "{ code:" + code + ", data:" + data + "}";
    }

}
