package com.crec.shield.entity.common.AreaToLine;

public class CommonLineEntity {

    private String linename;
    private String linecode;

    public String getLinename() {
        return linename;
    }

    public void setLinename(String linename) {
        this.linename = linename;
    }

    public String getLinecode() {
        return linecode;
    }

    public void setLinecode(String linecode) {
        this.linecode = linecode;
    }

    @Override
    public String toString() {
        return "{ linename:" + linename + ", linecode:" + linecode + "}";
    }
}
