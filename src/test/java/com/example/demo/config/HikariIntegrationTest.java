package com.example.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class HikariIntegrationTest {

    @Autowired
    DataSource dataSource;

    @Test
    public void whenGetDataSource_shouldUseHikari() {
        assertTrue(dataSource instanceof HikariDataSource);
    }
}
