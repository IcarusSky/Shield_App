package com.crec.shield.entity.project.status;

public class ShieldStatusResponse {
    private Integer code;
    private ShieldStatusEntity data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ShieldStatusEntity getData() {
        return data;
    }

    public void setData(ShieldStatusEntity data) {
        this.data = data;
    }
}
