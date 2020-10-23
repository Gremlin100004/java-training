package com.senla.carservice.ui.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.LongDto;
import com.senla.carservice.dto.PlaceDto;
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
public class PlaceClientImpl implements PlaceClient {
    private static final String ADD_PLACE_PATH = "places";
    private static final String GET_PLACES_PATH = "places";
    private static final String GET_FREE_PLACES_PATH = "places/?stringExecuteDate=";
    private static final String GET_NUMBER_FREE_PLACES_PATH = "places/numberPlaces?date=";
    private static final String GET_NUMBER_PLACES_PATH = "places/numberPlaces";
    private static final String DELETE_PLACE_PATH = "places/";
    private static final String WARNING_SERVER_MESSAGE = "There are no message from server";
    private static final String PLACE_ADD_SUCCESS_MESSAGE = "Place added successfully";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpHeaders httpHeaders;

    @Override
    public String addPlace(int numberPlace) {
        log.debug("[getOrderMasters]");
        log.trace("[numberPlace: {}]", numberPlace);
        try {
            PlaceDto placeDto = new PlaceDto();
            placeDto.setNumber(numberPlace);
            ResponseEntity<PlaceDto> response = restTemplate.exchange(
                ADD_PLACE_PATH, HttpMethod.POST, new HttpEntity<>(placeDto, httpHeaders),  PlaceDto.class);
            PlaceDto receivedPlaceDto = response.getBody();
            if (receivedPlaceDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return PLACE_ADD_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public List<PlaceDto> getPlaces() {
        log.debug("[getPlaces]");
        try {
            ParameterizedTypeReference<List<PlaceDto>> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<List<PlaceDto>> response = restTemplate.exchange(
                GET_PLACES_PATH, HttpMethod.GET, new HttpEntity<>(httpHeaders), beanType);
            List<PlaceDto> listPlaceDto = response.getBody();
            if (listPlaceDto == null) {
                throw new BusinessException("Error, there are no places");
            }
            return listPlaceDto;
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public String deletePlace(Long idPlace) {
        log.debug("[deletePlace]");
        log.trace("[idPlace: {}]", idPlace);
        try {
            ResponseEntity<ClientMessageDto> response = restTemplate.exchange(
                DELETE_PLACE_PATH + idPlace, HttpMethod.DELETE, new HttpEntity<>(httpHeaders), ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return clientMessageDto.getMessage();
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public Long getNumberPlace() {
        log.debug("[getNumberPlace]");
        try {
            ParameterizedTypeReference<LongDto> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<LongDto> response = restTemplate.exchange(
                GET_NUMBER_PLACES_PATH, HttpMethod.GET, new HttpEntity<>(httpHeaders), beanType);
            LongDto numberPlace = response.getBody();
            if (numberPlace == null) {
                throw new BusinessException("Error, there are no number");
            }
            return numberPlace.getNumber();
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public Long getNumberFreePlace(String date) {
        log.debug("[getNumberFreePlace]");
        log.debug("[date: {}]", date);
        try {
            ParameterizedTypeReference<LongDto> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<LongDto> response = restTemplate.exchange(
                GET_NUMBER_FREE_PLACES_PATH + date, HttpMethod.GET, new HttpEntity<>(httpHeaders), beanType);
            LongDto numberPlace = response.getBody();
            if (numberPlace == null) {
                throw new BusinessException("Error, there are no number");
            }
            return numberPlace.getNumber();
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public List<PlaceDto> getFreePlacesByDate(String stringExecuteDate) {
        log.debug("[getFreePlacesByDate]");
        log.trace("[stringExecuteDate: {}]", stringExecuteDate);
        try {
            ParameterizedTypeReference<List<PlaceDto>> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<List<PlaceDto>> response = restTemplate.exchange(
                GET_FREE_PLACES_PATH + stringExecuteDate, HttpMethod.GET, new HttpEntity<>(httpHeaders), beanType);
            List<PlaceDto> listPlaceDto = response.getBody();
            if (listPlaceDto == null) {
                throw new BusinessException("Error, there are no places");
            }
            return listPlaceDto;
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

}
