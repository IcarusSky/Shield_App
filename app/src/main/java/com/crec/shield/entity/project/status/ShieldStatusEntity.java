package com.crec.shield.entity.project.status;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22.
 */

public class ShieldStatusEntity {
    private String shield_name;
    private String shield_status;
    private String shield_data_status;
    private String shield_data_time;
    private String driving_circle_num;
    private String horizontal_forward_point;
    private String horizontal_back_point;
    private String horizontal_trend;
    private String guide_progress;
    private String vertical_forward_point;
    private String vertical_back_point;
    private String vertical_trend;
//    private List<PointParameter> params;

    public String getShield_name() {
        return shield_name;
    }

    public void setShield_name(String shield_name) {
        this.shield_name = shield_name;
    }

    public String getShield_status() {
        return shield_status;
    }

    public void setShield_status(String shield_status) {
        this.shield_status = shield_status;
    }

    public String getShield_data_status() {
        return shield_data_status;
    }

    public void setShield_data_status(String shield_data_status) {
        this.shield_data_status = shield_data_status;
    }

    public String getShield_data_time() {
        return shield_data_time;
    }

    public void setShield_data_time(String shield_data_time) {
        this.shield_data_time = shield_data_time;
    }

    public String getDriving_circle_num() {
        return driving_circle_num;
    }

    public void setDriving_circle_num(String driving_circle_num) {
        this.driving_circle_num = driving_circle_num;
    }

    public String getHorizontal_forward_point() {
        return horizontal_forward_point;
    }

    public void setHorizontal_forward_point(String horizontal_forward_point) {
        this.horizontal_forward_point = horizontal_forward_point;
    }

    public String getHorizontal_back_point() {
        return horizontal_back_point;
    }

    public void setHorizontal_back_point(String horizontal_back_point) {
        this.horizontal_back_point = horizontal_back_point;
    }

    public String getHorizontal_trend() {
        return horizontal_trend;
    }

    public void setHorizontal_trend(String horizontal_trend) {
        this.horizontal_trend = horizontal_trend;
    }

    public String getGuide_progress() {
        return guide_progress;
    }

    public void setGuide_progress(String guide_progress) {
        this.guide_progress = guide_progress;
    }

    public String getVertical_forward_point() {
        return vertical_forward_point;
    }

    public void setVertical_forward_point(String vertical_forward_point) {
        this.vertical_forward_point = vertical_forward_point;
    }

    public String getVertical_back_point() {
        return vertical_back_point;
    }

    public void setVertical_back_point(String vertical_back_point) {
        this.vertical_back_point = vertical_back_point;
    }

    public String getVertical_trend() {
        return vertical_trend;
    }

    public void setVertical_trend(String vertical_trend) {
        this.vertical_trend = vertical_trend;
    }
/*    public List<PointParameter> getParams() {
        return params;
    }

    public void setParams(List<PointParameter> params) {
        this.params = params;
    }*/
}
