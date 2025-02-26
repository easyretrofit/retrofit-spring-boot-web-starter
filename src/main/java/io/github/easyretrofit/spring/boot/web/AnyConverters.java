package io.github.easyretrofit.spring.boot.web;

import io.github.easyretrofit.spring.boot.web.converter.JsonConverter;
import io.github.easyretrofit.spring.boot.web.converter.ProtocolConverter;
import io.github.easyretrofit.spring.boot.web.converter.XmlConverter;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public final class AnyConverters {

    static class ConverterBean {
        protected final Class<? extends Annotation> cls;
        protected final Converter.Factory factory;

        ConverterBean(Class<? extends Annotation> cls, Converter.Factory factory) {
            this.cls = cls;
            this.factory = factory;
        }

        public Class<? extends Annotation> getCls() {
            return cls;
        }

        public Converter.Factory getFactory() {
            return factory;
        }
    }

    static class ProtocolConverterBean extends ConverterBean {
        private final ProtocolConverter protocolConverter;

        ProtocolConverterBean(Class<? extends Annotation> cls, ProtocolConverter protocolConverter, Converter.Factory factory) {
            super(cls, factory);
            this.protocolConverter = protocolConverter;

        }

        public ProtocolConverter getProtocolConverter() {
            return protocolConverter;
        }
    }

    static class XmlConverterBean extends ConverterBean {
        private final XmlConverter xmlConverter;

        XmlConverterBean(Class<? extends Annotation> cls, XmlConverter xmlConverter, Converter.Factory factory) {
            super(cls, factory);
            this.xmlConverter = xmlConverter;

        }

        public XmlConverter getXmlConverter() {
            return xmlConverter;
        }
    }

    static class JsonConverterBean extends ConverterBean {
        private final JsonConverter jacksonConverter;

        JsonConverterBean(Class<? extends Annotation> cls, JsonConverter jacksonConverter, Converter.Factory factory) {
            super(cls, factory);
            this.jacksonConverter = jacksonConverter;
        }

        public JsonConverter getJacksonConverter() {
            return jacksonConverter;
        }
    }

    @Retention(RUNTIME)
    public @interface Json {
        JsonConverter value() default JsonConverter.JACKSON;
    }

    @Retention(RUNTIME)
    public @interface Xml {
        XmlConverter value() default XmlConverter.JAXB;
    }

    @Retention(RUNTIME)
    public @interface Protocol {
        ProtocolConverter value() default ProtocolConverter.PROTOBUF;
    }

    public static class QualifiedTypeConverterFactory extends Converter.Factory {
        private static List<JsonConverterBean> jsonFactories = new ArrayList<>();
        private static List<XmlConverterBean> xmlFactories = new ArrayList<>();
        private static List<ProtocolConverterBean> protocolFactories = new ArrayList<>();

        public static class Builder {

            public Builder add(Class<? extends Annotation> cls, JsonConverter jsonConverter, Converter.Factory factory) {
                if (cls == null) {
                    throw new NullPointerException("cls == null");
                }
                if (factory == null) {
                    throw new NullPointerException("factory == null");
                }
                jsonFactories.add(new JsonConverterBean(cls, jsonConverter, factory));
                return this;
            }

            public Builder add(Class<? extends Annotation> cls, XmlConverter xmlConverter, Converter.Factory factory) {
                if (cls == null) {
                    throw new NullPointerException("cls == null");
                }
                if (factory == null) {
                    throw new NullPointerException("factory == null");
                }
                xmlFactories.add(new XmlConverterBean(cls, xmlConverter, factory));
                return this;
            }

            public Builder add(Class<? extends Annotation> cls, ProtocolConverter protocolConverter, Converter.Factory factory) {
                if (cls == null) {
                    throw new NullPointerException("cls == null");
                }
                if (factory == null) {
                    throw new NullPointerException("factory == null");
                }
                protocolFactories.add(new ProtocolConverterBean(cls, protocolConverter, factory));
                return this;
            }

            public QualifiedTypeConverterFactory build() {
                return new QualifiedTypeConverterFactory(jsonFactories, xmlFactories, protocolFactories);
            }
        }

        QualifiedTypeConverterFactory(List<JsonConverterBean> jsonFactories, List<XmlConverterBean> xmlFactories, List<ProtocolConverterBean> protocolFactories) {
            QualifiedTypeConverterFactory.jsonFactories = jsonFactories;
            QualifiedTypeConverterFactory.xmlFactories = xmlFactories;
            QualifiedTypeConverterFactory.protocolFactories = protocolFactories;
        }

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof Json) {
                    JsonConverterBean converterBean = jsonFactories.stream().filter(jsonConverterBean -> jsonConverterBean.getJacksonConverter() == ((Json) annotation).value()).findFirst().orElse(null);
                    assert converterBean != null;
                    return converterBean.getFactory().responseBodyConverter(type, annotations, retrofit);

                }
                if (annotation instanceof Xml) {
                    XmlConverterBean xmlFactory = xmlFactories.stream().filter(xmlConverterBean -> xmlConverterBean.getXmlConverter() == ((Xml) annotation).value()).findFirst().orElse(null);
                    assert xmlFactory != null;
                    return xmlFactory.getFactory().responseBodyConverter(type, annotations, retrofit);
                }
                if (annotation instanceof Protocol) {
                    ProtocolConverterBean protocolFactory = protocolFactories.stream().filter(protocolConverterBean -> protocolConverterBean.getProtocolConverter() == ((Protocol) annotation).value()).findFirst().orElse(null);
                    assert protocolFactory != null;
                    return protocolFactory.getFactory().responseBodyConverter(type, annotations, retrofit);
                }
            }
            JsonConverterBean converterBean = jsonFactories.stream().filter(jsonConverterBean -> jsonConverterBean.getJacksonConverter() == JsonConverter.JACKSON).findFirst().orElse(null);
            assert converterBean != null;
            return converterBean.getFactory().responseBodyConverter(type, annotations, retrofit);
        }

        @Override
        public @Nullable Converter<?, RequestBody> requestBodyConverter(Type type,
                                                                        Annotation[] parameterAnnotations,
                                                                        Annotation[] methodAnnotations,
                                                                        Retrofit retrofit) {
            for (Annotation annotation : parameterAnnotations) {
                if (annotation instanceof Json) {
                    JsonConverterBean converterBean = jsonFactories.stream().filter(jsonConverterBean -> jsonConverterBean.getJacksonConverter() == ((Json) annotation).value()).findFirst().orElse(null);
                    assert converterBean != null;
                    return converterBean.getFactory().requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);

                }
                if (annotation instanceof Xml) {
                    XmlConverterBean xmlFactory = xmlFactories.stream().filter(xmlConverterBean -> xmlConverterBean.getXmlConverter() == ((Xml) annotation).value()).findFirst().orElse(null);
                    assert xmlFactory != null;
                    return xmlFactory.getFactory().requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
                }
                if (annotation instanceof Protocol) {
                    ProtocolConverterBean protocolFactory = protocolFactories.stream().filter(protocolConverterBean -> protocolConverterBean.getProtocolConverter() == ((Protocol) annotation).value()).findFirst().orElse(null);
                    assert protocolFactory != null;
                    return protocolFactory.getFactory().requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
                }
            }
            JsonConverterBean converterBean = jsonFactories.stream().filter(jsonConverterBean -> jsonConverterBean.getJacksonConverter() == JsonConverter.JACKSON).findFirst().orElse(null);
            assert converterBean != null;
            return converterBean.getFactory().requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);

        }
    }
}
