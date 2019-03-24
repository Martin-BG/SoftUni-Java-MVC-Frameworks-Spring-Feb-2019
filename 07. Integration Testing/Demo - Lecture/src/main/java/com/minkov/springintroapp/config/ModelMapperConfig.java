package com.minkov.springintroapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    private static ModelMapper mapper;

    static {
        mapper = new ModelMapper();
        // ModelMapperConfig.configModelMapper(mapper);
    }

    @Bean
    public ModelMapper modelMapper() {
        return mapper;
    }
}
