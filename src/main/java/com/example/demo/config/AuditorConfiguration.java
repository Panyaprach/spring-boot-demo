package com.example.demo.config;

import com.example.demo.security.UserToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditorConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {

        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            return Optional.ofNullable(authentication)
                    .map(Authentication::getPrincipal)
                    .map(this::cast);
        };
    }

    private String cast(Object principal) {
        if (principal instanceof String) {
            return (String) principal;
        } else if (principal instanceof User) {
            return ((User) principal).getUsername();
        } else {
            String error = String.format("Unable to cast %s to %s", principal.getClass(), String.class);
            throw new IllegalArgumentException(error);
        }
    }
}
