package org.softuni.residentevil.config;

import org.softuni.residentevil.domain.enums.Authority;
import org.softuni.residentevil.web.handlers.AccessDeniedHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final int REMEMBER_ME_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 30; // 30 Days
    private static final String J_SESSION_ID = "JSESSIONID";

    private final UserDetailsService userService;

    @Autowired
    public WebSecurityConfig(@Qualifier("userDetailsService") UserDetailsService userService) {
        this.userService = userService;
    }

    private static CsrfTokenRepository csrfTokenRepository() {
        final String CSRF_ATTRIBUTE_NAME = "_csrf";
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName(CSRF_ATTRIBUTE_NAME);
        return repository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .cors()
                .disable()
            .csrf()
                .csrfTokenRepository(csrfTokenRepository())
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
                .and()
            .logout()
                .logoutUrl(WebConfig.URL_USER_LOGOUT)
                .deleteCookies(J_SESSION_ID)
                .logoutSuccessUrl(WebConfig.URL_USER_LOGIN + "?logout")
                .and()
            .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
            .and()
                .sessionManagement()
                .invalidSessionUrl(WebConfig.URL_USER_LOGIN + "?expired");
        // @formatter:on
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }
}
