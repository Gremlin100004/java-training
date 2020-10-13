package com.senla.carservice.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ComponentScan("com.senla.carservice")
@PropertySource("classpath:application.properties")
public class WebConfig {

}
