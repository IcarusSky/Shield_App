package com.crec.shield.entity.crec22.project.parameterlist;

import java.io.Serializable;

public class StatusData implements Serializable {
    private String point_name;
    private String tagvalue;
    private String unit;
    private String status;

    public String getPoint_name() {
        return point_name;
    }

    public void setPoint_name(String point_name) {
        this.point_name = point_name;
    }

    public String getTagvalue() {
        return tagvalue;
    }

    public void setTagvalue(String tagvalue) {
        this.tagvalue = tagvalue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
