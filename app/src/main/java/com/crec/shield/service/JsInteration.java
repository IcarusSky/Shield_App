package com.crec.shield.service;

import android.support.v4.app.Fragment;
import android.webkit.JavascriptInterface;

/**
 * Created by annie on 2018/6/11.
 */

public class JsInteration {
    Fragment mContext;

    public JsInteration(Fragment c) {
        mContext = c;
    }

    @JavascriptInterface
    public void test(String timepoint)
    {
/*        if(mContext instanceof Second1Fragment) {
            ((Second1Fragment) mContext).initTimePointMsg(timepoint);
        }else if(mContext instanceof Second2Fragment) {
            ((Second2Fragment) mContext).initTimePointMsg(timepoint);
        }
        else if(mContext instanceof Second3Fragment) {
            ((Second3Fragment) mContext).initTimePointMsg(timepoint);
        }*/

    }
}
