package com.crec.shield.entity.overview.overview;

public class ScheduleChartResponse {

    private Integer code;
    private ScheduleChartEntity data;
    private  String errorDescription;

    public Integer getCode() { return code; }

    public void setCode(Integer code) { this.code = code; }

    public ScheduleChartEntity getData() { return data; }

    public void setData(ScheduleChartEntity data) { this.data = data; }

    public String getErrorDescription() { return errorDescription; }

    public void setErrorDescription(String errorDescription) { this.errorDescription = errorDescription; }

    @Override
    public String toString() {
        return "{ code:" + code+ ", data:" + data + ", errorDescription:" + errorDescription + "}";
    }

}
