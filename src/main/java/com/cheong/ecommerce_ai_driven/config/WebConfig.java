package com.cheong.ecommerce_ai_driven.config;

import com.cheong.ecommerce_ai_driven.common.converter.JsonPatchHttpMessageConverter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebConfig implements WebFluxConfigurer {

    private final JsonPatchHttpMessageConverter jsonPatchHttpMessageConverter;

    public WebConfig(JsonPatchHttpMessageConverter jsonPatchHttpMessageConverter) {
        this.jsonPatchHttpMessageConverter = jsonPatchHttpMessageConverter;
    }

    @Override
    public void configureHttpMessageCodecs(org.springframework.http.codec.ServerCodecConfigurer configurer) {

        configurer.customCodecs().register(jsonPatchHttpMessageConverter);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

}
