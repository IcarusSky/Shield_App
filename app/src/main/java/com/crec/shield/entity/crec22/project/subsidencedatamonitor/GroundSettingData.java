package com.crec.shield.entity.crec22.project.subsidencedatamonitor;

import java.util.List;

public class GroundSettingData {
    private List<String> code;      //监测点编号
    private List<Integer>  ringNo;  //环号
    private List<Float>  danger;   //预警线
    private List<Float>  totalchange; //累计变化量
    private List<Float>  nowchange; //本次变化量
    private List<Float>  changeRate;   //沉降速率

    public List<String> getCode() {
        return code;
    }

    public void setCode(List<String> code) {
        this.code = code;
    }

    public List<Integer> getRingNo() {
        return ringNo;
    }

    public void setRingNo(List<Integer> ringNo) {
        this.ringNo = ringNo;
    }

    public List<Float> getDanger() {
        return danger;
    }

    public void setDanger(List<Float> danger) {
        this.danger = danger;
    }

    public List<Float> getTotalchange() {
        return totalchange;
    }

    public void setTotalchange(List<Float> totalchange) {
        this.totalchange = totalchange;
    }

    public List<Float> getNowchange() {
        return nowchange;
    }

    public void setNowchange(List<Float> nowchange) {
        this.nowchange = nowchange;
    }

    public List<Float> getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(List<Float> changeRate) {
        this.changeRate = changeRate;
    }
}
