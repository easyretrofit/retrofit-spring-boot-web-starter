package io.github.easyretrofit.spring.boot.web;

import io.github.easyretrofit.adapter.simplebody.ErrorParameter;

public class EasyRetrofitBodyCallAdapterException extends RuntimeException {

    public EasyRetrofitBodyCallAdapterException(ErrorParameter errorParameter) {
        super("code:" + errorParameter.getResponse().code() + " message:" + errorParameter.getResponse().message());
    }
}
