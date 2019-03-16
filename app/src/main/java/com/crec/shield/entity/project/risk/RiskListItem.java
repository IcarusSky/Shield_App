//package com.crec.shield.entity.project.risk;
//
//import com.bin.david.form.annotation.SmartColumn;
//import com.bin.david.form.annotation.SmartTable;
//
///**
// * Created by Administrator on 2018/8/22.
// */
//@SmartTable(name = "风险源列表")
//public class RiskListItem {
//
//    @SmartColumn(id = 1, name = "开始")
//    private Integer start_num;
//    @SmartColumn(id = 2, name = "结束")
//    private Integer end_num;
//    @SmartColumn(id = 3, name = "等级")
//    private String risk_level;
//    @SmartColumn(id = 4, name = "类型")
//    private String risk_content;
//    @SmartColumn(id = 5, name = "标地")
//    private String cross_building;
//    @SmartColumn(id = 6, name = "状态")
//    private String status;
//
//    public RiskListItem(int start_num,int end_num, String risk_level,String risk_content,String cross_building, String status) {
//        this.start_num = start_num;
//        this.end_num = end_num;
//        this.risk_level = risk_level;
//        this.risk_content = risk_content;
//        this.cross_building = cross_building;
//        this.status = status;
//    }
//
//
//    public Integer getStart_num() {
//        return start_num;
//    }
//
//    public void setStart_num(int start_num) {
//        this.start_num = start_num;
//    }
//
//    public Integer getEnd_num() {
//        return end_num;
//    }
//
//    public void setEnd_num(int end_num) {
//        this.end_num = end_num;
//    }
//
//    public String getRisk_level() {
//        return risk_level;
//    }
//
//    public void setRisk_level(String risk_level) {
//        this.risk_level = risk_level;
//    }
//
//    public String getRisk_content() {
//        return risk_content;
//    }
//
//    public void setRisk_content(String risk_content) {
//        this.risk_content = risk_content;
//    }
//
//    public String getCross_building() {
//        return cross_building;
//    }
//
//    public void setCross_building(String cross_building) {
//        this.cross_building = cross_building;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//}
