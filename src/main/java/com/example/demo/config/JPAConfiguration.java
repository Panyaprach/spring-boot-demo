package com.example.demo.config;

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
    @Autowired
    private HealthCheckRegistry healthCheckRegistry;

    /* @Bean
    public DataSource mysql() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/demo?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    } */

    @Bean
    public DataSource h2() {
        HikariDataSource dataSource = new HikariDataSource(hikariConfig());

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
        config.setPoolName("H2");
        config.setHealthCheckRegistry(healthCheckRegistry);

        return config;
    }
}
