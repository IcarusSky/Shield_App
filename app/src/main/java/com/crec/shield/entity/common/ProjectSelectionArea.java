package com.crec.shield.entity.common;

import java.util.List;

public class ProjectSelectionArea {

    private String areaname; // 片区名称
    private String areaid; // 片区编码
    private List<ProjectSelectionEntity> project; // 片区下的项目组


    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getareaid() {
        return areaid;
    }

    public void setareaid(String areaid) {
        this.areaid = areaid;
    }

    public List<ProjectSelectionEntity> getProject() {
        return project;
    }

    public void setProject(List<ProjectSelectionEntity> project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "{ areaname:" + areaname + ", areaid:" + areaid + ", project:" + project + "}";
    }



}
