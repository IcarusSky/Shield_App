package com.crec.shield.weidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.TextView;

/**
 * Created by XieZhenyu on 2016/3/8 0008.
 */
public class CheckableTextView extends TextView implements Checkable {

    public CheckableTextView(Context context) {
        super(context);
    }

    public CheckableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean mChecked = false;
    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};


    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

}
