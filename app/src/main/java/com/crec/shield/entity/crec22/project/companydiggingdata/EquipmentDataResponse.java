package com.crec.shield.entity.crec22.project.companydiggingdata;

public class EquipmentDataResponse {
    private Integer code;
    private EquipmentData data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public EquipmentData getData() {
        return data;
    }

    public void setData(EquipmentData data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "{ code:" + code + ", data:" + data + "}";
    }
}
