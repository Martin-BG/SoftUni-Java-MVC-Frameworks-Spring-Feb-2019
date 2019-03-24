package com.minkov.springintroapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarsConfig {
    @Bean
    public String[] cars() {
        return new String[] { "bqla", "zelena"};
    }
}
