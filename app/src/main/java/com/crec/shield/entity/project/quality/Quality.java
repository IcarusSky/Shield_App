package com.crec.shield.entity.project.quality;

/**
 * Created by Administrator on 2018/8/18.
 */
public class Quality {
    private String circleNum;
    private Integer status;
//    private String progress;
    private String semi_bias;
    private String high_bias;
    private String adjacent_segment_dislocation;
    private String adjacent_ring_dislocation;

    public String getCircleNum() {
        return circleNum;
    }

    public void setCircleNum(String circleNum) {
        this.circleNum = circleNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

//    public String getProgress() {
//        return progress;
//    }
//
//    public void setProgress(String progress) {
//        this.progress = progress;
//    }

    public String getSemi_bias() {
        return semi_bias;
    }

    public void setSemi_bias(String semi_bias) {
        this.semi_bias = semi_bias;
    }

    public String getHigh_bias() {
        return high_bias;
    }

    public void setHigh_bias(String high_bias) {
        this.high_bias = high_bias;
    }

    public String getAdjacent_segment_dislocation() {
        return adjacent_segment_dislocation;
    }

    public void setAdjacent_segment_dislocation(String adjacent_segment_dislocation) {
        this.adjacent_segment_dislocation = adjacent_segment_dislocation;
    }

    public String getAdjacent_ring_dislocation() {
        return adjacent_ring_dislocation;
    }

    public void setAdjacent_ring_dislocation(String adjacent_ring_dislocation) {
        this.adjacent_ring_dislocation = adjacent_ring_dislocation;
    }
}
