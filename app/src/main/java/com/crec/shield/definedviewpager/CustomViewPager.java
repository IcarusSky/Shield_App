package com.crec.shield.definedviewpager;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CustomViewPager extends ViewPager implements Serializable {
    private int height = 0;
    private int currPosition;

    private HashMap<Integer, View> mChildViews = new LinkedHashMap<>();

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mChildViews.size() > currPosition){
            View child = mChildViews.get(currPosition);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            height = child.getMeasuredHeight();
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /** 通过设置LayoutParams来控制子View的高度 即重置当前位置下标（fragment）的高度*/
    public void resetHeight(int position){
        this.currPosition = position;
        if (mChildViews.size() > currPosition){
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
            if (layoutParams == null){
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            }

            layoutParams.height = height;

            setLayoutParams(layoutParams);
        }
    }

    public void setObjectForView(View view, int position){
        mChildViews.put(position, view);
    }



}

