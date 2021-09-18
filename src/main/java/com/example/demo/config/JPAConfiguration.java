package com.example.demo.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JPAConfiguration {
    private final String HIBERNATE_DIALECT_PROPERTY = "jpa.properties.hibernate.dialect";

    @Autowired
    private MetricRegistry metricRegistry;

    @Autowired
    private HealthCheckRegistry healthCheckRegistry;

    /* @Bean
    public DataSource mysql() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/demo?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        setDialect(MySQL8Dialect.class);

        return dataSource;
    } */

    private void setDialect(Class<? extends Dialect> dialect) {
        System.setProperty(HIBERNATE_DIALECT_PROPERTY, dialect.getName());
    }

    @Bean
    public DataSource h2() {
        HikariDataSource dataSource = new HikariDataSource(hikariConfig());
        setDialect(H2Dialect.class);

        return dataSource;
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.h2.Driver");
        config.setJdbcUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        config.setUsername("sa");
        config.setPassword("root");
        config.setLeakDetectionThreshold(5000);
        config.setMetricRegistry(metricRegistry);
        config.setHealthCheckRegistry(healthCheckRegistry);
        config.setPoolName("H2");

        return config;
    }
}
