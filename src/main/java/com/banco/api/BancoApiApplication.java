package com.banco.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableJpaRepositories(basePackages="com.banco.api.repository")
@EnableScheduling
public class BancoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BancoApiApplication.class, args);
    }

}
