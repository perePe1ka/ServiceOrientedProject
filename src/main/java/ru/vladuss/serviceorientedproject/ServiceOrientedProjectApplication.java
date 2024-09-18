package ru.vladuss.serviceorientedproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
@EntityScan
public class ServiceOrientedProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceOrientedProjectApplication.class, args);
        System.setProperty("server.port", "8080");
    }

}
