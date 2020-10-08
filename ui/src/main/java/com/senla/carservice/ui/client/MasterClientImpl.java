package com.senla.carservice.ui.client;

import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.ui.exception.BusinessException;
import com.senla.carservice.ui.util.ExceptionUtil;
import com.senla.carservice.ui.util.StringMaster;
import com.senla.carservice.ui.util.StringOrder;
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
public class MasterClientImpl implements MasterClient {
    private static final String GET_MASTERS_PATH = "masters";
    private static final String ADD_MASTER_PATH = "masters";
    private static final String CHECK_MASTERS_PATH = "masters/check";
    private static final String DELETE_MASTER_PATH = "masters/";
    private static final String GET_MASTER_BY_ALPHABET_PATH = "masters/sort/byAlphabet";
    private static final String GET_MASTER_BY_BUSY_PATH = "masters/sort/byBusy";
    private static final String GET_FREE_MASTERS_PATH = "masters/free";
    private static final String GET_MASTER_ORDERS_START_PATH = "orders/";
    private static final String GET_MASTER_ORDERS_END_PATH = "/master/orders";
    private static final String WARNING_SERVER_MESSAGE = "There are no message from server";
    private static final String MASTER_ADD_SUCCESS_MESSAGE = "Master added successfully";
    private static final String MASTER_DELETE_SUCCESS_MESSAGE = "The master has been deleted successfully";
    private static final String REQUEST_PARAMETER_DATE = "stringExecuteDate";
    @Autowired
    private RestTemplate restTemplate;
    @Value("${carservice.connection.url:http://localhost:8080/}")
    private String connectionUrl;

    @Override
    public List<MasterDto> getMasters() {
        log.debug("Method getMasters");
        try {
            ResponseEntity<MasterDto[]> response = restTemplate.getForEntity(
                connectionUrl + GET_MASTERS_PATH, MasterDto[].class);
            MasterDto[] arrayMasterDto = response.getBody();
            if (arrayMasterDto == null) {
                throw new BusinessException("Error, there are no masters");
            }
            return Arrays.asList(arrayMasterDto);
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessageFromException(exception));
        }
    }

    @Override
    public String addMaster(String name) {
        log.debug("Method addMaster");
        log.trace("Parameter name: {}", name);
        try {
            MasterDto masterDto = new MasterDto();
            masterDto.setName(name);
            ResponseEntity<MasterDto> response = restTemplate.postForEntity(
                connectionUrl + ADD_MASTER_PATH, masterDto, MasterDto.class);
            MasterDto receivedMasterDto = response.getBody();
            if (receivedMasterDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return MASTER_ADD_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    @Override
    public String checkMasters() {
        log.debug("Method checkMasters");
        try {
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                connectionUrl + CHECK_MASTERS_PATH, ClientMessageDto.class);
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
    public String deleteMaster(Long idMaster) {
        log.debug("Method deleteMaster");
        log.trace("Parameter idMaster: {}", idMaster);
        try {
            restTemplate.delete(connectionUrl + DELETE_MASTER_PATH + idMaster, MasterDto.class);
            return MASTER_DELETE_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    @Override
    public String getMasterByAlphabet() {
        log.debug("Method getMasterByAlphabet");
        try {
            ResponseEntity<MasterDto[]> response = restTemplate.getForEntity(
                GET_MASTER_BY_ALPHABET_PATH, MasterDto[].class);
            MasterDto[] arrayMastersDto = response.getBody();
            if (arrayMastersDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return StringMaster.getStringFromMasters(Arrays.asList(arrayMastersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    @Override
    public String getMasterByBusy() {
        log.debug("Method getMasterByBusy");
        try {
            ResponseEntity<MasterDto[]> response = restTemplate.getForEntity(
                connectionUrl + GET_MASTER_BY_BUSY_PATH, MasterDto[].class);
            MasterDto[] arrayMastersDto = response.getBody();
            if (arrayMastersDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return StringMaster.getStringFromMasters(Arrays.asList(arrayMastersDto));
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

    @Override
    public List<MasterDto> getFreeMasters(String stringExecuteDate) {
        log.debug("Method getFreeMasters");
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(connectionUrl + GET_FREE_MASTERS_PATH)
                .queryParam(REQUEST_PARAMETER_DATE, stringExecuteDate);
            ResponseEntity<MasterDto[]> response = restTemplate.getForEntity(builder.toUriString(), MasterDto[].class);
            MasterDto[] arrayMastersDto = response.getBody();
            if (arrayMastersDto == null) {
                throw new BusinessException("Error, there are no masters");
            }
            return Arrays.asList(arrayMastersDto);
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessageFromException(exception));
        }
    }

    @Override
    public String getMasterOrders(Long masterId) {
        log.debug("Method getMasterOrders");
        log.trace("Parameter masterId: {}", masterId);
        try {
            ResponseEntity<OrderDto[]> response = restTemplate.getForEntity(
                    connectionUrl + GET_MASTER_ORDERS_START_PATH + masterId + GET_MASTER_ORDERS_END_PATH, OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            List<OrderDto> ordersDto = Arrays.asList(arrayOrdersDto);
            return StringOrder.getStringFromOrder(ordersDto);
        } catch (HttpClientErrorException.Conflict exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessageFromException(exception);
        }
    }

}
