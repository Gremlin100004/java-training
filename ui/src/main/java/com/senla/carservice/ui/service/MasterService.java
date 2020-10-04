package com.senla.carservice.ui.service;

import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.ui.util.ExceptionUtil;
import com.senla.carservice.ui.util.StringMaster;
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
public class MasterService {
    private static final String GET_MASTERS_PATH = "masters";
    private static final String ADD_MASTER_PATH = "masters";
    private static final String CHECK_MASTERS_PATH = "masters/check";
    private static final String DELETE_MASTER_PATH = "masters/";
    private static final String GET_MASTER_BY_ALPHABET_PATH = "masters/sort-by-alphabet";
    private static final String GET_MASTER_BY_BUSY_PATH = "masters/sort-by-busy";
    private static final String GET_FREE_MASTERS_PATH = "masters/free";
    @Autowired
    private RestTemplate restTemplate;
    @Value("${carservice.connection.url:http://localhost:8080/}")
    private String connectionUrl;

    public List<String> getMasters() {
        List<String> requiredList = new ArrayList<>();
        try {
            ResponseEntity<MasterDto[]> response = restTemplate.getForEntity(
                connectionUrl + GET_MASTERS_PATH, MasterDto[].class);
            MasterDto[] arrayMasterDto = response.getBody();
            if (arrayMasterDto == null) {
                requiredList.add("There are no message from server");
                return requiredList;
            }
            List<MasterDto> mastersDto = Arrays.asList(arrayMasterDto);
            requiredList.add(StringMaster.getStringFromMasters(mastersDto));
            requiredList.addAll(StringMaster.getListId(mastersDto));
            return requiredList;
        } catch (HttpClientErrorException.Conflict exception) {
            requiredList.add(ExceptionUtil.getMessageFromException(exception));
            return requiredList;
        }
    }

    public String addMaster(MasterDto masterDto) {
        try {
            ResponseEntity<MasterDto> response = restTemplate.postForEntity(
                connectionUrl + ADD_MASTER_PATH, masterDto, MasterDto.class);
            MasterDto receivedMasterDto = response.getBody();
            if (receivedMasterDto == null) {
                return "There are no message from server";
            }
            return "Master added successfully";
        } catch (HttpClientErrorException.Conflict exception) {
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String checkMasters() {
        try {
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                connectionUrl + CHECK_MASTERS_PATH, ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return "There are no message from server";
            }
            return clientMessageDto.getMessage();
        } catch (HttpClientErrorException.Conflict exception) {
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String deleteMaster(Long idMaster) {
        try {
            restTemplate.delete(connectionUrl + DELETE_MASTER_PATH + idMaster, MasterDto.class);
            return "The master has been deleted successfully";
        } catch (HttpClientErrorException.Conflict exception) {
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getMasterByAlphabet() {
        try {
            ResponseEntity<MasterDto[]> response = restTemplate.getForEntity(
                connectionUrl + GET_MASTER_BY_ALPHABET_PATH, MasterDto[].class);
            MasterDto[] arrayMastersDto = response.getBody();
            if (arrayMastersDto == null) {
                return "There are no message from server";
            }
            return StringMaster.getStringFromMasters(Arrays.asList(arrayMastersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getMasterByBusy() {
        try {
            ResponseEntity<MasterDto[]> response = restTemplate.getForEntity(
                connectionUrl + GET_MASTER_BY_BUSY_PATH, MasterDto[].class);
            MasterDto[] arrayMastersDto = response.getBody();
            if (arrayMastersDto == null) {
                return "There are no message from server";
            }
            return StringMaster.getStringFromMasters(Arrays.asList(arrayMastersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    public String getFreeMasters(String stringExecuteDate) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(connectionUrl + GET_FREE_MASTERS_PATH)
                .queryParam("stringExecuteDate", stringExecuteDate);
            ResponseEntity<MasterDto[]> response = restTemplate.getForEntity(builder.toUriString(), MasterDto[].class);
            MasterDto[] arrayMastersDto = response.getBody();
            if (arrayMastersDto == null) {
                return "There are no message from server";
            }
            return StringMaster.getStringFromMasters(Arrays.asList(arrayMastersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            return ExceptionUtil.getMessageFromException(exception);
        }
    }
}