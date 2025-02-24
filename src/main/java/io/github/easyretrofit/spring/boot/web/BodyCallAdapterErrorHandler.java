package io.github.easyretrofit.spring.boot.web;

import io.github.easyretrofit.adapter.simplebody.ErrorParameter;

public interface BodyCallAdapterErrorHandler {

    Object handleError(ErrorParameter errorParameter);
}
