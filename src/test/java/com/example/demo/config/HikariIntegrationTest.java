package com.example.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class HikariIntegrationTest {

    @Autowired
    DataSource dataSource;

    @Test
    public void whenGetDataSource_thenHikari() {
        assertTrue(dataSource instanceof HikariDataSource);
    }
}
