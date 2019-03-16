package com.crec.shield.entity.crec22.project.management;

import java.util.List;

public class ShieldDanttData {
    private List<Integer> normalInDate;      //正常掘进时长
    private List<Integer>  unusualStopDate;  //故障停机时长
    private List<Integer>  normalStopDate;   //正常停机时长
    private List<String>  monthNo;           //月份

    public List<Integer> getNormalInDate() {
        return normalInDate;
    }

    public void setNormalInDate(List<Integer> normalInDate) {
        this.normalInDate = normalInDate;
    }

    public List<Integer> getUnusualStopDate() {
        return unusualStopDate;
    }

    public void setUnusualStopDate(List<Integer> unusualStopDate) {
        this.unusualStopDate = unusualStopDate;
    }

    public List<Integer> getNormalStopDate() {
        return normalStopDate;
    }

    public void setNormalStopDate(List<Integer> normalStopDate) {
        this.normalStopDate = normalStopDate;
    }

    public List<String> getMonthNo() {
        return monthNo;
    }

    public void setMonthNo(List<String> monthNo) {
        this.monthNo = monthNo;
    }
}
