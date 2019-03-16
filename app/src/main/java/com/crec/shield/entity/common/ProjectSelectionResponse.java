package com.crec.shield.entity.common;

import java.util.List;

public class ProjectSelectionResponse {

    public Integer code;
    public List<ProjectSelectionStatus> data;

    public Integer getCode() { return code; }

    public void setCode(Integer code) { this.code = code; }

    public List<ProjectSelectionStatus> getData() {
        return data;
    }

    public void setData(List<ProjectSelectionStatus> data) {
        this.data = data;
    }



    @Override
    public String toString() {
        return "{ code:" + code + ", data:" + data + "}";
    }

}
