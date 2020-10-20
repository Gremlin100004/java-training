package com.senla.carservice.ui.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.UserDto;
import com.senla.carservice.ui.exception.BusinessException;
import com.senla.carservice.ui.util.ExceptionUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@NoArgsConstructor
@Slf4j
public class UserClientImpl implements UserClient {
    private static final String GET_USERS_PATH = "users";
    private static final String REGISTRATION_USERS_PATH = "users/registration";
    private static final String AUTHORIZATION_USERS_PATH = "users/authorization";
    private static final String DELETE_USERS_PATH = "users/";
    private static final String WARNING_SERVER_MESSAGE = "There are no message from server";
    private static final String ADD_USER_SUCCESS_MESSAGE = "User registered successfully";
    private static final String AUTHORIZATION_USER_SUCCESS_MESSAGE = "Welcome user: ";
    private static final String USER_DELETE_SUCCESS_MESSAGE = "The user has been deleted successfully";
    private static final String HEADER_NAME_AUTHORIZATION = "Authorization";
    private static final String TOKEN_TYPE = "Bearer ";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpHeaders httpHeaders;

    @Override
    public List<UserDto> getUsers() {
        log.debug("[getUsers]");
        try {
            ParameterizedTypeReference<List<UserDto>> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<List<UserDto>> response = restTemplate.exchange(
                GET_USERS_PATH, HttpMethod.GET, new HttpEntity<>(httpHeaders), beanType);
            return response.getBody();
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public String addUser(UserDto userDto) {
        log.debug("[addUser]");
        try {
            ResponseEntity<UserDto> response = restTemplate.postForEntity(
                REGISTRATION_USERS_PATH, userDto, UserDto.class);
            UserDto receivedUserDto = response.getBody();
            if (receivedUserDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return ADD_USER_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public String logIn(UserDto userDto) {
        log.debug("[logIn]");
        try {
            ResponseEntity<ClientMessageDto> response = restTemplate.postForEntity(
                AUTHORIZATION_USERS_PATH, userDto, ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            httpHeaders.keySet().clear();
            httpHeaders.add(HEADER_NAME_AUTHORIZATION, TOKEN_TYPE + clientMessageDto.getMessage());
            return AUTHORIZATION_USER_SUCCESS_MESSAGE + userDto.getEmail();
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public String deletePlace(Long idUser) {
        log.debug("[logIn]");
        log.trace("[idUser: {}]", idUser);
        try {
            restTemplate.exchange(
                DELETE_USERS_PATH + idUser, HttpMethod.DELETE, new HttpEntity<>(httpHeaders), UserDto.class);
            return USER_DELETE_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

}