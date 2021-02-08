package com.example.demo.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@TestConfiguration
@EnableJpaAuditing(auditorAwareRef = "testAuditorProvider")
public class AuditorTestConfiguration {

    @Bean
    public AuditorAware<String> testAuditorProvider() {
        return () -> Optional.of("Tester");
    }
}
