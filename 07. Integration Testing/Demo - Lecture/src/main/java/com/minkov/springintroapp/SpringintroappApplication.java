package com.minkov.springintroapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringintroappApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringintroappApplication.class, args);
    }
}
