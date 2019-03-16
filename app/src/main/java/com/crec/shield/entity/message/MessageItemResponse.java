package com.crec.shield.entity.message;

import java.util.List;

public class MessageItemResponse {
    private Integer  code;
    private List<MessageItemData> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<MessageItemData> getData() {
        return data;
    }

    public void setData(List<MessageItemData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{ success:" + code + ", data:" + data + "}";
    }
}
