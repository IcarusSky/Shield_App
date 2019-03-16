package com.crec.shield.entity.overview.overview;

/**
 * Created by annie on 2018/8/22.
 */

public class PropelRankChildEntity {
    private String projectName; // 项目名称
    private int totalRing;  // 总环数
    private int todayRing;  // 今日掘进环数
    private int yesterdatRing;  // 昨日掘进环数

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getTotalRing() {
        return totalRing;
    }

    public void setTotalRing(int totalRing) {
        this.totalRing = totalRing;
    }

    public int getTodayRing() {
        return todayRing;
    }

    public void setTodayRing(int todayRing) {
        this.todayRing = todayRing;
    }

    public int getYesterdatRing() {
        return yesterdatRing;
    }

    public void setYesterdatRing(int yesterdatRing) {
        this.yesterdatRing = yesterdatRing;
    }
}
