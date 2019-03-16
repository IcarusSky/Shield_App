package com.crec.shield.entity.project.progress;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ProgressEntity {

    private int currency_circle_num;  // 当前环数
    private int today_finished;  // 今日完成
    //private int total_num;  // 累计掘进
    private int percent;
    private int total_length;  // 总长
    private String expect_date;  //预计出洞日期

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

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
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