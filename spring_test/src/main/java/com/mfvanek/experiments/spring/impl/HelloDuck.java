package com.mfvanek.experiments.spring.impl;

import com.mfvanek.experiments.spring.interfaces.Duck;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
class HelloDuck implements Duck {

    private int count;

    @Override
    public String quack() {
        StringBuilder builder = new StringBuilder("Hello");
        for (int i = 0; i < count; ++i) {
            builder.append('!');
        }
        return builder.toString();
    }

    @PostConstruct
    void init() {
        System.out.println(this.getClass().getSimpleName() + " init() is called");
        count = 5;
    }

    @PreDestroy
    void destroy() {
        System.out.println(this.getClass().getSimpleName() + " destroy() is called");
    }
}
