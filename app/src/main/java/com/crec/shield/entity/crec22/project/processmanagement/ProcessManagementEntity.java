package com.crec.shield.entity.crec22.project.processmanagement;

import java.util.List;

public class ProcessManagementEntity {
    private List<ProcessManagementParamter> yesterdayRanking;
    private List<ProcessManagementParamter> todayRanking;
    private List<ProcessManagementParamter> sevenRanking;
    private List<ProcessManagementParamter> thirtyRanking;

    public List<ProcessManagementParamter> getYesterdayRanking() {
        return yesterdayRanking;
    }

    public void setYesterdayRanking(List<ProcessManagementParamter> yesterdayRanking) {
        this.yesterdayRanking = yesterdayRanking;
    }

    public List<ProcessManagementParamter> getTodayRanking() {
        return todayRanking;
    }

    public void setTodayRanking(List<ProcessManagementParamter> todayRanking) {
        this.todayRanking = todayRanking;
    }

    public List<ProcessManagementParamter> getSevenRanking() {
        return sevenRanking;
    }

    public void setSevenRanking(List<ProcessManagementParamter> sevenRanking) {
        this.sevenRanking = sevenRanking;
    }

    public List<ProcessManagementParamter> getThirtyRanking() {
        return thirtyRanking;
    }

    public void setThirtyRanking(List<ProcessManagementParamter> thirtyRanking) {
        this.thirtyRanking = thirtyRanking;
    }
}
