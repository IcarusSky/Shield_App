package com.crec.shield.entity.crec22.project.unnormalworkcondition;

public class UnnormalHeadDataResponse {
    public Integer code;
    public UnnormalHeadData data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public UnnormalHeadData getData() {
        return data;
    }

    public void setData(UnnormalHeadData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{ code:" + code + ", data:" + data + "}";
    }
}
