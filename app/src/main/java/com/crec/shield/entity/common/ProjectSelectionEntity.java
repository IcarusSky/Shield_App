package com.crec.shield.entity.common;

public class ProjectSelectionEntity {

    private String projectname; // 项目名称
    private String projectid; // 项目编码

    public ProjectSelectionEntity() {
    }

    public ProjectSelectionEntity( String projectname, String projectid) {
        this.projectname = projectname;
        this.projectid = projectid;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    @Override
    public String toString() {
        return "{ projectname:" + projectname + ", projectid:" + projectid + "}";
    }

}
