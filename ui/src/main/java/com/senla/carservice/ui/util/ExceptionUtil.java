package com.senla.carservice.ui.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.carservice.dto.ClientMessageDto;
import org.springframework.web.client.HttpClientErrorException;

public class ExceptionUtil {
    private final static String ERROR_MESSAGE = "Server message error";

    public static String getMessage(HttpClientErrorException exception, ObjectMapper objectMapper) {
        ClientMessageDto clientMessageDto;
        try {
            clientMessageDto = objectMapper.readValue(exception.getResponseBodyAsString(), ClientMessageDto.class);
            return clientMessageDto.getMessage();
        } catch (JsonProcessingException e) {
            return ERROR_MESSAGE;
        }
    }

}
