package com.crec.shield.entity.overview.overview;

/**
 * Created by annie on 2018/8/22.
 */

public class PropelRankResponse {
    public String code;
    public PropelRankEntity data;


    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public PropelRankEntity getData() {
        return data;
    }

    public void setData(PropelRankEntity data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "{ code:" + code + ", data:" + data + "}";
    }
}
