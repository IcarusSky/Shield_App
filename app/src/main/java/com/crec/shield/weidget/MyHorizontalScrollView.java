package com.crec.shield.weidget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * @author hahaliu
 * @ClassName MyHorizontalScrollView
 * @Description
 * @date 2018-10-08
 */
public class MyHorizontalScrollView extends HorizontalScrollView {
    public MyHorizontalScrollView(Context context) {
        super(context);
    }

    public MyHorizontalScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHorizontalScrollView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private float lastX, lastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        boolean intercept = super.onInterceptTouchEvent(e);

        switch (e.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = e.getX();
                lastY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 只要横向大于竖向，就拦截掉事件。
                // 部分机型点击事件（slopx==slopy==0），会触发MOVE事件。
                // 所以要加判断(slopX > 0 || sloy > 0)
                float slopX = Math.abs(e.getX() - lastX);
                float slopY = Math.abs(e.getY() - lastY);
                if((slopX > 0 || slopY > 0) && slopX >= slopY){
                    requestDisallowInterceptTouchEvent(true);
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        return intercept;
    }
}
