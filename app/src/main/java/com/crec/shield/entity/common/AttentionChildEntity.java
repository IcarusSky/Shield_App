package com.crec.shield.entity.common;

import java.util.List;

public class AttentionChildEntity {

    private String id;//id
    private String project_id;
    private String line; // 项目片区
    private String type; // 项目名称
    private Integer today_rings; // 今日环数
    private Integer persent_rings; // 当前环数
    private Integer total_rings; // 总环数

    public AttentionChildEntity() {
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public AttentionChildEntity(String id, String line, String type, int today_rings, int persent_rings, int total_rings) {
        this.id = id;
        this.line = line;
        this.type = type;

        this.today_rings = today_rings;
        this.persent_rings = persent_rings;
        this.total_rings = total_rings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() { return type; }

    public void setType(String type) {
        this.type = type;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Integer getToday_rings() {
        return today_rings;
    }

    public void setToday_rings(Integer today_rings) {
        this.today_rings = today_rings;
    }

    public Integer getPersent_rings() {
        return persent_rings;
    }

    public void setPersent_rings(Integer persent_rings) {
        this.persent_rings = persent_rings;
    }

    public Integer getTotal_rings() {
        return total_rings;
    }

    public void setTotal_rings(Integer total_rings) {
        this.total_rings = total_rings;
    }
}
