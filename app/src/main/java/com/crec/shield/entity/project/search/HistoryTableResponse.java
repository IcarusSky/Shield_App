package com.crec.shield.entity.project.search;

import java.util.List;

public class HistoryTableResponse {

    public Integer code;
    public List<HistoryTableEntity> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<HistoryTableEntity> getData() {
        return data;
    }

    public void setData(List<HistoryTableEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{ code:" + code + ", data:" + data + "}";
    }

}
