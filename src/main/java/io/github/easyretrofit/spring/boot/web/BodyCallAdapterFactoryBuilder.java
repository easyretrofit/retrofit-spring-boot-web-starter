package io.github.easyretrofit.spring.boot.web;


import io.github.easyretrofit.adapter.simplebody.SimpleBodyCallAdapterFactory;
import io.github.easyretrofit.core.builder.BaseCallAdapterFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.CallAdapter;

@Component
public class BodyCallAdapterFactoryBuilder extends BaseCallAdapterFactoryBuilder {

    @Autowired(required = false)
    private BodyCallAdapterErrorHandler errorHandler;

    @Override
    public CallAdapter.Factory buildCallAdapterFactory() {
        return SimpleBodyCallAdapterFactory.create(errorParameter -> {
            if (errorHandler != null) {
                return errorHandler.handleError(errorParameter);
            }
            throw new EasyRetrofitBodyCallAdapterException(errorParameter);
        });
    }
}
