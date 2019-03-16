package com.crec.shield.service;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.crec.shield.ui.activity.SplashActivity;

public class DemoApplication extends Application {

    private static final String TAG = "GetuiSdkDemo";

    private static DemoHandler handler;
    public static SplashActivity pushActivity;

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkpushActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "DemoApplication onCreate");

        if (handler == null) {
            handler = new DemoHandler();
        }
    }

    public static void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    public static class DemoHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (pushActivity != null) {
                        payloadData.append((String) msg.obj);
                        payloadData.append("\n");
                        /*if (GetuiSdkpushActivity.tLogView != null) {
                            GetuiSdkpushActivity.tLogView.append(msg.obj + "\n");
                        }*/
                    }
                    break;

                case 1:
                    if (pushActivity != null) {
                       /* if (GetuiSdkpushActivity.tLogView != null) {
                            GetuiSdkpushActivity.tView.setText((String) msg.obj);
                        }*/
                    }
                    break;
            }
        }
    }
}
