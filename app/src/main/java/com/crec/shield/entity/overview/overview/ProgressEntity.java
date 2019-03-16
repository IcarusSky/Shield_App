package com.crec.shield.entity.overview.overview;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ProgressEntity {
    //当前环数
    private int currency_circle_num;
    //今日完成
    private int today_finished;
    //累计掘进
    private int total_num;
    //总长
    private int total_length;
    //预计出洞日期
    private String expect_date;

    public int getCurrency_circle_num() {
        return currency_circle_num;
    }

    public void setCurrency_circle_num(int currency_circle_num) {
        this.currency_circle_num = currency_circle_num;
    }

    public int getToday_finished() {
        return today_finished;
    }

    public void setToday_finished(int today_finished) {
        this.today_finished = today_finished;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public int getTotal_length() {
        return total_length;
    }

    public void setTotal_length(int total_length) {
        this.total_length = total_length;
    }

    public String getExpect_date() {
        return expect_date;
    }

    public void setExpect_date(String expect_date) {
        this.expect_date = expect_date;
    }

}
