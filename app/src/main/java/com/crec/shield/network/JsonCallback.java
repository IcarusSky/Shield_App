package com.crec.shield.network;

import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

public abstract class JsonCallback<T> extends AbsCallback<T> {

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);

//        request.headers("header1", "HeaderValue1")//
//                .params("params1", "ParamsValue1")//
//                .params("token", "3215sdf13ad1f65asd4f3ads1f");
    }


    @Override
    public T convertSuccess(Response response) throws Exception {

        Type genType = getClass().getGenericSuperclass();

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        Type type = params[0];

        JsonReader jsonReader = new JsonReader(response.body().charStream());
        T data = Convert.fromJson(jsonReader, type);
        response.close();
        return data;
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);


    }
}