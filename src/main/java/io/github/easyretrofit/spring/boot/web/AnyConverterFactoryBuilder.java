package io.github.easyretrofit.spring.boot.web;

import io.github.easyretrofit.core.builder.BaseConverterFactoryBuilder;
import io.github.easyretrofit.spring.boot.web.converter.JsonConverter;
import io.github.easyretrofit.spring.boot.web.converter.ProtocolConverter;
import io.github.easyretrofit.spring.boot.web.converter.XmlConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.jaxb.JaxbConverterFactory;
import retrofit2.converter.protobuf.ProtoConverterFactory;
import retrofit2.converter.wire.WireConverterFactory;

public class AnyConverterFactoryBuilder extends BaseConverterFactoryBuilder {


    private static final Logger log = LoggerFactory.getLogger(AnyConverterFactoryBuilder.class);

    @Override
    public Converter.Factory buildConverterFactory() {
        AnyConverters.QualifiedTypeConverterFactory.Builder builder = new AnyConverters.QualifiedTypeConverterFactory.Builder();
        builder.add(AnyConverters.Json.class, JsonConverter.JACKSON, JacksonConverterFactory.create())
                .add(AnyConverters.Json.class, JsonConverter.GSON, GsonConverterFactory.create())
                .add(AnyConverters.Protocol.class, ProtocolConverter.PROTOBUF, ProtoConverterFactory.create())
                .add(AnyConverters.Protocol.class, ProtocolConverter.WIRE, WireConverterFactory.create());
        try {
            Class.forName("javax.xml.bind.JAXBContext");
            builder.add(AnyConverters.Xml.class, XmlConverter.JAXB, JaxbConverterFactory.create());
        } catch (ClassNotFoundException e) {
            log.warn("javax.xml.bind.JAXBContext is available in the classpath.");
        }
//        try {
//            Class.forName("jakarta.xml.bind.JAXBContext");
//            builder.add(AnyConverters.Xml.class, XmlConverter.JAXB, retrofit2.converter.jaxb3.JaxbConverterFactory.create());
//        } catch (ClassNotFoundException e) {
//            log.warn("jakarta.xml.bind.JAXBContext is available in the classpath.");
//        }
        return builder.build();

    }
}
