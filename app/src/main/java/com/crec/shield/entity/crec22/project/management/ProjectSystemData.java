package com.crec.shield.entity.crec22.project.management;

public class ProjectSystemData
{

    private RingRatio ringRatio;          //  环数比例
    private int presentRings ;     //  盾构机当前环数
    private int todayFinish;       //  今日完成环数
    private int yesterdayFinish;  //   昨日完成环数
    private int sevenFinish;       //  七日完成环数
    private int currentRisk;        //  当前风险源
    private int nearRisk;           //  临近风险源
    private int totalRisk;          //  总风险源
    private int currentUnusual;    //  当前异常工况
    private int nearUnusual;       //  临近异常工况
    private int totalUnusual;      //  总异常工况
    private StatusScale statusScale; // 设备状态比例饼图
    private String deviceName;      //  设备名称
    private int todayStatus;       //  今日状态 (0：掘进，1：拼装，2：停机)
    private int totalNumber;       //  累计掘进环数
    private int normalNumber;      //  正常环数
    private int abnormalNumber;    //  异常环数

    public RingRatio getRingRatio() {

        return ringRatio;
    }

    public void setRingRatio(RingRatio ringRatio) {
        this.ringRatio = ringRatio;
    }

    public int getPresentRings() {
        return presentRings;
    }

    public void setPresentRings(int presentRings) {
        this.presentRings = presentRings;
    }

    public int getTodayFinish() {
        return todayFinish;
    }

    public void setTodayFinish(int todayFinish) {
        this.todayFinish = todayFinish;
    }

    public int getYesterdayFinish() {
        return yesterdayFinish;
    }

    public void setYesterdayFinish(int yesterdayFinish) {
        this.yesterdayFinish = yesterdayFinish;
    }

    public int getSevenFinish() {
        return sevenFinish;
    }

    public void setSevenFinish(int sevenFinish) {
        this.sevenFinish = sevenFinish;
    }

    public int getCurrentRisk() {
        return currentRisk;
    }

    public void setCurrentRisk(int currentRisk) {
        this.currentRisk = currentRisk;
    }

    public int getNearRisk() {
        return nearRisk;
    }

    public void setNearRisk(int nearRisk) {
        this.nearRisk = nearRisk;
    }

    public int getTotalRisk() {
        return totalRisk;
    }

    public void setTotalRisk(int totalRisk) {
        this.totalRisk = totalRisk;
    }

    public int getCurrentUnusual() {
        return currentUnusual;
    }

    public void setCurrentUnusual(int currentUnusual) {
        this.currentUnusual = currentUnusual;
    }

    public int getNearUnusual() {
        return nearUnusual;
    }

    public void setNearUnusual(int nearUnusual) {
        this.nearUnusual = nearUnusual;
    }

    public int getTotalUnusual() {
        return totalUnusual;
    }

    public void setTotalUnusual(int totalUnusual) {
        this.totalUnusual = totalUnusual;
    }

    public StatusScale getStatusScale() {
        return statusScale;
    }

    public void setStatusScale(StatusScale statusScale) {
        this.statusScale = statusScale;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getTodayStatus() {
        return todayStatus;
    }

    public void setTodayStatus(int todayStatus) {
        this.todayStatus = todayStatus;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public int getNormalNumber() {
        return normalNumber;
    }

    public void setNormalNumber(int normalNumber) {
        this.normalNumber = normalNumber;
    }

    public int getAbnormalNumber() {
        return abnormalNumber;
    }

    public void setAbnormalNumber(int abnormalNumber) {
        this.abnormalNumber = abnormalNumber;
    }



}
