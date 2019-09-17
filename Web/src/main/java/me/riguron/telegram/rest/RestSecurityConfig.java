package me.riguron.telegram.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@Order(1)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
            http.
                antMatcher("/api/**")
                    .authorizeRequests()
                       .anyRequest().authenticated()
                       .and()
                    .exceptionHandling()
                       .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                       .and()
                    .httpBasic().authenticationEntryPoint(authenticationEntryPoint())
                       .and()
                    .csrf()
                       .disable();
    }

    @Bean
    public BasicAuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationFailureEntryPoint();
    }
}
