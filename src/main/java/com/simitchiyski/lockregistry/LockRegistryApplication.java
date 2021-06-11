package com.simitchiyski.lockregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.UUID;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class LockRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(LockRegistryApplication.class, args);
    }
}