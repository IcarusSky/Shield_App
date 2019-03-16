package com.crec.shield.entity.crec22.project.unnormalworkcondition;

public class UnnormalListItemEntity {
    private Integer start_num;        //": 564,
    private Integer end_num;         //": 586,
    private String unusual_level;     //": "Ⅱ 级",
    private String unusual_content;    //": "钟宅地下通道，基础结构为条形基础，基础埋深4.7米，机房基础埋深6.05，该通道基础距区间隧道顶部4.06米。",
    private String cross_building;      //": "钟宅地下通道",
    private String status;               //": "已解决"

    public Integer getStart_num() {
        return start_num;
    }

    public void setStart_num(Integer start_num) {
        this.start_num = start_num;
    }

    public Integer getEnd_num() {
        return end_num;
    }

    public void setEnd_num(Integer end_num) {
        this.end_num = end_num;
    }

    public String getUnusual_level() {
        return unusual_level;
    }

    public void setUnusual_level(String unusual_level) {
        this.unusual_level = unusual_level;
    }

    public String getUnusual_content() {
        return unusual_content;
    }

    public void setUnusual_content(String unusual_content) {
        this.unusual_content = unusual_content;
    }

    public String getCross_building() {
        return cross_building;
    }

    public void setCross_building(String cross_building) {
        this.cross_building = cross_building;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
