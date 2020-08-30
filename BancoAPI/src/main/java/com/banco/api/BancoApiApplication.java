package com.banco.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableJpaRepositories(basePackages="com.banco.api.repository")
@EntityScan(basePackages="com.banco.api.model.internal")
public class BancoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BancoApiApplication.class, args);
    }

}
