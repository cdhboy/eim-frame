package com.eim;

import com.eim.dao.annotation.EnableDynamicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDynamicDataSource
public class EimApplication {
    public static void main(String[] args) {
        SpringApplication.run(EimApplication.class, args);
    }
}