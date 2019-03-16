package com.crec.shield.entity.common.AreaToLine;

import java.util.List;

public class AreaToLineResponse {
    public int code;
    public List<CommonAreaEntity> data;

    
    public int getCode() {
        return code;
    }

   
    public void setCode(int code) {
        this.code = code;
    }

    public List<CommonAreaEntity> getData() {
        return data;
    }

    public void setData(List<CommonAreaEntity> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "{ code:" + code + ", data:" + data + "}";
    }
}
