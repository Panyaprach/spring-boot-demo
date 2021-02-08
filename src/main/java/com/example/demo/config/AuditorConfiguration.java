package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing( auditorAwareRef = "auditorProvider")
public class AuditorConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {

        return () -> Optional.of("Fizz");
    }
}
