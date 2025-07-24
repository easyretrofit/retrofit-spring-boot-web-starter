package io.github.easyretrofit.spring.boot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.easyretrofit.adapter.simplebody.ErrorParameter;

import java.io.IOException;

public class EasyRetrofitBodyCallAdapterException extends RuntimeException {

    public EasyRetrofitBodyCallAdapterException(ErrorParameter errorParameter) throws IOException {
        super("code:" + errorParameter.getResponse().code() + " message:" + errorParameter.getResponse().toString());
    }
}
