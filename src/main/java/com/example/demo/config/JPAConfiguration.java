package com.example.demo.config;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JPAConfiguration {
    private final String HIBERNATE_DIALECT_PROPERTY = "jpa.properties.hibernate.dialect";

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
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUsername("sa");
        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        setDialect(H2Dialect.class);

        return dataSource;
    }
}
