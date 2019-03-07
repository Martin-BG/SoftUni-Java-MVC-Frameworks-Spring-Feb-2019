package org.softuni.exodia.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class ApplicationConfig {

    /**
     * Configure ModelMapper to use field instead of property access for mapping between classes
     * and instances thus promoting better encapsulation and immutability.
     *
     * @return ModelMapper bean
     */
    @Bean
    ModelMapper createModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        return modelMapper;
    }

    /**
     * Set system {@link TimeZone} to "UTC" to match setting used for database connection
     */
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
