package com.crec.shield.entity.crec22.project.unnormalworkcondition;

public class UnnormalHeadCurrentUnnormal {
    public String unusualId;
    public String current_unusual_detail;    //": "(95-429)(Ⅲ级)", //当前异常工况基本信息（盾构机当前处于）
    public String current_unusual_cross;     //": "盾尾密封失效", //当前异常工况名称
    public String current_circle_num;        //160", //当前环数
    public String current_expect_date;       //2019-03-10", //预计通过时间

    public String getUnusualId() {
        return unusualId;
    }

    public void setUnusualId(String unusualId) {
        this.unusualId = unusualId;
    }

    public String getCurrent_unusual_detail() {
        return current_unusual_detail;
    }

    public void setCurrent_unusual_detail(String current_unusual_detail) {
        this.current_unusual_detail = current_unusual_detail;
    }

    public String getCurrent_unusual_cross() {
        return current_unusual_cross;
    }

    public void setCurrent_unusual_cross(String current_unusual_cross) {
        this.current_unusual_cross = current_unusual_cross;
    }

    public String getCurrent_circle_num() {
        return current_circle_num;
    }

    public void setCurrent_circle_num(String current_circle_num) {
        this.current_circle_num = current_circle_num;
    }

    public String getCurrent_expect_date() {
        return current_expect_date;
    }

    public void setCurrent_expect_date(String current_expect_date) {
        this.current_expect_date = current_expect_date;
    }
}
