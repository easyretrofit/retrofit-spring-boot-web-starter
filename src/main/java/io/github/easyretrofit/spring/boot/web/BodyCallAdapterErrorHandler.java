package io.github.easyretrofit.spring.boot.web;

import io.github.easyretrofit.adapter.simplebody.ErrorParameter;

public interface BodyCallAdapterErrorHandler {

    void handleError(ErrorParameter errorParameter);
}
