package com.example.demo.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.AdminServlet;
import com.example.demo.servlet.HealthCheckServletContextListener;
import com.example.demo.servlet.MetricsServletContextListener;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DropWizardMetricConfiguration {

    @Bean
    public MetricRegistry metricRegistry() {
        return new MetricRegistry();
    }

    @Bean
    public HealthCheckRegistry healthCheckRegistry() {
        return new HealthCheckRegistry();
    }

    @Bean
    public HealthCheckServletContextListener healthCheckServletContextListener() {
        return new HealthCheckServletContextListener(healthCheckRegistry());
    }

    @Bean
    public MetricsServletContextListener metricsServletContextListener() {
        return new MetricsServletContextListener(metricRegistry());
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new AdminServlet(), "/system/*");
    }
}
