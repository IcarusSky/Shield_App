package com.crec.shield.entity.common.AreaToLine;

import java.util.List;

public class CommonProjectEntity {

    private String projectname;
    private String projectcode;
    private List<CommonLineEntity> line;

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode;
    }

    public List<CommonLineEntity> getLine() {
        return line;
    }

    public void setLine(List<CommonLineEntity> line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "{ projectname:" + projectname + ", projectcode:" + projectcode + ", line:" + line + "}";
    }
}
