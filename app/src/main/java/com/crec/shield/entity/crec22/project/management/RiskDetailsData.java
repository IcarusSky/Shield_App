package com.crec.shield.entity.crec22.project.management;

public class RiskDetailsData {
    private String riskCode; //
    private String riskName; //风险名称
    private int riskLevel;//风险等级
    private int startRegion;//开始环号
    private int endRegion;//结束环号
    private float risk;//风险里程
    private String riskMileage; //风险接近关系
    private int feedStatus;//反馈状态
    private String riskEvents; //风险事件说明
    private String RiskConsequence; //主要风险及后果
    private String riskPhoto;       //风险图片
    private String feedUser; //反馈人
    private String greateDate; //时间

    public String getRiskCode() {
        return riskCode;
    }

    public void setRiskCode(String riskCode) {
        this.riskCode = riskCode;
    }

    public String getRiskName() {
        return riskName;
    }

    public void setRiskName(String riskName) {
        this.riskName = riskName;
    }

    public int getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(int riskLevel) {
        this.riskLevel = riskLevel;
    }

    public int getStartRegion() {
        return startRegion;
    }

    public void setStartRegion(int startRegion) {
        this.startRegion = startRegion;
    }

    public int getEndRegion() {
        return endRegion;
    }

    public void setEndRegion(int endRegion) {
        this.endRegion = endRegion;
    }

    public float getRisk() {
        return risk;
    }

    public void setRisk(float risk) {
        this.risk = risk;
    }

    public String getRiskMileage() {
        return riskMileage;
    }

    public void setRiskMileage(String riskMileage) {
        this.riskMileage = riskMileage;
    }

    public int getFeedStatus() {
        return feedStatus;
    }

    public void setFeedStatus(int feedStatus) {
        this.feedStatus = feedStatus;
    }

    public String getRiskEvents() {
        return riskEvents;
    }

    public void setRiskEvents(String riskEvents) {
        this.riskEvents = riskEvents;
    }

    public String getRiskConsequence() {
        return RiskConsequence;
    }

    public void setRiskConsequence(String riskConsequence) {
        RiskConsequence = riskConsequence;
    }

    public String getFeedUser() {
        return feedUser;
    }

    public void setFeedUser(String feedUser) {
        this.feedUser = feedUser;
    }

    public String getGreateDate() {
        return greateDate;
    }

    public void setGreateDate(String greateDate) {
        this.greateDate = greateDate;
    }

    public String getRiskPhoto() {
        return riskPhoto;
    }

    public void setRiskPhoto(String riskPhoto) {
        this.riskPhoto = riskPhoto;
    }

}
