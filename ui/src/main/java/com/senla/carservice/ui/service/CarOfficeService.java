package com.senla.carservice.ui.service;

import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.ui.util.ExceptionUtil;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@NoArgsConstructor
public class CarOfficeService {
    @Value("${carservice.connection.url:http://localhost:8080/}")
    private String connectionUrl;
    private static final String GET_FREE_PLACES_MASTERS_BY_DATE_PATH = "number-of-free-places";
    private static final String GET_NEAREST_FREE_DATE_PATH = "nearest-free-date";
    private static final String EXPORT_ENTITIES_PATH = "export";
    private static final String IMPORT_ENTITIES_PATH = "import";
    @Autowired
    private RestTemplate restTemplate;

    public String getFreePlacesMastersByDate(String date) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                connectionUrl + GET_FREE_PLACES_MASTERS_BY_DATE_PATH)
                    .queryParam("date", date);
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                builder.toUriString(), ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return "There are no message from server";
            }
            return clientMessageDto.getMessage();
        } catch (HttpClientErrorException.Conflict exception) {
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getNearestFreeDate() {
        try {
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                connectionUrl + GET_NEAREST_FREE_DATE_PATH, ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return "There are no message from server";
            }
            return clientMessageDto.getMessage();
        } catch (HttpClientErrorException.Conflict exception) {
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String exportEntities() {
        try {
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                connectionUrl + EXPORT_ENTITIES_PATH, ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return "There are no message from server";
            }
            return clientMessageDto.getMessage();
        } catch (HttpClientErrorException.Conflict exception) {
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String importEntities() {
        try {
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                connectionUrl + IMPORT_ENTITIES_PATH, ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return "There are no message from server";
            }
            return clientMessageDto.getMessage();
        } catch (HttpClientErrorException.Conflict exception) {
            return ExceptionUtil.getMessageFromException(exception);
        }
    }
}
