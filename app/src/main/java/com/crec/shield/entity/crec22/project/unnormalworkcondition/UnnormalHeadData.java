package com.crec.shield.entity.crec22.project.unnormalworkcondition;
import java.util.List;

public class UnnormalHeadData {

    List<UnnormalHeadCurrentUnnormal> currentunusuals;//当前异常数据
    List<UnnormalHeadReachUnnormal> reachunusuals; //临近异常数据


    public List<UnnormalHeadCurrentUnnormal> getCurrentunusuals() {
        return currentunusuals;
    }

    public void setCurrentunusuals(List<UnnormalHeadCurrentUnnormal> currentunusuals) {
        this.currentunusuals = currentunusuals;
    }

    public List<UnnormalHeadReachUnnormal> getReachunusuals() {
        return reachunusuals;
    }

    public void setReachunusuals(List<UnnormalHeadReachUnnormal> reachunusuals) {
        this.reachunusuals = reachunusuals;
    }
}
