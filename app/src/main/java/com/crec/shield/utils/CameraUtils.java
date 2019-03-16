package com.crec.shield.utils;

import android.util.Log;

import com.hikvision.netsdk.HCNetSDK;

public class CameraUtils {
    private static final String TAG = "CameraUtils";
    public static boolean initeSdk() {
        // init net sdk
        if (!HCNetSDK.getInstance().NET_DVR_Init()) {
            Log.e(TAG, "HCNetSDK init is failed!");
            return false;
        }
        HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, "/mnt/sdcard/sdklog/",
                true);

        return true;
    }
}
