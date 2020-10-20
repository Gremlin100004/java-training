package com.senla.carservice.ui.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.DateDto;
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
public class OrderClientImpl implements OrderClient {
    private static final String ADD_ORDER_PATH = "orders";
    private static final String GET_ORDERS_PATH = "orders";
    private static final String COMPLETE_ORDER_START_PATH = "orders/";
    private static final String COMPLETE_ORDER_END_PATH = "/complete";
    private static final String CLOSE_ORDER_START_PATH = "orders/";
    private static final String CLOSE_ORDER_END_PATH = "/close";
    private static final String CANCEL_ORDER_START_PATH = "orders/";
    private static final String CANCEL_ORDER_END_PATH = "/cancel";
    private static final String DELETE_ORDER_PATH = "orders/";
    private static final String SHIFT_LEAD_TIME_PATH = "orders/shiftLeadTime";
    private static final String GET_SORT_ORDERS_PATH = "orders?sortParameter=";
    private static final String PARAMETER_START_TIME = "&startPeriod=";
    private static final String PARAMETER_END_TIME = "&endPeriod=";
    private static final String GET_ORDER_MASTERS_START_PATH = "orders/";
    private static final String GET_ORDER_MASTERS_END_PATH = "/masters";
    private static final String WARNING_SERVER_MESSAGE = "There are no message from server";
    private static final String ORDER_TRANSFERRED_SUCCESS_MESSAGE = "The order has been transferred to execution status";
    private static final String ADD_ORDER_SUCCESS_MESSAGE = "Order added successfully";
    private static final String ORDER_COMPLETE_SUCCESS_MESSAGE = "The order has been completed successfully";
    private static final String ORDER_CANCEL_SUCCESS_MESSAGE = "The order has been canceled successfully";
    private static final String ORDER_DELETE_SUCCESS_MESSAGE = "The order has been deleted";
    private static final String ORDER_CHANGE_TIME_SUCCESS_MESSAGE = "The order lead time has been changed successfully";
    private static final String GET_NEAREST_FREE_DATE_PATH = "orders/freeDate";
    private static final String EXPORT_ENTITIES_PATH = "orders/csv/export";
    private static final String IMPORT_ENTITIES_PATH = "orders/csv/import";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpHeaders httpHeaders;

    @Override
    public String addOrder(OrderDto orderDto) {
        log.debug("[addOrder]");
        log.trace("[orderDto: {}]", orderDto);
        try {
            ParameterizedTypeReference<OrderDto> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<OrderDto> response = restTemplate.exchange(
                ADD_ORDER_PATH, HttpMethod.POST, new HttpEntity<>(httpHeaders), beanType, orderDto);
            OrderDto receivedOrderDto = response.getBody();
            if (receivedOrderDto == null) {
                return WARNING_SERVER_MESSAGE;
            }
            return ADD_ORDER_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public List<OrderDto> getOrders() {
        log.debug("[getOrders]");
        try {
            ParameterizedTypeReference<List<OrderDto>> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<List<OrderDto>> response = restTemplate.exchange(
                GET_ORDERS_PATH, HttpMethod.GET, new HttpEntity<>(httpHeaders), beanType);
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

    @Override
    public String completeOrder(Long idOrder) {
        log.debug("[completeOrder]");
        log.trace("[idOrder: {}]", idOrder);
        try {
            ParameterizedTypeReference<OrderDto> beanType = new ParameterizedTypeReference<>() { };
            restTemplate.exchange(
                COMPLETE_ORDER_START_PATH + idOrder + COMPLETE_ORDER_END_PATH, HttpMethod.PUT,
                new HttpEntity<>(httpHeaders), beanType);
            return ORDER_TRANSFERRED_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public String closeOrder(Long idOrder) {
        log.debug("[closeOrder]");
        log.trace("[idOrder: {}]", idOrder);
        try {
            ParameterizedTypeReference<OrderDto> beanType = new ParameterizedTypeReference<>() { };
            restTemplate.exchange(
                CLOSE_ORDER_START_PATH + idOrder + CLOSE_ORDER_END_PATH, HttpMethod.PUT, new HttpEntity<>(
                    httpHeaders), beanType);
            return ORDER_COMPLETE_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public String cancelOrder(Long idOrder) {
        log.debug("[cancelOrder]");
        log.trace("[idOrder: {}]", idOrder);
        try {
            ParameterizedTypeReference<OrderDto> beanType = new ParameterizedTypeReference<>() { };
            restTemplate.exchange(
                CANCEL_ORDER_START_PATH + idOrder + CANCEL_ORDER_END_PATH, HttpMethod.PUT, new HttpEntity<>(
                    httpHeaders), beanType);
            return ORDER_CANCEL_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public String deleteOrder(Long idOrder) {
        log.debug("[deleteOrder]");
        log.trace("[idOrder: {}]", idOrder);
        try {
            ParameterizedTypeReference<OrderDto> beanType = new ParameterizedTypeReference<>() { };
            restTemplate.exchange(
                DELETE_ORDER_PATH + idOrder, HttpMethod.DELETE, new HttpEntity<>(httpHeaders), beanType);
            return ORDER_DELETE_SUCCESS_MESSAGE;
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            return ExceptionUtil.getMessage(exception, objectMapper);
        }
    }

    @Override
    public String shiftLeadTime(OrderDto orderDto) {
        log.debug("[shiftLeadTime]");
        log.trace("[orderDto: {}]", orderDto);
        try {
            ParameterizedTypeReference<ClientMessageDto> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<ClientMessageDto> response = restTemplate.exchange(
                SHIFT_LEAD_TIME_PATH, HttpMethod.PUT, new HttpEntity<>(httpHeaders), beanType, orderDto);
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
    public List<OrderDto> getSortOrders(String sortParameter) {
        log.debug("[getOrdersSortByFilingDate]");
        log.trace("[sortParameter: {}]", sortParameter);
        try {
            ParameterizedTypeReference<List<OrderDto>> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<List<OrderDto>> response = restTemplate.exchange(
                GET_SORT_ORDERS_PATH + sortParameter, HttpMethod.GET, new HttpEntity<>(httpHeaders), beanType);
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

    @Override
    public List<OrderDto> getSortOrdersByPeriod(String sortParameter, String startPeriod, String endPeriod) {
        log.debug("[getOrdersSortByFilingDate]");
        log.trace("[sortParameter: {}]", sortParameter);
        try {
            ParameterizedTypeReference<List<OrderDto>> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<List<OrderDto>> response = restTemplate.exchange(
                GET_SORT_ORDERS_PATH + sortParameter + PARAMETER_START_TIME + startPeriod +
                PARAMETER_END_TIME + endPeriod, HttpMethod.GET, new HttpEntity<>(httpHeaders), beanType);
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

    @Override
    public List<MasterDto> getOrderMasters(Long orderId) {
        log.debug("[getOrderMasters]");
        log.trace("[orderId: {}]", orderId);
        try {
            ParameterizedTypeReference<List<MasterDto>> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<List<MasterDto>> response = restTemplate.exchange(
                GET_ORDER_MASTERS_START_PATH + orderId + GET_ORDER_MASTERS_END_PATH, HttpMethod.GET,
                new HttpEntity<>(httpHeaders), beanType);
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
    public DateDto getNearestFreeDate() {
        log.debug("[getNearestFreeDate]");
        try {
            ParameterizedTypeReference<DateDto> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<DateDto> response = restTemplate.exchange(
                GET_NEAREST_FREE_DATE_PATH, HttpMethod.GET, new HttpEntity<>(httpHeaders), beanType);
            return response.getBody();
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            throw new BusinessException(ExceptionUtil.getMessage(exception, objectMapper));
        }
    }

    @Override
    public String exportEntities() {
        log.debug("[exportEntities]");
        try {
            ParameterizedTypeReference<ClientMessageDto> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<ClientMessageDto> response = restTemplate.exchange(
                EXPORT_ENTITIES_PATH, HttpMethod.POST, new HttpEntity<>(httpHeaders), beanType);
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
    public String importEntities() {
        log.debug("[importEntities]");
        try {
            ParameterizedTypeReference<ClientMessageDto> beanType = new ParameterizedTypeReference<>() { };
            ResponseEntity<ClientMessageDto> response = restTemplate.exchange(
                IMPORT_ENTITIES_PATH, HttpMethod.POST, new HttpEntity<>(httpHeaders), beanType);
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

}
