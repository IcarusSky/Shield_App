package com.crec.shield.entity.project.progress;

import java.util.List;

/**
 * Created by Administrator on 2018/8/24.
 */

public class Gantt {
    private List<Double> repair;
    private List<Double> working;
    private List<Double> packaging;
    private List<Double> total_ring;

    public List<Double> getTotal_ring() {
        return total_ring;
    }

    public void setTotal_ring(List<Double> total_ring) {
        this.total_ring = total_ring;
    }

    public List<Double> getRepair() {
        return repair;
    }

    public void setRepair(List<Double> repair) {
        this.repair = repair;
    }

    public List<Double> getWorking() {
        return working;
    }

    public void setWorking(List<Double> working) {
        this.working = working;
    }

    public List<Double> getPackaging() {
        return packaging;
    }

    public void setPackaging(List<Double> packaging) {
        this.packaging = packaging;
    }
}
