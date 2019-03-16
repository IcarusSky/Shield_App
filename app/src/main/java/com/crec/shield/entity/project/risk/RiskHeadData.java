package com.crec.shield.entity.project.risk;

import java.util.List;

public class RiskHeadData {
    List<RiskHeadCurrentRisks> currentRisks; // 当前风险数据
    List<RiskHeadReachRisks> reachRisks;  // 临近到达数据

    public List<RiskHeadCurrentRisks> getCurrentRisks() {
        return currentRisks;
    }

    public void setCurrentRisks(List<RiskHeadCurrentRisks> currentRisks) {
        this.currentRisks = currentRisks;
    }

    public List<RiskHeadReachRisks> getReachRisks() {
        return reachRisks;
    }

    public void setReachRisks(List<RiskHeadReachRisks> reachRisks) {
        this.reachRisks = reachRisks;
    }

}
