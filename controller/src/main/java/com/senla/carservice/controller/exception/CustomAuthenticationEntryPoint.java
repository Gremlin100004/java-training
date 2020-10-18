package com.senla.carservice.controller.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.carservice.dto.ClientMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            res.setContentType("application/json;charset=UTF-8");
            res.setStatus(403);
            res.getWriter().write(mapper.writeValueAsString(new ClientMessageDto("Permission denied")));
        } catch (IOException e) {
            throw new ControllerException("Server error");
        }
    }

}
