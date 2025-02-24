package io.github.easyretrofit.spring.boot.web;

import io.github.easyretrofit.converter.basetype.BaseTypeConverterFactory;
import io.github.easyretrofit.core.builder.BaseConverterFactoryBuilder;
import retrofit2.Converter;

public class BodyBaseTypeConvertFactoryBuilder extends BaseConverterFactoryBuilder {
    @Override
    public Converter.Factory buildConverterFactory() {
        return BaseTypeConverterFactory.create();
    }
}
