package com.muc.store.util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {

    private OkHttpClient http;
    private Request request;
    private String url;

    public interface HttpLister {

        void onSuccess(String body);

        void onError(String error);
    }

    public HttpLister mHttpLister;

    public HttpUtils(String url) {
        http = new OkHttpClient();
        this.url = url;
    }

    public void setHttpListener(HttpLister listener) {
        this.mHttpLister = listener;
    }

    public HttpUtils get() {
        request = new Request.Builder()
                .url(url)
                .build();
        Call call = http.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String body = response.body().string();
                mHttpLister.onSuccess(body);
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mHttpLister.onError(e.getMessage());
            }
        });
        return HttpUtils.this;
    }

    /**
     * 使用post方式提交数据
     * @param body 使用FormBody.Builder传入
     * @return HttpUtils
     */
    public HttpUtils post(FormBody.Builder body) {
        request = new Request.Builder()
                .url(url)
                .post(body.build())
                .build();
        Call call = http.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                mHttpLister.onSuccess(response.body().string());
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mHttpLister.onError(e.getMessage());
            }

        });
        return HttpUtils.this;
    }

}
