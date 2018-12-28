package com.mfvanek.experiments.spring.impl;

import com.mfvanek.experiments.spring.interfaces.Duck;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class EvilDuck implements Duck {

    private final int count;

    public EvilDuck(int count) {
        this.count = count;
    }

    @Override
    public String quack() {
        StringBuilder builder = new StringBuilder("Die! Aha");
        for (int i = 0; i < count; ++i) {
            builder.append("-ha");
        }
        builder.append("!!!");
        return builder.toString();
    }

    @PostConstruct
    void init() {
        System.out.println(this.getClass().getSimpleName() + " init() is called");
    }

    @PreDestroy
    void destroy() {
        System.out.println(this.getClass().getSimpleName() + " destroy() is called");
    }
}
