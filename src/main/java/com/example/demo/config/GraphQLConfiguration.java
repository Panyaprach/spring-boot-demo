package com.example.demo.config;

import com.example.demo.graphql.scalar.InstantScalar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfiguration {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {

        return wiring -> wiring.scalar(InstantScalar.INSTANCE);
    }
}
