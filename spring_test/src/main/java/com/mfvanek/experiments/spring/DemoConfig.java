package com.mfvanek.experiments.spring;

import com.mfvanek.experiments.spring.impl.EvilDuck;
import com.mfvanek.experiments.spring.interfaces.Duck;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@ComponentScan(basePackages = "com.mfvanek.experiments.spring")
class DemoConfig {

    @Bean
    public Duck evilDuck() {
        return new EvilDuck(7, messageSource());
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
}
