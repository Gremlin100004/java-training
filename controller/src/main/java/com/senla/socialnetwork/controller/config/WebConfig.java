package com.senla.socialnetwork.controller.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@ComponentScan("com.senla.socialnetwork")
@PropertySource("classpath:application.properties")
public class WebConfig implements WebMvcConfigurer {
    private static final String SWAGGER_PATH_PATTERN = "swagger-ui.html";
    private static final String SWAGGER_RESOURCE_LOCATION = "classpath:/META-INF/resources/";
    private static final String WEBJARS_PATH_PATTERN = "/webjars/**";
    private static final String WEBJARS_RESOURCE_LOCATION = "classpath:/META-INF/resources/webjars/";

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(SWAGGER_PATH_PATTERN)
            .addResourceLocations(SWAGGER_RESOURCE_LOCATION);

        registry.addResourceHandler(WEBJARS_PATH_PATTERN)
            .addResourceLocations(WEBJARS_RESOURCE_LOCATION);
    }

}
