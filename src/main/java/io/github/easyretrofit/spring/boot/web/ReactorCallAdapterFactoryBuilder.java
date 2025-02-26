package io.github.easyretrofit.spring.boot.web;

import io.github.easyretrofit.adapter.reactor.ReactorCallAdapterFactory;
import io.github.easyretrofit.core.builder.BaseCallAdapterFactoryBuilder;
import retrofit2.CallAdapter;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public class ReactorCallAdapterFactoryBuilder extends BaseCallAdapterFactoryBuilder {

    @Override
    public CallAdapter.Factory buildCallAdapterFactory() {
        return ReactorCallAdapterFactory.create();
    }
}
