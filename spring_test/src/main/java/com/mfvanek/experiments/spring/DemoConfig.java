package com.mfvanek.experiments.spring;

import com.mfvanek.experiments.spring.impl.EvilDuck;
import com.mfvanek.experiments.spring.interfaces.Duck;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.mfvanek.experiments.spring")
class DemoConfig {

    @Bean
    public Duck evilDuck() {
        return new EvilDuck(7);
    }
}
