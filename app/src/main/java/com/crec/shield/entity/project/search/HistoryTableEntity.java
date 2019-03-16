package com.crec.shield.entity.project.search;

import android.provider.ContactsContract;

import java.util.Date;

public class HistoryTableEntity {

    private String projectId;  // 公司/项目编码
    private String projectName;  // 公司/项目名称

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
