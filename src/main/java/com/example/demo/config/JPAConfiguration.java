package com.example.demo.config;

import org.hibernate.dialect.MySQL8Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;

@Configuration
public class JPAConfiguration {

    @Bean
    public DataSource mysql() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/demo?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        setMySQL8Properties();

        return dataSource;
    }

    private void setMySQL8Properties() {
        System.setProperty("jpa.properties.hibernate.dialect", MySQL8Dialect.class.getName());
    }
}
