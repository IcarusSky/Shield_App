package com.crec.shield.entity.crec22.project.projectdevice;

import java.util.List;

public class Data {
    private int inProject;
    private int inLine;
    private int totalLong;
    private int finish;
    private int tbmNumber;
    private int free;
    private int lease;
    private int workNow;
    private List<FinishProject> finishProject;

    public int getInProject() {
        return inProject;
    }

    public void setInProject(int inProject) {
        this.inProject = inProject;
    }

    public int getInLine() {
        return inLine;
    }

    public void setInLine(int inLine) {
        this.inLine = inLine;
    }

    public int getTotalLong() {
        return totalLong;
    }

    public void setTotalLong(int totalLong) {
        this.totalLong = totalLong;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public int getTbmNumber() {
        return tbmNumber;
    }

    public void setTbmNumber(int tbmNumber) {
        this.tbmNumber = tbmNumber;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public int getLease() {
        return lease;
    }

    public void setLease(int lease) {
        this.lease = lease;
    }

    public int getWorkNow() {
        return workNow;
    }

    public void setWorkNow(int workNow) {
        this.workNow = workNow;
    }

    public List<FinishProject> getFinishProject() {
        return finishProject;
    }

    public void setFinishProject(List<FinishProject> finishProject) {
        this.finishProject = finishProject;
    }
}
