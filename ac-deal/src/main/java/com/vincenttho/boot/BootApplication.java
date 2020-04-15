package com.vincenttho.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {
        "com.vincenttho.service",
        "com.vincenttho.config",
        "com.vincenttho.task",
        "com.vincenttho.utils"
})
@EntityScan(basePackages = {
        "com.vincenttho.service"
})
@EnableJpaRepositories(basePackages = {
        "com.vincenttho.service"
})
@EnableScheduling
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

}
