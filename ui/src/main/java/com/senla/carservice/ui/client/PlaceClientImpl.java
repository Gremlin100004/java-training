package com.senla.carservice.ui.client;

import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.PlaceDto;
import com.senla.carservice.ui.exception.BusinessException;
import com.senla.carservice.ui.util.ExceptionUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Component
@NoArgsConstructor
@Slf4j
public class PlaceClientImpl implements PlaceClient {
    private static final String ADD_PLACE_PATH = "places";
    private static final String CHECK_PLACES_PATH = "places/check";
    private static final String GET_PLACES_PATH = "places";
    private static final String DELETE_PLACE_PATH = "places/";
    private static final String GET_FREE_PLACES_BY_DATE_PATH = "places/freeByDate";
    private static final String REQUEST_PARAMETER_STRING_EXECUTION_DATE = "stringExecuteDate";
    private static final String WARNING_SERVER_MESSAGE = "There are no message from server";
    private static final String PLACE_ADD_SUCCESS_MESSAGE = "Place added successfully";
    private static final String PLACE_DELETE_SUCCESS_MESSAGE = "The place has been deleted successfully";
    @Autowired
    private RestTemplate restTemplate;
    @Value("${carservice.connection.url:http://localhost:8080/}")
    private String connectionUrl;

    @Override
    public String addPlace(int numberPlace) {
        log.debug("Method getOrderMasters");
        log.trace("Parameter numberPlace: {}", numberPlace);
        try {
            PlaceDto placeDto = new PlaceDto();
            placeDto.setNumber(numberPlace);
            ResponseEntity<PlaceDto> response = restTemplate.postForEntity(
                connectionUrl + ADD_PLACE_PATH, placeDto, PlaceDto.class);
            PlaceDto receivedPlaceDto = response.getBody();
            if (receivedPlaceDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return PLACE_ADD_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    @Override
    public String checkPlaces() {
        log.debug("Method checkPlaces");
        try {
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                connectionUrl + CHECK_PLACES_PATH, ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return clientMessageDto.getMessage();
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    @Override
    public List<PlaceDto> getPlaces() {
        log.debug("Method getPlaces");
        try {
            ResponseEntity<PlaceDto[]> response = restTemplate.getForEntity(
                connectionUrl + GET_PLACES_PATH, PlaceDto[].class);
            PlaceDto[] arrayPlaceDto = response.getBody();
            if (arrayPlaceDto == null) {
                throw new BusinessException("Error, there are no places");
            }
            return Arrays.asList(arrayPlaceDto);
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessageFromException(exception));
        }
    }

    @Override
    public String deletePlace(Long idPlace) {
        log.debug("Method deletePlace");
        log.trace("Parameter idPlace: {}", idPlace);
        try {
            restTemplate.delete(connectionUrl + DELETE_PLACE_PATH + idPlace, PlaceDto.class);
            return PLACE_DELETE_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    @Override
    public List<PlaceDto> getFreePlacesByDate(String stringExecuteDate) {
        log.debug("Method getFreePlacesByDate");
        log.trace("Parameter stringExecuteDate: {}", stringExecuteDate);
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(connectionUrl + GET_FREE_PLACES_BY_DATE_PATH)
                .queryParam(REQUEST_PARAMETER_STRING_EXECUTION_DATE, stringExecuteDate);
            ResponseEntity<PlaceDto[]> response = restTemplate.getForEntity(builder.toUriString(), PlaceDto[].class);
            PlaceDto[] arrayPlacesDto = response.getBody();
            if (arrayPlacesDto == null) {
                throw new BusinessException("Error, there are no places");
            }
            return Arrays.asList(arrayPlacesDto);
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessageFromException(exception));
        }
    }

}
