package com.crec.shield.entity.crec22.project.management;

public class StatusScale {
    private int driving;    //  （掘进）
    private int trouble;    //  （故障）
    private int close;      //  （停机）

    public int getDriving() {
        return driving;
    }

    public void setDriving(int driving) {
        this.driving = driving;
    }

    public int getTrouble() {
        return trouble;
    }

    public void setTrouble(int trouble) {
        this.trouble = trouble;
    }

    public int getClose() {
        return close;
    }

    public void setClose(int close) {
        this.close = close;
    }
}
