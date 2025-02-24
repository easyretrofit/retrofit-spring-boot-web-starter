package io.github.easyretrofit.spring.boot.web;

import io.github.easyretrofit.core.RetrofitResourceContext;
import io.github.easyretrofit.core.extension.BaseInterceptor;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;


public class HttpLogInterceptor extends BaseInterceptor {
    private final HttpLoggingInterceptor httpLoggingInterceptor;

    public HttpLogInterceptor() {
        httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @Override
    protected Response executeIntercept(Chain chain) throws IOException {
        return httpLoggingInterceptor.intercept(chain);
    }

    @Override
    protected RetrofitResourceContext getInjectedRetrofitResourceContext() {
        return null;
    }

}
