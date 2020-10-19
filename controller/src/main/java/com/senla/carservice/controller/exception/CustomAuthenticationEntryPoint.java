package com.senla.carservice.controller.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.carservice.dto.ClientMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) {
        try {
            res.setContentType(CONTENT_TYPE);
            res.setStatus(403);
            res.getWriter().write(objectMapper.writeValueAsString(new ClientMessageDto("Permission denied")));
        } catch (IOException e) {
            throw new ControllerException("Server error");
        }
    }

}
