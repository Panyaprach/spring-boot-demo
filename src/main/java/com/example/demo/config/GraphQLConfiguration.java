package com.example.demo.config;

import com.example.demo.graphql.scalar.InstantScalar;
import com.example.demo.security.GraphQLBasicSecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class GraphQLConfiguration {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {

        return wiring -> wiring.scalar(InstantScalar.INSTANCE);
    }

    @Bean
    public WebGraphQlInterceptor graphQlInterceptor(UserDetailsService service, PasswordEncoder encoder) {
        return new GraphQLBasicSecurityInterceptor(service, encoder);
    }
}
