package io.github.easyretrofit.spring.boot.web;

import io.github.easyretrofit.core.builder.BaseCallAdapterFactoryBuilder;
import retrofit2.CallAdapter;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public class Rxjava3CallAdapterFactoryBuilder extends BaseCallAdapterFactoryBuilder {

    @Override
    public CallAdapter.Factory buildCallAdapterFactory() {
        return RxJava3CallAdapterFactory.create();
    }
}
