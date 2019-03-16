package com.crec.shield.entity.crec22.project.projectlistforcity;

import java.util.List;

public class ProjectListForCityData {
    private String city;
    private List<ProjectListParam> projectList;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<ProjectListParam> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectListParam> projectList) {
        this.projectList = projectList;
    }
}
