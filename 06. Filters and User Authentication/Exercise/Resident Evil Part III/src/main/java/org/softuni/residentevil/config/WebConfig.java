package org.softuni.residentevil.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static final String URL_INDEX = "/";
    public static final String URL_VIRUS_ALL = "/viruses";
    public static final String URL_VIRUS_ADD = "/viruses/add";
    public static final String URL_VIRUS_DELETE = "/viruses/delete";
    public static final String URL_VIRUS_EDIT = "/viruses/edit";
    public static final String URL_ALL = "/all";
    public static final String URL_USER_REGISTER = "/user/register";
    public static final String URL_USER_LOGIN = "/user/login";
    public static final String URL_USER_LOGOUT = "/user/logout";
    public static final String URL_USER_HOME = "/user/home";
    public static final String URL_USER_ALL = "/user";
    public static final String URL_ERROR_UNAUTHORIZED = "/unauthorized";

    public static final String REST_API_CAPITAL = "/api/capital";
    public static final String REST_API_VIRUS = "/api/virus";

    private final HandlerInterceptor thymeleafLayoutInterceptor;

    @Autowired
    public WebConfig(@Qualifier("ThymeleafLayoutInterceptor") HandlerInterceptor thymeleafLayoutInterceptor) {
        this.thymeleafLayoutInterceptor = thymeleafLayoutInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(thymeleafLayoutInterceptor);
    }
}
