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
    public static final String URL_ALL = "/all";
    public static final String URL_ERROR_UNAUTHORIZED = "/unauthorized";

    private static final String USER_BASE_URL = "/user";
    public static final String URL_USER_ALL = USER_BASE_URL;
    public static final String URL_USER_REGISTER = USER_BASE_URL + "/register";
    public static final String URL_USER_LOGIN = USER_BASE_URL + "/login";
    public static final String URL_USER_LOGOUT = USER_BASE_URL + "/logout";
    public static final String URL_USER_HOME = USER_BASE_URL + "/home";

    private static final String VIRUS_BASE_URL = "/virus";
    public static final String URL_VIRUS_ALL = VIRUS_BASE_URL;
    public static final String URL_VIRUS_ADD = VIRUS_BASE_URL + "/add";
    public static final String URL_VIRUS_DELETE = VIRUS_BASE_URL + "/delete";
    public static final String URL_VIRUS_EDIT = VIRUS_BASE_URL + "/edit";

    private static final String API_BASE_URL = "/api";
    public static final String REST_API_CAPITAL = API_BASE_URL + "/capital";
    public static final String REST_API_VIRUS = API_BASE_URL + VIRUS_BASE_URL;

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
