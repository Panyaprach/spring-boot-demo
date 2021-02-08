package com.example.demo.extension;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

public class SpringPrintSqlExtension implements TestInstancePostProcessor {

    private static final String SPRING_JPA_SHOW_SQL = "spring.jpa.show-sql";
    private static final String SPRING_JPA_HIBERNATE_SQL_FORMAT = "spring.jpa.properties.hibernate.format_sql";

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        enable(SPRING_JPA_SHOW_SQL);
        enable(SPRING_JPA_HIBERNATE_SQL_FORMAT);
    }

    private void enable(String property){
        final String enable = "true";

        System.setProperty(property, enable);
    }
}
