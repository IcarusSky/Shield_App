package com.crec.shield.entity.crec22.project.parameterlist;

import java.util.List;

public class ParameterListEntity {
    private String sunSystem;
    public List<StatusData>statusData;

    public String getSunSystem() {
        return sunSystem;
    }

    public void setSunSystem(String sunSystem) {
        this.sunSystem = sunSystem;
    }

    public List<StatusData> getStatusData() {
        return statusData;
    }

    public void setStatusData(List<StatusData> statusData) {
        this.statusData = statusData;
    }
}
