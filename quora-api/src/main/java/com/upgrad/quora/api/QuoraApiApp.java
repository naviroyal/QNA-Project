package com.upgrad.quora.api;

import com.upgrad.quora.service.ServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ServiceConfiguration.class)
public class QuoraApiApp {
    public static void main(String[] args) {
        SpringApplication.run(QuoraApiApp.class, args);
    }
}

