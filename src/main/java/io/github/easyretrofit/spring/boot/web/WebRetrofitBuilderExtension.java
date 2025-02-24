package io.github.easyretrofit.spring.boot.web;

import io.github.easyretrofit.core.RetrofitBuilderExtension;
import io.github.easyretrofit.core.builder.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class WebRetrofitBuilderExtension implements RetrofitBuilderExtension {

    @Override
    public boolean enable() {
        return true;
    }

    @Override
    public String globalBaseUrl() {
        return null;
    }

    @Override
    public Class<? extends BaseCallAdapterFactoryBuilder>[] globalCallAdapterFactoryBuilderClazz() {
        return new ArrayList<Class<? extends BaseCallAdapterFactoryBuilder>>() {{
            add(BodyCallAdapterFactoryBuilder.class);
            add(GuavaCallAdapterFactoryBuilder.class);
            add(Rxjava3CallAdapterFactoryBuilder.class);
        }}.toArray(new Class[0]);
    }

    @Override
    public Class<? extends BaseConverterFactoryBuilder>[] globalConverterFactoryBuilderClazz() {
        return new ArrayList<Class<? extends BaseConverterFactoryBuilder>>() {{
            add(JacksonConvertFactoryBuilder.class);
            add(BodyBaseTypeConvertFactoryBuilder.class);
        }}.toArray(new Class[0]);
    }

    @Override
    public Class<? extends BaseOkHttpClientBuilder> globalOkHttpClientBuilderClazz() {
        return null;
    }

    @Override
    public Class<? extends BaseCallBackExecutorBuilder> globalCallBackExecutorBuilderClazz() {
        return null;
    }

    @Override
    public Class<? extends BaseCallFactoryBuilder> globalCallFactoryBuilderClazz() {
        return null;
    }

    @Override
    public String globalValidateEagerly() {
        return null;
    }
}
