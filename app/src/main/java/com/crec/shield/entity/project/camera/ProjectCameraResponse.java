package com.crec.shield.entity.project.camera;

import java.util.Date;
import java.util.List;

public class ProjectCameraResponse {

    private Integer code;
    private List<ProjectCameraResp> data;

    public Integer getCode() { return code; }

    public void setCode(Integer code) { this.code = code; }

    public List<ProjectCameraResp> getData() {
        return data;
    }

    public void setData(List<ProjectCameraResp> data) {
        this.data = data;
    }
}
