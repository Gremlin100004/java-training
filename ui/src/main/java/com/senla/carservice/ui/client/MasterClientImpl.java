package com.senla.carservice.ui.client;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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
    private static final String GET_SORT_MASTERS = "masters?sortParameter=";
    private static final String GET_FREE_MASTERS_PATH = "masters?stringExecuteDate=";
    private static final String GET_MASTER_ORDERS_START_PATH = "masters/";
    private static final String GET_MASTER_ORDERS_END_PATH = "/orders";
    private static final String WARNING_SERVER_MESSAGE = "There are no message from server";
    private static final String MASTER_ADD_SUCCESS_MESSAGE = "Master added successfully";
    private static final String MASTER_DELETE_SUCCESS_MESSAGE = "The master has been deleted successfully";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<MasterDto> getMasters() {
        log.debug("[getMasters]");
        try {
            ResponseEntity<MasterDto[]> response = restTemplate.getForEntity(GET_MASTERS_PATH, MasterDto[].class);
            MasterDto[] arrayMasterDto = response.getBody();
            if (arrayMasterDto == null) {
                throw new BusinessException("Error, there are no masters");
            }
            return Arrays.asList(arrayMasterDto);
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public String addMaster(String name) {
        log.debug("[addMaster]");
        log.trace("[name: {}]", name);
        try {
            MasterDto masterDto = new MasterDto();
            masterDto.setName(name);
            ResponseEntity<MasterDto> response = restTemplate.postForEntity(ADD_MASTER_PATH, masterDto, MasterDto.class);
            MasterDto receivedMasterDto = response.getBody();
            if (receivedMasterDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return MASTER_ADD_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public String checkMasters() {
        log.debug("[checkMasters]");
        try {
            ResponseEntity<ClientMessageDto> response = restTemplate.getForEntity(
                CHECK_MASTERS_PATH, ClientMessageDto.class);
            ClientMessageDto clientMessageDto = response.getBody();
            if (clientMessageDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return clientMessageDto.getMessage();
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public String deleteMaster(Long idMaster) {
        log.debug("[deleteMaster]");
        log.trace("[idMaster: {}]", idMaster);
        try {
            restTemplate.delete(DELETE_MASTER_PATH + idMaster, MasterDto.class);
            return MASTER_DELETE_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public String getSortMasters(String sortParameter) {
        log.debug("[getSortMasters]");
        log.trace("[sortParameter: {}]", sortParameter);
        try {
            ResponseEntity<MasterDto[]> response = restTemplate.getForEntity(
                GET_SORT_MASTERS + sortParameter, MasterDto[].class);
            MasterDto[] arrayMastersDto = response.getBody();
            if (arrayMastersDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return StringMaster.getStringFromMasters(Arrays.asList(arrayMastersDto));
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public List<MasterDto> getFreeMasters(String stringExecuteDate) {
        log.debug("[getFreeMasters]");
        log.trace("[stringExecuteDate: {}]", stringExecuteDate);
        try {
            ResponseEntity<MasterDto[]> response = restTemplate.getForEntity(
                GET_FREE_MASTERS_PATH + stringExecuteDate, MasterDto[].class);
            MasterDto[] arrayMastersDto = response.getBody();
            if (arrayMastersDto == null) {
                throw new BusinessException("Error, there are no masters");
            }
            return Arrays.asList(arrayMastersDto);
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public String getMasterOrders(Long masterId) {
        log.debug("[getMasterOrders]");
        log.trace("[masterId: {}]", masterId);
        try {
            ResponseEntity<OrderDto[]> response = restTemplate.getForEntity(
                    GET_MASTER_ORDERS_START_PATH + masterId + GET_MASTER_ORDERS_END_PATH, OrderDto[].class);
            OrderDto[] arrayOrdersDto = response.getBody();
            if (arrayOrdersDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            List<OrderDto> ordersDto = Arrays.asList(arrayOrdersDto);
            return StringOrder.getStringFromOrder(ordersDto);
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

}
