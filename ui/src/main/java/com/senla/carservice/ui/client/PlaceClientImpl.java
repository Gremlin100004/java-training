package com.senla.carservice.ui.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.carservice.dto.LongDto;
import com.senla.carservice.dto.PlaceDto;
import com.senla.carservice.ui.exception.BusinessException;
import com.senla.carservice.ui.util.ExceptionUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
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
    private static final String PLACE_DELETE_SUCCESS_MESSAGE = "The place has been deleted successfully";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String addPlace(int numberPlace) {
        log.debug("[getOrderMasters]");
        log.trace("[numberPlace: {}]", numberPlace);
        try {
            PlaceDto placeDto = new PlaceDto();
            placeDto.setNumber(numberPlace);
            ResponseEntity<PlaceDto> response = restTemplate.postForEntity(ADD_PLACE_PATH, placeDto, PlaceDto.class);
            PlaceDto receivedPlaceDto = response.getBody();
            if (receivedPlaceDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return PLACE_ADD_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public List<PlaceDto> getPlaces() {
        log.debug("[getPlaces]");
        try {
            ResponseEntity<PlaceDto[]> response = restTemplate.getForEntity(GET_PLACES_PATH, PlaceDto[].class);
            PlaceDto[] arrayPlaceDto = response.getBody();
            if (arrayPlaceDto == null) {
                throw new BusinessException("Error, there are no places");
            }
            return Arrays.asList(arrayPlaceDto);
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public String deletePlace(Long idPlace) {
        log.debug("[deletePlace]");
        log.trace("[idPlace: {}]", idPlace);
        try {
            restTemplate.delete(DELETE_PLACE_PATH + idPlace, PlaceDto.class);
            return PLACE_DELETE_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public Long getNumberPlace() {
        log.debug("[getNumberPlace]");
        try {
            ResponseEntity<LongDto> response = restTemplate.getForEntity(GET_NUMBER_PLACES_PATH, LongDto.class);
            LongDto longDto = response.getBody();
            if (longDto == null) {
                throw new BusinessException("Error, there are no number");
            }
            return longDto.getNumber();
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public Long getNumberFreePlace(String date) {
        log.debug("[getNumberFreePlace]");
        log.debug("[date: {}]", date);
        try {
            ResponseEntity<LongDto> response = restTemplate.getForEntity(
                GET_NUMBER_FREE_PLACES_PATH + date, LongDto.class);
            LongDto longDto = response.getBody();
            if (longDto == null) {
                throw new BusinessException("Error, there are no number");
            }
            return longDto.getNumber();
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public List<PlaceDto> getFreePlacesByDate(String stringExecuteDate) {
        log.debug("[getFreePlacesByDate]");
        log.trace("[stringExecuteDate: {}]", stringExecuteDate);
        try {
            ResponseEntity<PlaceDto[]> response = restTemplate.getForEntity(
                GET_FREE_PLACES_PATH + stringExecuteDate, PlaceDto[].class);
            PlaceDto[] arrayPlacesDto = response.getBody();
            if (arrayPlacesDto == null) {
                throw new BusinessException("Error, there are no places");
            }
            return Arrays.asList(arrayPlacesDto);
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

}
