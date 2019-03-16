package com.crec.shield.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.crec.shield.global.IstuaryGlobal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getStringArray(int resId) {
        return IstuaryGlobal.context.getResources().getStringArray(resId);
    }


    public static void runOnUIThread(Runnable runnable) {
        IstuaryGlobal.mainHandler.post(runnable);
    }


    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }


    public boolean checkPsw(String str) {
        return str.matches("^.*[a-zA-Z]+.*$") && str.matches("^.*[0-9]+.*$")
                && str.matches("^.*[/^/$/.//,;:'!@#%&/*/|/?/+/(/)/[/]/{/}]+.*$");
    }

    public boolean checkPswLength(String str) {
        return str.matches("^.{8,}$");
    }

    /**
     * 输入arraylist 转换成逗号隔开的字符串
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {
        String result = "";
        for (int i = 0, len = list.size(); i < len; i++) {
            if (len == 1) {
                result += list.get(i);
            } else if (i == len - 1) {
                result += list.get(i);
            } else {
                result += list.get(i) + ",";
            }
        }

        return result;
    }

    /**
     * 从assets中获取json数据
     * @param fileName
     * @param context
     * @return
     */
    public static String getJson(String fileName,Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}

