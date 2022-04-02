package com.example.demo.config;

import com.example.demo.graphql.scalar.InstantScalar;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQLScalarTypeConfiguration {

    @Bean
    public GraphQLScalarType instant() {
        return InstantScalar.INSTANCE;
    }
}
