package com.senla.carservice.controller.config;

import com.senla.carservice.controller.config.filter.JwtFilter;
import com.senla.carservice.controller.exception.ControllerException;
import com.senla.carservice.controller.exception.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@AllArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String REGISTRATION_URL = "/users/registration";
    private static final String LOGIN_URL = "/users/login";
    private final UserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) {
        log.debug("[configure]");
        log.trace("[httpSecurity: {}]", httpSecurity);
        try {
            httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, REGISTRATION_URL).permitAll()
                .antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(globalExceptionHandler())
                .and()
                .exceptionHandling().accessDeniedHandler(globalExceptionHandler())
                .and()
                .formLogin().disable()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        } catch (Exception exception) {
            log.error("[{}]", exception.getMessage());
            throw new ControllerException("HttpSecurity configuration is wrong");
        }
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() {
        try {
            return authenticationManager();
        } catch (Exception exception) {
            log.error("[{}]", exception.getMessage());
            throw new ControllerException("The authentication of the passed authentication object is wrong");
        }
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        log.debug("[configure]");
        log.trace("[authenticationManagerBuilder: {}]", authenticationManagerBuilder);
        try {
            authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        } catch (Exception exception) {
            log.error("[{}]", exception.getMessage());
            throw new ControllerException("AuthenticationManagerBuilder configuration is wrong");
        }
    }

}
