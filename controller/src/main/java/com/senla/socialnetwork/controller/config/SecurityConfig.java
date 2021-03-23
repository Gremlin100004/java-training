package com.senla.socialnetwork.controller.config;

import com.senla.socialnetwork.controller.exception.ControllerException;
import com.senla.socialnetwork.controller.exception.GlobalExceptionHandler;
import com.senla.socialnetwork.controller.filter.JwtAuthorizationFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@AllArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String REGISTRATION_URL = "/users/registration";
    private static final String LOGIN_URL = "/users/login";
    private static final String ADMIN_URL = "/admin";
    private static final String[] AUTH_WHITELIST = {"/v2/api-docs", "/swagger-resources", "/swagger-resources/**",
       "/configuration/ui", "/configuration/security", "/webjars/**", "/v3/api-docs/**", "/swagger-ui/**" };

    private final UserDetailsService userDetailsService;
    private final JwtAuthorizationFilter jwtFilter;
    @Autowired
    private final GlobalExceptionHandler globalExceptionHandler;

    @Override
    protected void configure(final HttpSecurity httpSecurity) {
        log.debug("[httpSecurity: {}]", httpSecurity);
        try {
            httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.POST, REGISTRATION_URL).permitAll()
                .antMatchers(HttpMethod.PUT, LOGIN_URL).permitAll()
                .antMatchers(HttpMethod.GET, ADMIN_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(globalExceptionHandler)
                .and()
                .exceptionHandling().accessDeniedHandler(globalExceptionHandler)
                .and()
                .formLogin().disable()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        } catch (Exception exception) {
            log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
            throw new ControllerException("HttpSecurity configuration is wrong");
        }
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) {
        log.debug("[authenticationManagerBuilder: {}]", authenticationManagerBuilder);
        try {
            authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        } catch (Exception exception) {
            log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
            throw new ControllerException("AuthenticationManagerBuilder configuration is wrong");
        }
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() {
        try {
            return authenticationManager();
        } catch (Exception exception) {
            log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
            throw new ControllerException("The authentication of the passed authentication object is wrong");
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
