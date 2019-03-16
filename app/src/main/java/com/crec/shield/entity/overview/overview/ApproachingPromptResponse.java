package com.crec.shield.entity.overview.overview;

import java.util.List;

public class ApproachingPromptResponse {
    private Integer code;
    private List<ApproachingPromptEntity> data;

    public Integer getCode() { return code; }

    public void setCode(Integer code) { this.code = code; }

    public List<ApproachingPromptEntity> getData() {
        return data;
    }

    public void setData(List<ApproachingPromptEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{ code:" + code + ", data:" + data + "}";
    }
}
