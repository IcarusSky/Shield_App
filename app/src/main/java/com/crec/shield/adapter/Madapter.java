package com.crec.shield.adapter;

import android.widget.BaseAdapter;

import java.util.List;

abstract class Madapter extends BaseAdapter {
    public abstract List<?> getItems();  //返回ListView的数据集
    public abstract void setSelectColor(int color);    //修改选中后item的颜色
    public abstract void setSelectedPosition(int selectedPosition); //设置选中项
    public abstract String getShowKey(int position , String key);//获取选中值，必须有这个方法。
}