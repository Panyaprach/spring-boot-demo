package com.example.demo;

import com.codahale.metrics.MetricRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
public class DemoController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("leak")
    public ResponseEntity<?> leakConnection() {
        try {
            dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.noContent().build();
    }
}
