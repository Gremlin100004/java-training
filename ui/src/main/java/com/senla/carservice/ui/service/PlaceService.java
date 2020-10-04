package com.senla.carservice.ui.service;

import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.PlaceDto;
import com.senla.carservice.ui.util.ExceptionUtil;
import com.senla.carservice.ui.util.StringPlaces;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@NoArgsConstructor
public class PlaceService {
    private static final String ADD_PLACE_PATH = "places";
    private static final String CHECK_PLACES_PATH = "places/check";
    private static final String GET_PLACES_PATH = "places";
    private static final String DELETE_PLACE_PATH = "places/";
    private static final String GET_FREE_PLACES_BY_DATE_PATH = "places/free-by-date";
    @Autowired
    private RestTemplate restTemplate;
    @Value("${carservice.connection.url:http://localhost:8080/}")
    private String connectionUrl;

    public String addPlace(PlaceDto placeDto) {
        try {
            ResponseEntity<PlaceDto> response = restTemplate.postForEntity(
                connectionUrl + ADD_PLACE_PATH, placeDto, PlaceDto.class);
            PlaceDto receivedPlaceDto = response.getBody();
            if (receivedPlaceDto == null) {
                return "There are no message from server";
            }
            return "Place added successfully";
        } catch (HttpClientErrorException.Conflict exception) {
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String checkPlaces() {
        try {
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                connectionUrl + CHECK_PLACES_PATH, ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return "There are no message from server";
            }
            return clientMessageDto.getMessage();
        } catch (HttpClientErrorException.Conflict exception) {
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public List<String> getPlaces() {
        List<String> requiredList = new ArrayList<>();
        try {
            ResponseEntity<PlaceDto[]> response = restTemplate.getForEntity(
                connectionUrl + GET_PLACES_PATH, PlaceDto[].class);
            PlaceDto[] arrayPlaceDto = response.getBody();
            if (arrayPlaceDto == null) {
                requiredList.add("There are no message from server");
                return requiredList;
            }
            List<PlaceDto> placesDto = Arrays.asList(arrayPlaceDto);
            requiredList.add(StringPlaces.getStringFromPlaces(placesDto));
            requiredList.addAll(StringPlaces.getListId(placesDto));
            return requiredList;
        } catch (HttpClientErrorException.Conflict exception) {
            requiredList.add(ExceptionUtil.getMessageFromException(exception));
            return requiredList;
        }
    }

    public String deletePlace(Long idPlace) {
        try {
            restTemplate.delete(connectionUrl + DELETE_PLACE_PATH + idPlace, PlaceDto.class);
            return "The place has been deleted successfully";
        } catch (HttpClientErrorException.Conflict exception) {
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getFreePlacesByDate(String stringExecuteDate) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(connectionUrl + GET_FREE_PLACES_BY_DATE_PATH)
                .queryParam("stringExecuteDate", stringExecuteDate);
            ResponseEntity<PlaceDto[]> response = restTemplate.getForEntity(builder.toUriString(), PlaceDto[].class);
            PlaceDto[] arrayPlacesDto = response.getBody();
            if (arrayPlacesDto == null) {
                return "There are no message from server";
            }
            return StringPlaces.getStringFromPlaces(Arrays.asList(arrayPlacesDto));
        } catch (HttpClientErrorException.Conflict exception) {
            return ExceptionUtil.getMessageFromException(exception);
        }
    }
}