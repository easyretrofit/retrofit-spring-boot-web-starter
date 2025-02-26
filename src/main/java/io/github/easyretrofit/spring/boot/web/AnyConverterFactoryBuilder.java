package io.github.easyretrofit.spring.boot.web;

import io.github.easyretrofit.core.builder.BaseConverterFactoryBuilder;
import io.github.easyretrofit.spring.boot.web.converter.JsonConverter;
import io.github.easyretrofit.spring.boot.web.converter.ProtocolConverter;
import io.github.easyretrofit.spring.boot.web.converter.XmlConverter;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.jaxb.JaxbConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.converter.protobuf.ProtoConverterFactory;
import retrofit2.converter.wire.WireConverterFactory;

public class AnyConverterFactoryBuilder extends BaseConverterFactoryBuilder {
    @Override
    public Converter.Factory buildConverterFactory() {
        return new AnyConverters.QualifiedTypeConverterFactory.Builder()
                .add(AnyConverters.Json.class, JsonConverter.JACKSON, JacksonConverterFactory.create())
                .add(AnyConverters.Json.class, JsonConverter.GSON, GsonConverterFactory.create())
                .add(AnyConverters.Json.class, JsonConverter.MOSHI, MoshiConverterFactory.create())
                .add(AnyConverters.Xml.class, XmlConverter.JAXB, JaxbConverterFactory.create())
//                .add(AnyConverters.Xml.class, XmlConverter.JAXB3, retrofit2.converter.jaxb3.JaxbConverterFactory.create())
                .add(AnyConverters.Protocol.class, ProtocolConverter.PROTOBUF, ProtoConverterFactory.create())
                .add(AnyConverters.Protocol.class, ProtocolConverter.WIRE, WireConverterFactory.create())
                .build();

    }
}
