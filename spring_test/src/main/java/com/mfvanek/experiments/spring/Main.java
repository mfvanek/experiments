package com.mfvanek.experiments.spring;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig.class)) {
            DuckDemo duckDemo = context.getBean(DuckDemo.class);
            duckDemo.test();

            Locale.setDefault(Locale.FRENCH);
            duckDemo.test();
        }
    }
}
