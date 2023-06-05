package com.dynamiccalculations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })

public class DynamicCalculationsApplication {

    public static void main(String[] args) {

        SpringApplication.run(DynamicCalculationsApplication.class, args);
    }

}
