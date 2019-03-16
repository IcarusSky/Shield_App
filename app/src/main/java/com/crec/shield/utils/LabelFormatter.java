package com.crec.shield.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

//通过LabelFormatter实现IAxisValueFormatter 重新定义方法用来构造X轴数据 可以显示字符串。
public class LabelFormatter implements IAxisValueFormatter {
    private final String[] mLabels;
    public LabelFormatter(String[] labels) {
        mLabels = labels;
    }
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        try {
            return mLabels[(int) value];
        } catch (Exception e) {
            e.printStackTrace();
            return mLabels[0];
        }
    }
}
