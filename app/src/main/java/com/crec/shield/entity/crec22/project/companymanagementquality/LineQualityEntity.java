package com.crec.shield.entity.crec22.project.companymanagementquality;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class LineQualityEntity implements Serializable {
    private String project;
    private String line;
    private String level;
    private String line_id;


    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

}


