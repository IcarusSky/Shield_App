package com.crec.shield.entity.project.risk;

public class RiskChartEntity {

    private String firstLevel;   // 1级风险源个数
    private String secondLevel;  // 2级风险源个数
    private String thirdLevel;   // 3级风险源个数
    private String fourthLevel;  // 4级风险源个数

    public String getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(String firstLevel) {
        this.firstLevel = firstLevel;
    }

    public String getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(String secondLevel) {
        this.secondLevel = secondLevel;
    }

    public String getThirdLevel() {
        return thirdLevel;
    }

    public void setThirdLevel(String thirdLevel) {
        this.thirdLevel = thirdLevel;
    }
}
