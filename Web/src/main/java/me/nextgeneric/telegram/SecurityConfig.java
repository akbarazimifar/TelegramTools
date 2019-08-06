package me.nextgeneric.telegram;

import me.nextgeneric.telegram.confirm.ConfirmationFilter;
import me.nextgeneric.telegram.security.AuthFailureHandler;
import me.nextgeneric.telegram.security.LoginFormValidatingFilter;
import me.nextgeneric.telegram.security.RoleAssistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.Filter;
import javax.validation.Validator;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Validator validator;

    private AuthenticationSuccessHandler authSuccessHandler;

    private RoleAssistant roleAssistant;

    @Autowired
    public SecurityConfig(Validator validator, AuthenticationSuccessHandler authSuccessHandler, RoleAssistant roleAssistant) {
        this.validator = validator;
        this.authSuccessHandler = authSuccessHandler;
        this.roleAssistant = roleAssistant;
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService service) throws Exception {
        auth.userDetailsService(service).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // @formatter:off
        http
                .addFilterBefore(loginFormValidatingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(confirmationFilter(roleAssistant), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                   .antMatchers("/resources/**").permitAll()
                   .anyRequest().authenticated()
                   .and()
                .sessionManagement()
                   .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                   .and()
                .formLogin()
                   .loginPage("/login")
                   .usernameParameter("login")
                   .passwordParameter("password")
                   .permitAll()
                   .and()
                .logout()
                   .invalidateHttpSession(true)
                   .permitAll()
                   .and()
                .csrf()
                   .disable();
    }

    @Bean
    public AuthFailureHandler authFailureHandler() {
        return new AuthFailureHandler(sessionFlashMapManager());
    }

    @Bean
    public SessionFlashMapManager sessionFlashMapManager() {
        return new SessionFlashMapManager();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public Filter confirmationFilter(RoleAssistant roleAssistant) {
        return new ConfirmationFilter(roleAssistant);
    }

    @Bean
    public LoginFormValidatingFilter loginFormValidatingFilter() throws Exception {
        LoginFormValidatingFilter authenticationFilter = new LoginFormValidatingFilter(validator);
        authenticationFilter.setAuthenticationManager(customAuthenticationManager());
        authenticationFilter.setAuthenticationFailureHandler(authFailureHandler());
        authenticationFilter.setAuthenticationSuccessHandler(authSuccessHandler);
        return authenticationFilter;
    }

}