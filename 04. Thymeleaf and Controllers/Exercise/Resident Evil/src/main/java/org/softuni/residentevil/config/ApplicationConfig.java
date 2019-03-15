package org.softuni.residentevil.config;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
@EnableCaching
public class ApplicationConfig {

    private static final String SYSTEM_TIME_ZONE = "UTC";

    /**
     * Set system {@link TimeZone} to {@value #SYSTEM_TIME_ZONE} to match setting used for database connection
     */
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone(SYSTEM_TIME_ZONE));
    }

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
     * Configuration of runtime method arguments validation and return values validation.
     * {@link org.springframework.validation.annotation.Validated} annotation should be present on the class for validation to work.
     * <p>
     * Could be useful in repository methods to prevent request with invalid parameters
     * (ex. empty or not properly username in findUserByUsername)
     * <p><a href="https://www.baeldung.com/javax-validation-method-constraints">More information</a></p>
     * <hr>
     * <pre>{@code @Validated
     * @Repository
     * public interface UserRepository extends JpaRepository<User, UUID> {
     *
     *     Optional<User> findUserByUsername(@ValidUserUsername String username);
     * }</pre>
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
