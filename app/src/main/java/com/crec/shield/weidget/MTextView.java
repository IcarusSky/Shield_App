package com.crec.shield.weidget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018/10/31.
 */

public class MTextView extends android.support.v7.widget.AppCompatTextView {

    public MTextView(Context context) {
        super(context);
    }

    public MTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

}