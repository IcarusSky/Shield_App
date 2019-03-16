package com.crec.shield.entity.crec22.project.management;

public class ProjectSystemResponse {
    private Integer code;
    private ProjectSystemData data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ProjectSystemData getData() {
        return data;
    }

    public void setData(ProjectSystemData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{ code:" + code + ", data:" + data + "}";
    }
}
