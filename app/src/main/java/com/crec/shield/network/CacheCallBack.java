package com.crec.shield.network;

import android.app.Activity;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by diaokaibin@gmail.com on 2017/6/22.
 */

public class CacheCallBack<T> extends DialogCallback<T> {

    public CacheCallBack(Activity activity) {
        super(activity);
    }


    @Override
    public void onSuccess(T t, Call call, Response response) {

    }
}
