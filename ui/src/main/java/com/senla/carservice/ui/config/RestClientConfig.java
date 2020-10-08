package com.senla.carservice.ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        //Todo correct all uri
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(3000);
        clientHttpRequestFactory.setReadTimeout(3000);
        RestTemplate restTemplate = new RestTemplate();
        //Todo get uri from property file
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080/carservice/"));
        return restTemplate;
    }

}
