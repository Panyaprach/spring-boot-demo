package com.example.demo.servlet;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;

public class MetricsServletContextListener extends MetricsServlet.ContextListener {

    private final MetricRegistry registry;

    public MetricsServletContextListener(MetricRegistry registry) {
        this.registry = registry;
    }

    @Override
    protected MetricRegistry getMetricRegistry() {
        return registry;
    }
}
