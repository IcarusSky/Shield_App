package com.crec.shield.weidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.crec.shield.R;

/**
 * Created by hahaliu on 2018/9/27.
 */

public class DashLineView extends View {
    private Paint mPaint;
    private Path mPath;
    public DashLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(context.getResources().getColor(R.color.line_gray));
        // 需要加上这句，否则画不出东西
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setPathEffect(new DashPathEffect(new float[] {15, 5}, 0));
        mPath = new Path();
    }
        @Override protected void onDraw(Canvas canvas) {
        int centerY = getWidth() / 2;
        mPath.reset();
        mPath.moveTo(centerY, 0);
        mPath.lineTo(centerY, getHeight());
        canvas.drawPath(mPath, mPaint); }

}
