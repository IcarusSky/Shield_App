package com.crec.shield.entity.overview.overview;

/**
 * Created by annie on 2018/8/23.
 */

public class RiskRankChildEntity {

    private String projectName;
    private int level1;
    private int level2;
    private int level3;
    private int todayRing;
    private int yesterdatRing;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getLevel1() {
        return level1;
    }

    public void setLevel1(int level1) {
        this.level1 = level1;
    }

    public int getLevel2() {
        return level2;
    }

    public void setLevel2(int level2) {
        this.level2 = level2;
    }

    public int getLevel3() {
        return level3;
    }

    public void setLevel3(int level3) {
        this.level3 = level3;
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
