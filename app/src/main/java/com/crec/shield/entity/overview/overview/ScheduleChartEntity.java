package com.crec.shield.entity.overview.overview;

public class ScheduleChartEntity {

    private Integer todayRings; // 今日掘进总环数
    private Integer cumulativeRings; // 累计掘进总环数
    private Integer totalRings; // 计划总环数

    public Integer getTodayRings() {
        return todayRings;
    }

    public void setTodayRings(Integer todayRings) {
        this.todayRings = todayRings;
    }

    public Integer getCumulativeRings() {
        return cumulativeRings;
    }

    public void setCumulativeRings(Integer cumulativeRings) {
        this.cumulativeRings = cumulativeRings;
    }

    public Integer getTotalRings() {
        return totalRings;
    }

    public void setTotalRings(Integer totalRings) {
        this.totalRings = totalRings;
    }
}
