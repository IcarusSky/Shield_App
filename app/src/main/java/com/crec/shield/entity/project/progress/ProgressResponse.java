package com.crec.shield.entity.project.progress;

public class ProgressResponse {
    private Integer code ;
    private  ProgressEntity data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ProgressEntity getData() {
        return data;
    }

    public void setData(ProgressEntity data) {
        this.data = data;
    }
}
