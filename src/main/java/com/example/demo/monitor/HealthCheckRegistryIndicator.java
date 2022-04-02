package com.example.demo.monitor;

import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.stream.Collectors;

@Component("pool")
public class HealthCheckRegistryIndicator implements HealthIndicator {

    @Autowired
    private HealthCheckRegistry registry;

    @Override
    public Health health() {
        SortedMap<String, Result> healthChecks = registry.runHealthChecks();
        Map<String, Boolean> result = healthChecks.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, e -> e.getValue().isHealthy()));

        boolean healthy = healthChecks.values().stream().allMatch(Result::isHealthy);

        Health.Builder health = healthy ? Health.up() : Health.down();

        return health.withDetails(result).build();
    }

}
