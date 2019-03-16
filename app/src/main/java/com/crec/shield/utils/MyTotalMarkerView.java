package com.crec.shield.utils;

import android.content.Context;
import android.widget.TextView;

import com.crec.shield.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

/**
 *自定义MyMarkerView
 */
public class MyTotalMarkerView extends MarkerView {

    private TextView tvContent;

    public MyTotalMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent= (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText("变化量：" + ce.getHigh()+"mm");
        } else {
            tvContent.setText("变化量：" + e.getY()+"mm");
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
