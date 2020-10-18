package com.senla.carservice.service.config;

import com.senla.carservice.service.util.JwtHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public JwtHandler jwtHandler() {
        return new JwtHandler();
    }

}
