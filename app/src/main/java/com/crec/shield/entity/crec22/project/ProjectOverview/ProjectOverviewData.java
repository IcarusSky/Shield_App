package com.crec.shield.entity.crec22.project.projectoverview;

public class ProjectOverviewData {
    private String projectName;
    private String lineName;
    private String planStartEnd;//计划时间
    private String sectionMileage;//里程
    private String condition;//地址条件
    private String lineType;//线路类型
    private Tube tube;//管片规格
    private String tbmResource;//投入资源
    private String manager;//项目经理

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getPlanStartEnd() {
        return planStartEnd;
    }

    public void setPlanStartEnd(String planStartEnd) {
        this.planStartEnd = planStartEnd;
    }

    public String getSectionMileage() {
        return sectionMileage;
    }

    public void setSectionMileage(String sectionMileage) {
        this.sectionMileage = sectionMileage;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public Tube getTube() {
        return tube;
    }

    public void setTube(Tube tube) {
        this.tube = tube;
    }

    public String getTbmResource() {
        return tbmResource;
    }

    public void setTbmResource(String tbmResource) {
        this.tbmResource = tbmResource;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
