package com.example.demo.servlet;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;

public class HealthCheckServletContextListener extends HealthCheckServlet.ContextListener {
    private final HealthCheckRegistry registry;

    public HealthCheckServletContextListener(HealthCheckRegistry registry) {
        this.registry = registry;
    }

    @Override
    protected HealthCheckRegistry getHealthCheckRegistry() {
        return registry;
    }
}
