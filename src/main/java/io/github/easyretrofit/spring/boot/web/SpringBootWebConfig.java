package io.github.easyretrofit.spring.boot.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBootWebConfig {

    @Bean
    @ConditionalOnMissingBean
    public BodyCallAdapterFactoryBuilder bodyCallAdapterFactoryBuilder() {
        return new BodyCallAdapterFactoryBuilder();
    }

}
