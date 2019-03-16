package com.crec.shield.entity.common;

import java.util.List;

public class ProjectSelectionStatus {
    private String status; //状态
    private List<ProjectSelectionArea> area; // 某一种状态的所有项目

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProjectSelectionArea> getArea() {
        return area;
    }

    public void setArea(List<ProjectSelectionArea> area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "{ status:" + status + ", area:" + "}";
    }
}
