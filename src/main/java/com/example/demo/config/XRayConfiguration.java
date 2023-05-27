package com.example.demo.config;

import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.servlet.Filter;

@Configuration
@Profile("cloud")
public class XRayConfiguration {

    @Bean
    public Filter tracingFilter() {
        return new AWSXRayServletFilter("Demo");
    }
}
