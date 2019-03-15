package org.softuni.residentevil.config;

import org.softuni.residentevil.web.interceptors.ThymeleafLayoutInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                ThymeleafLayoutInterceptor
                        .builder()
                        .withDefaultLayout("/layouts/default")
                        .withViewAttribute("view")
                        .withViewPrefix("/views/")
                        .build());
    }
}
