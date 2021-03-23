package com.senla.socialnetwork.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
public class SpringFoxConfig {
    private static final String API_INFO_TITLE = "SOCIAL NETWORK REST API DOCUMENT";
    private static final String API_INFO_DESCRIPTION = "Some custom description of API.";
    private static final String API_INFO_VERSION = "1.0";
    private static final String API_INFO_NAME = "Hrinkov Sergey";
    private static final String API_INFO_EMAIL = "hrinkovsergey@yandex.ru";
    private static final String API_INFO_WEBSITE = null;
    private static final String API_INFO_TERMS_OF_SERVICE_URL = null;
    private static final String API_INFO_LICENSE = null;
    private static final String API_INFO_LICENSE_URL = null;
    private static final String API_KEY_NAME = "JWT";
    private static final String API_KEY_PASS_AS = "header";
    private static final String AUTHORIZATION_SCOPE = "global";
    private static final int AUTHORIZATION_SCOPE_ARRAY_ELEMENTS_NUMBER = 1;
    private static final int AUTHORIZATION_SCOPE_ARRAY_ELEMENT_NUMBER = 0;
    private static final String AUTHORIZATION_SCOPE_DESCRIPTION = "accessEverything";
    private static final String SECURITY_REFERENCE = "JWT";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                API_INFO_TITLE, API_INFO_DESCRIPTION, API_INFO_VERSION, API_INFO_TERMS_OF_SERVICE_URL, new Contact(
                API_INFO_NAME, API_INFO_WEBSITE, API_INFO_EMAIL), API_INFO_LICENSE, API_INFO_LICENSE_URL,
                Collections.emptyList());
    }

    private ApiKey apiKey() {
        return new ApiKey(API_KEY_NAME, HttpHeaders.AUTHORIZATION, API_KEY_PASS_AS);
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(
                AUTHORIZATION_SCOPE, AUTHORIZATION_SCOPE_DESCRIPTION);
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[AUTHORIZATION_SCOPE_ARRAY_ELEMENTS_NUMBER];
        authorizationScopes[AUTHORIZATION_SCOPE_ARRAY_ELEMENT_NUMBER] = authorizationScope;
        return Collections.singletonList(new SecurityReference(SECURITY_REFERENCE, authorizationScopes));
    }

}
