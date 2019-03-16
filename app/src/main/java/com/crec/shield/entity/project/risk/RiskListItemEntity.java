package com.crec.shield.entity.project.risk;

/**
 * Created by Administrator on 2018/8/23.
 */

public class RiskListItemEntity {
    private Integer start_num;
    private Integer end_num;
    private String risk_level;
    private String risk_content;
    private String cross_building;
    private String status;

    public Integer getStart_num() {
        return start_num;
    }

    public void setStart_num(int start_num) {
        this.start_num = start_num;
    }

    public Integer getEnd_num() {
        return end_num;
    }

    public void setEnd_num(int end_num) {
        this.end_num = end_num;
    }

    public String getRisk_level() {
        return risk_level;
    }

    public void setRisk_level(String risk_level) {
        this.risk_level = risk_level;
    }

    public String getRisk_content() {
        return risk_content;
    }

    public void setRisk_content(String risk_content) {
        this.risk_content = risk_content;
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
