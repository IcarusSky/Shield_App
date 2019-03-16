package com.crec.shield.entity.overview.overview;

public class RiskPieChartResponse {

    public String code;
    public RiskPieChartEntity data;

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code;}

    public RiskPieChartEntity getData() {
        return data;
    }

    public void setData(RiskPieChartEntity data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "{ code:" + code + ", data:" + data + "}";
    }

}
