package io.github.easyretrofit.spring.boot.web;

import io.github.easyretrofit.adapter.simplebody.ErrorParameter;

public interface BodyCallAdapterErrorHandler<T> {

    T handleError(ErrorParameter errorParameter);
}
