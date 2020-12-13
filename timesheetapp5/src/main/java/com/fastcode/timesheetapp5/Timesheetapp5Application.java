package com.fastcode.timesheetapp5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = { "com.fastcode.timesheetapp5", "org.springframework.versions" })
public class Timesheetapp5Application {

    public static void main(String[] args) {
        SpringApplication.run(Timesheetapp5Application.class, args);
    }
}
