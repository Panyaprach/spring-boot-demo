package com.example.demo.config;

import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class XRayConfiguration {

    @Bean
    public Filter tracingFilter() {
        return new AWSXRayServletFilter("Demo");
    }
}
