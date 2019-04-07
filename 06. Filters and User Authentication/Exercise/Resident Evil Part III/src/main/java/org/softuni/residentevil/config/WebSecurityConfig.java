package org.softuni.residentevil.config;

import org.softuni.residentevil.domain.enums.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String CSRF_ATTRIBUTE_NAME = "_csrf";

    private static final int REMEMBER_ME_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 30; // 30 Days
    private static final String REMEMBER_ME_KEY = "remember-me-key";
    private static final String REMEMBER_ME_COOKIE = "remember-me";
    private static final String SESSION_COOKIE = "JSESSIONID";

    private final UserDetailsService userService;
    private final AccessDeniedHandler accessDeniedHandler;
    private final CsrfTokenRepository csrfTokenRepository;

    @Autowired
    public WebSecurityConfig(@Qualifier("UserServiceImpl") UserDetailsService userService,
                             AccessDeniedHandler accessDeniedHandler,
                             CsrfTokenRepository csrfTokenRepository) {
        this.userService = userService;
        this.accessDeniedHandler = accessDeniedHandler;
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .cors()
                .disable()
            .csrf()
                .csrfTokenRepository(csrfTokenRepository)
                .and()
            .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**")
                    .permitAll()
                .antMatchers(WebConfig.URL_INDEX)
                    .permitAll()
                .antMatchers(WebConfig.URL_USER_REGISTER, WebConfig.URL_USER_LOGIN)
                    .anonymous()
                .antMatchers(WebConfig.URL_USER_ALL)
                    .hasAuthority(Authority.ADMIN.role())
                .antMatchers(WebConfig.URL_VIRUS_EDIT, WebConfig.URL_VIRUS_DELETE, WebConfig.URL_VIRUS_ADD)
                    .hasAuthority(Authority.MODERATOR.role())
                .anyRequest()
                    .authenticated()
                .and()
            .formLogin()
                .loginPage(WebConfig.URL_USER_LOGIN)
                .defaultSuccessUrl(WebConfig.URL_USER_HOME)
                .and()
            .rememberMe()
                .userDetailsService(userService)
                .tokenValiditySeconds(REMEMBER_ME_TOKEN_VALIDITY_SECONDS)
                .key(REMEMBER_ME_KEY)
                .rememberMeCookieName(REMEMBER_ME_COOKIE)
                .and()
            .logout()
                .logoutUrl(WebConfig.URL_USER_LOGOUT)
                .deleteCookies(SESSION_COOKIE, REMEMBER_ME_COOKIE)
                .logoutSuccessUrl(WebConfig.URL_USER_LOGIN + "?logout")
                .and()
            .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
            .and()
                .sessionManagement()
                .invalidSessionUrl(WebConfig.URL_USER_LOGIN + "?expired");
        // @formatter:on
    }
}
