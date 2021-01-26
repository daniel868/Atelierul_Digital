package com.example.atelierul_digital.week_7;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class QueryInterceptor implements Interceptor {
    private final String name;
    private final String value;

    public QueryInterceptor(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request originalRequest = chain.request();

        HttpUrl.Builder url = originalRequest.url().newBuilder();
        url.addQueryParameter(name, value);

        Request request = originalRequest.newBuilder()
                .url(url.build())
                .build();
        return chain.proceed(request);
    }
}
