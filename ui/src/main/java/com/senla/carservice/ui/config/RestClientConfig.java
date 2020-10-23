package com.senla.carservice.ui.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestClientConfig {
    // обычно для пропертей используют аннотацию Value - там и дефолтное значение можно прописать,
    // а не целый энвайронмент
    private static final String BASE_URI = "carservice.connection.url";
    // если какому-то бину нужна какая-то зависимость - можно добавлять ее в аргументы метода-бина,
    // и спринг подставит сам: public RestTemplate restTemplate(Environment environment)
    // (без каких либо аннотаций, только @Bean )
    @Autowired
    private Environment environment;

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(3000);
        clientHttpRequestFactory.setReadTimeout(3000);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(environment.getRequiredProperty(BASE_URI)));
        return restTemplate;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    // необычный подход, но в целом неплохо, молодец, оригинально
    // я бы только посоветовал дать название методу (а мы знаем, что это имя метода - это айди бина),
    // которое пояснит, что именно это за хэдеры
    // еще я бы посмотрел в стророну установки дефолтных хэдеров в сам рест темлейт - уверен, он это умеет
    @Bean
    public HttpHeaders httpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

}
