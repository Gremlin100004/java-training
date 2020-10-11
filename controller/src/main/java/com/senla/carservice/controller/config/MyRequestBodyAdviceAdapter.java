package com.senla.carservice.controller.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

@ControllerAdvice
public class MyRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {
    @Override
    public boolean supports(
        final MethodParameter methodParameter, final Type type, final Class<? extends HttpMessageConverter<?>> aClass) {
        return false;
    }

    @Override
public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                            Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

    return body;
    }
}

