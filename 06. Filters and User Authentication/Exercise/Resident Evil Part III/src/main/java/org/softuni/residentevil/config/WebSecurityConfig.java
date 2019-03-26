package org.softuni.residentevil.config;

import org.softuni.residentevil.domain.enums.Authority;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String REMEMBER_ME_TOKEN_NAME = "rememberMe";
    private static final int REMEMBER_ME_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 30; // 30 Days

    private static CsrfTokenRepository csrfTokenRepository() {
        final String CSRF_ATTRIBUTE_NAME = "_csrf";
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName(CSRF_ATTRIBUTE_NAME);
        return repository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf()
                .csrfTokenRepository(csrfTokenRepository())
                .and()
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers(WebConfig.URL_INDEX, WebConfig.URL_USER_REGISTER, WebConfig.URL_USER_LOGIN)
                .anonymous()
                .antMatchers(WebConfig.URL_USER_ALL)
                .hasAuthority(Authority.ADMIN.role())
                .antMatchers(WebConfig.URL_VIRUS_EDIT, WebConfig.URL_VIRUS_DELETE, WebConfig.URL_VIRUS_ADD)
                .hasAuthority(Authority.MODERATOR.role())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(WebConfig.URL_USER_LOGIN)
                .defaultSuccessUrl(WebConfig.URL_USER_HOME)
                .and()
                .rememberMe()
                .tokenValiditySeconds(REMEMBER_ME_TOKEN_VALIDITY_SECONDS)
                .key(REMEMBER_ME_TOKEN_NAME)
                .and()
                .logout()
                .logoutUrl(WebConfig.URL_USER_LOGOUT)
                .logoutSuccessUrl(WebConfig.URL_INDEX);
    }
}
