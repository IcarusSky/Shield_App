package com.crec.shield.utils;

import android.webkit.WebSettings;

/**
 * Created by Administrator on 2018/8/20.
 */

public class WebViewUtil {
    public static final int EIGHT = 8;
    public static final int ONE_KB = 1024;

    public static void webSetting(WebSettings webSettings) {
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(EIGHT*ONE_KB*ONE_KB);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
    }
}
