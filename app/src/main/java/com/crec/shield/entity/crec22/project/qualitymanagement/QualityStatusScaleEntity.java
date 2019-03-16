package com.crec.shield.entity.crec22.project.qualitymanagement;

public class QualityStatusScaleEntity {
    String abnormal;//异常质量
    String normal;//正常质量
    String tubeDislocation;//管片错台
    String ringDislocation;//环片错台
    String vertical;//管片偏高
    String horizontal;//管片平偏

    public String getAbnormal() {
        return abnormal;
    }

    public void setAbnormal(String abnormal) {
        this.abnormal = abnormal;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getTubeDislocation() {
        return tubeDislocation;
    }

    public void setTubeDislocation(String tubeDislocation) {
        this.tubeDislocation = tubeDislocation;
    }

    public String getRingDislocation() {
        return ringDislocation;
    }

    public void setRingDislocation(String ringDislocation) {
        this.ringDislocation = ringDislocation;
    }

    public String getVertical() {
        return vertical;
    }

    public void setVertical(String vertical) {
        this.vertical = vertical;
    }

    public String getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(String horizontal) {
        this.horizontal = horizontal;
    }
}
