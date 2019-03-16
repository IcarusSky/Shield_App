package com.crec.shield.entity.common;

import java.util.List;

public class AttentionParentEntity {

    private String id;//id
    private String area; // 项目片区
    private String project; // 项目名称
    private String status; // 状态
    private Integer isAttention; //是否关注
    private List<AttentionChildEntity> lines; // 项目下的线路

    public AttentionParentEntity() {
    }

    public AttentionParentEntity(String id, String area,String project, String status,int isAttention, List<AttentionChildEntity> lines) {
        this.id = id;
        this.area = area;
        this.project= project;
        this.status = status;
        this.isAttention = isAttention;
        this.lines = lines;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea() { return area; }

    public void setArea(String area) { this.area = area; }

    public String getProject() { return project; }

    public void setProject(String project) { this.project = project; }

    public Integer getIsAttention() { return isAttention; }

    public void setIsAttention(Integer isAttention) {
        this.isAttention = isAttention;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AttentionChildEntity> getLines() {
        return lines;
    }

    public void setLines(List<AttentionChildEntity> lines) {
        this.lines = lines;
    }


}
