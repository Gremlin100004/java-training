package com.senla.carservice.ui.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.LongDto;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
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
public class MasterClientImpl implements MasterClient {
    private static final String GET_MASTERS_PATH = "masters";
    private static final String GET_NUMBER_MASTERS_PATH = "masters/numberMasters";
    private static final String GET_NUMBER_FREE_MASTERS_PATH = "masters/numberMasters?date=";
    private static final String ADD_MASTER_PATH = "masters";
    private static final String DELETE_MASTER_PATH = "masters/";
    private static final String GET_SORT_MASTERS = "masters?sortParameter=";
    private static final String GET_FREE_MASTERS_PATH = "masters?stringExecuteDate=";
    private static final String GET_MASTER_ORDERS_START_PATH = "masters/";
    private static final String GET_MASTER_ORDERS_END_PATH = "/orders";
    private static final String WARNING_SERVER_MESSAGE = "There are no message from server";
    private static final String MASTER_ADD_SUCCESS_MESSAGE = "Master added successfully";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpHeaders httpHeaders;

    @Override
    public List<MasterDto> getMasters() {
        log.debug("[getMasters]");
        try {
            ParameterizedTypeReference<List<MasterDto>> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<List<MasterDto>> response = restTemplate.exchange(
                GET_MASTERS_PATH, HttpMethod.GET, new HttpEntity<>(httpHeaders), beanType);
            List<MasterDto> listMastersDto = response.getBody();
            if (listMastersDto == null) {
                throw new BusinessException("Error, there are no masters");
            }
            return listMastersDto;
        } catch (HttpClientErrorException exception) {
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
            ParameterizedTypeReference<MasterDto> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<MasterDto> response = restTemplate.exchange(
                ADD_MASTER_PATH, HttpMethod.POST, new HttpEntity<>(httpHeaders), beanType, masterDto);
            MasterDto receivedMasterDto = response.getBody();
            if (receivedMasterDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return MASTER_ADD_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public String deleteMaster(Long idMaster) {
        log.debug("[deleteMaster]");
        log.trace("[idMaster: {}]", idMaster);
        try {
            ParameterizedTypeReference<ClientMessageDto> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<ClientMessageDto> response = restTemplate.exchange(
                DELETE_MASTER_PATH + idMaster, HttpMethod.DELETE, new HttpEntity<>(httpHeaders), beanType);
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
    public List<MasterDto> getSortMasters(String sortParameter) {
        log.debug("[getSortMasters]");
        log.trace("[sortParameter: {}]", sortParameter);
        try {
            ParameterizedTypeReference<List<MasterDto>> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<List<MasterDto>> response = restTemplate.exchange(
                GET_SORT_MASTERS + sortParameter, HttpMethod.GET, new HttpEntity<>(httpHeaders), beanType);
            List<MasterDto> listMastersDto = response.getBody();
            if (listMastersDto == null) {
                throw new BusinessException("Error, there are no masters");
            }
            return listMastersDto;
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public Long getNumberMasters() {
        log.debug("[getNumberMasters]");
        try {
            ParameterizedTypeReference<LongDto> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<LongDto> response = restTemplate.exchange(
                GET_NUMBER_MASTERS_PATH, HttpMethod.GET, new HttpEntity<>(httpHeaders), beanType);
            LongDto longDto = response.getBody();
            if (longDto == null) {
                throw new BusinessException("Error, there are no number");
            }
            return longDto.getNumber();
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public Long getNumberFreeMasters(String date) {
        log.debug("[getNumberFreePlace]");
        log.debug("[date: {}]", date);
        try {
            ParameterizedTypeReference<LongDto> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<LongDto> response = restTemplate.exchange(
                GET_NUMBER_FREE_MASTERS_PATH + date, HttpMethod.GET, new HttpEntity<>(httpHeaders), beanType);
            LongDto longDto = response.getBody();
            if (longDto == null) {
                throw new BusinessException("Error, there are no number");
            }
            return longDto.getNumber();
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public List<MasterDto> getFreeMasters(String stringExecuteDate) {
        log.debug("[getFreeMasters]");
        log.trace("[stringExecuteDate: {}]", stringExecuteDate);
        try {
            ParameterizedTypeReference<List<MasterDto>> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<List<MasterDto>> response = restTemplate.exchange(
                GET_FREE_MASTERS_PATH + stringExecuteDate, HttpMethod.GET, new HttpEntity<>(httpHeaders), beanType);
            List<MasterDto> listMastersDto = response.getBody();
            if (listMastersDto == null) {
                throw new BusinessException("Error, there are no masters");
            }
            return listMastersDto;
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public List<OrderDto> getMasterOrders(Long masterId) {
        log.debug("[getMasterOrders]");
        log.trace("[masterId: {}]", masterId);
        try {
            ParameterizedTypeReference<List<OrderDto>> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<List<OrderDto>> response = restTemplate.exchange(
                GET_MASTER_ORDERS_START_PATH + masterId + GET_MASTER_ORDERS_END_PATH, HttpMethod.GET,
                new HttpEntity<>(httpHeaders), beanType);
            List<OrderDto> listOrdersDto = response.getBody();
            if (listOrdersDto == null) {
                throw new BusinessException("Error, there are no orders");
            }
            return listOrdersDto;
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

}
