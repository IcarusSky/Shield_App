package com.crec.shield.entity.overview.overview;

/**
 * Created by annie on 2018/8/23.
 */

public class RiskRankResponse {
    public String code;
    public RiskRankEntity data;

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public RiskRankEntity getData() {
        return data;
    }

    public void setData(RiskRankEntity data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "{ code:" + code + ", data:" + data + "}";
    }
}
