package com.senla.carservice.ui.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.carservice.dto.ClientMessageDto;
import org.springframework.web.client.HttpClientErrorException;

public class ExceptionUtil {

    public static String getMessageFromException(HttpClientErrorException.Conflict exception){
        ClientMessageDto clientMessageDto;
        try {
            clientMessageDto = new ObjectMapper().readValue(exception.getResponseBodyAsString(), ClientMessageDto.class);
            if (clientMessageDto == null) {
                return "There are no message from server";
            }
            return clientMessageDto.getMessage();
        } catch (JsonProcessingException e) {
            return "Server message error";
        }
    }
}