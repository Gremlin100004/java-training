package com.senla.carservice.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@ComponentScan("com.senla.carservice")
@PropertySource("classpath:application.properties")
public class WebConfig implements WebMvcConfigurer {

}
